package com.example.mykotlin.data.provider

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.entity.User
import com.example.mykotlin.data.errors.NoAuthException
import com.example.mykotlin.data.model.NoteResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirestoreDataProvider : RemoteDataProvider {
    companion object {
        private const val NOTES_COLLECTION = "notes"
        private const val USERS_COLLECTION = "users"
    }

    private val store by lazy { FirebaseFirestore.getInstance() }
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val currentUser
        get() = auth.currentUser

    val notesCollection
        get() = currentUser?.let {
            store.collection(USERS_COLLECTION).document(it.uid).collection(NOTES_COLLECTION)
        } ?: throw NoAuthException()

    override fun subscribeToAllNotes(): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        notesCollection.addSnapshotListener { snapshot, e ->
            e?.let {
                value = NoteResult.Error(e)
            } ?: let {
                snapshot?.let {
                    val notes = snapshot.documents.map { doc ->
                        doc.toObject(Note::class.java)
                    }
                    value = NoteResult.Success(notes)
                }
            }
        }
    }

    override fun getNoteById(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesCollection.document(id).get()
                .addOnSuccessListener { snapshot ->
                    value = NoteResult.Success(snapshot.toObject(Note::class.java))
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        }

    override fun saveNote(note: Note): LiveData<NoteResult> = MutableLiveData<NoteResult>().apply {
        notesCollection.document(note.id).set(note)
            .addOnSuccessListener {
                value = NoteResult.Success(note)
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
    }

    override fun getCurrentUser(): LiveData<User?> = MutableLiveData<User?>().apply {
        value = currentUser?.let { User(it.displayName ?: "", it.email ?: "") }
    }

    override fun deleteNote(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            notesCollection.document(id).delete().addOnSuccessListener { snapshot ->
                value = NoteResult.Success(null)
            }.addOnFailureListener {
                value = NoteResult.Error(it)
            }
        }
}