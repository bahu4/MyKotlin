package com.example.mykotlin.main

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.mykotlin.R
import com.example.mykotlin.data.entity.Note
import com.example.mykotlin.ui.main.MainActivity
import com.example.mykotlin.ui.main.MainRVAdapter
import com.example.mykotlin.ui.main.MainViewModel
import com.example.mykotlin.ui.main.MainViewState
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext

class MainActivityTest {
    @get: Rule
    val activityRule = IntentsTestRule(MainActivity::class.java, true, false)
    private val model: MainViewModel = mockk()
    private val viewStateLiveData = MutableLiveData<MainViewState>()
    private val testNotes = listOf(
        Note("1", "title1", "text1"),
        Note("2", "title2", "text2"),
        Note("3", "title3", "text3")
    )

    @Before
    fun setup() {
        StandAloneContext.loadKoinModules(
            listOf(module {
                viewModel(override = true) { model }
            })
        )

        every { model.getViewState() } returns viewStateLiveData
        every { model.onCleared() } just runs
        activityRule.launchActivity(null)
        viewStateLiveData.postValue(MainViewState(taskList = testNotes))
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun check_data_is_displayed() {
        onView(withId(R.id.main_rv)).perform(scrollToPosition<MainRVAdapter.ViewHolder>(1))
        onView(withText(testNotes[0].task)).check(matches(isDisplayed()))
    }
}