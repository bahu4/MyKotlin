package com.example.mykotlin.data.provider

import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.entity.User
import com.example.mykotlin.data.errors.NoAuthException
import com.example.mykotlin.data.model.NoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirestoreDataProvider(val store: FirebaseFirestore, val auth: FirebaseAuth) :
    RemoteDataProvider {
    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val currentUser
        get() = auth.currentUser

    val notesCollection
        get() = currentUser?.let {
            store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()

    override fun subscribeToAllNotes(): ReceiveChannel<NoteResult> =
        Channel<NoteResult>(Channel.CONFLATED).apply {
            var registration: ListenerRegistration? = null
            try {
                registration = notesCollection.addSnapshotListener { snapshot, e ->
                    val value = e?.let {
                        NoteResult.Error(it)
                    } ?: let {
                        snapshot?.let {
                            val notes = snapshot.documents.map { doc ->
                                doc.toObject(Note::class.java)
                            }
                            NoteResult.Success(notes)
                        }
                    }
                    value?.let { offer(it) }
                }
            } catch (e: Throwable) {
                offer(NoteResult.Error(e))
            }
        }

    override suspend fun getNoteById(id: String): Note = suspendCoroutine { continuation ->
        try {
            notesCollection.document(id).get()
                .addOnSuccessListener { snapshot ->
                    continuation.resume(snapshot.toObject(Note::class.java)!!)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }


    override suspend fun saveNote(note: Note): Note = suspendCoroutine { continuation ->
        try {
            notesCollection.document(note.id).set(note)
                .addOnSuccessListener {
                    continuation.resume(note)
                }.addOnFailureListener {
                    continuation.resumeWithException(it)
                }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }

    override suspend fun getCurrentUser(): User? = suspendCoroutine { continuation ->
        currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
            .let { continuation.resume(it) }
    }

    override suspend fun deleteNote(id: String): Unit = suspendCoroutine { continuation ->
        try {
            notesCollection.document(id).delete().addOnSuccessListener { snapshot ->
                continuation.resume(Unit)
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }
        } catch (e: Throwable) {
            continuation.resumeWithException(e)
        }
    }
}