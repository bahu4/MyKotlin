package com.example.mykotlin.note

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mykotlin.data.NoteRepo
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.data.model.NoteResult
import com.example.mykotlin.ui.second.SecondViewModel
import com.example.mykotlin.ui.second.NoteData
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SecondViewModelTest {
    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    private val mockRepository = mockk<NoteRepo>(relaxed = true)
    private val secondLiveData = MutableLiveData<NoteResult>()
    private val testNote = Note("1", "title", "task")
    private lateinit var viewModel: SecondViewModel

    @Before
    fun setup() {
        clearAllMocks()
        every { mockRepository.getNoteById(testNote.id) } returns secondLiveData
        every { mockRepository.deleteNote(testNote.id) } returns secondLiveData
        viewModel = SecondViewModel(mockRepository)
    }

    @Test
    fun `loadNote should returns NoteData`() {
        var result: NoteData.Data? = null
        val testData = NoteData.Data(false, testNote)
        viewModel.viewStateLiveData.observeForever {
            result = it.data
        }
        viewModel.loadNote(testNote.id)
        secondLiveData.value = NoteResult.Success(testNote)
        assertEquals(testData, result)
    }

    @Test
    fun `loadNote should returns error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.viewStateLiveData.observeForever {
            result = it.error
        }
        viewModel.loadNote(testNote.id)
        secondLiveData.value = NoteResult.Error(error = testData)
        assertEquals(testData, result)
    }

    @Test
    fun `should save changes`() {
        viewModel.save(testNote)
        viewModel.onCleared()
        verify(exactly = 1) { mockRepository.saveNote(testNote) }
    }

    @Test
    fun `delete should returns Note with isDelete`() {
        var result: NoteData.Data? = null
        val testData = NoteData.Data(true)
        viewModel.viewStateLiveData.observeForever {
            result = it?.data
        }
        viewModel.save(testNote)
        viewModel.deleteNote()
        secondLiveData.value = NoteResult.Success(null)
        assertEquals(testData, result)
    }

    @Test
    fun `delete should return error`() {
        var result: Throwable? = null
        val testData = Throwable("error")
        viewModel.viewStateLiveData.observeForever {
            result = it?.error
        }
        viewModel.save(testNote)
        viewModel.deleteNote()
        secondLiveData.value = NoteResult.Error(error = testData)
        assertEquals(testData, result)
    }
}