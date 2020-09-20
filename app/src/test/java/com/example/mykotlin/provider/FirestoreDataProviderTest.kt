package com.example.mykotlin.provider

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.errors.NoAuthException
import com.example.mykotlin.data.model.NoteResult
import com.example.mykotlin.data.provider.FirestoreDataProvider
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.mockk.*
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FirestoreDataProviderTest {
    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    private val mockDB = mockk<FirebaseFirestore>()
    private val mockAuth = mockk<FirebaseAuth>()
    private val mockResultCollection = mockk<CollectionReference>()
    private val mockUser = mockk<FirebaseUser>()
    private val mockDoc = mockk<DocumentSnapshot>()
    private val testNote = listOf(Note("1"), Note("2"), Note("3"))
    private val provider = FirestoreDataProvider(mockDB, mockAuth)

    @Before
    fun setup() {
        clearAllMocks()
        every { mockAuth.currentUser } returns mockUser
        every { mockUser.uid } returns ""
        every {
            mockDB.collection(any()).document(any()).collection(any())
        } returns mockResultCollection
        every { mockDoc.toObject(Note::class.java) } returns testNote[0]
    }

    @Test
    fun `should throw NoAuthException if no auth`() {
        every { mockAuth.currentUser } returns null
        var result: Any? = null
        provider.subscribeToAllNotes().observeForever {
            result = (it as NoteResult.Error).error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `saveNote calls set`() {
        val mockDocumentReference = mockk<DocumentReference>()
        every { mockResultCollection.document(testNote[0].id) } returns mockDocumentReference
        provider.saveNote(testNote[0])
        verify(exactly = 1) { mockDocumentReference.set(testNote[0]) }
    }

    @Test
    fun `saveNote returns Note`() {
        var result: Note? = null
        val mockDocumentReference = mockk<DocumentReference>()
        val slot = slot<OnSuccessListener<in Void>>()

        every { mockResultCollection.document(testNote[0].id) } returns mockDocumentReference
        every { mockDocumentReference.set(testNote[0]).addOnSuccessListener(capture(slot)) } returns mockk()

        provider.saveNote(testNote[0]).observeForever {
            result = (it as? NoteResult.Success<Note>)?.data
        }

        slot.captured.onSuccess(null)
        assertEquals(result, testNote[0])
    }
}