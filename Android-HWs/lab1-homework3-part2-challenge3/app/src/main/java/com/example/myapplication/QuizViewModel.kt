package com.example.myapplication

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"
const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
const val CHEAT_FLAGS_KEY = "CHEAT_FLAGS_KEY"

class QuizViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_ocean, true),
        Question(R.string.question_middleEast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    // Initialize cheat flags: one boolean per question.
    private val cheatFlags: MutableList<Boolean> = savedStateHandle.get<List<Boolean>>(CHEAT_FLAGS_KEY)
        ?.toMutableList() ?: MutableList(questionBank.size) { false }

    private var currentIndex: Int
        get() = savedStateHandle.get(CURRENT_INDEX_KEY) ?: 0
        set(value) = savedStateHandle.set(CURRENT_INDEX_KEY, value)

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext(){
        currentIndex = (currentIndex+1) % questionBank.size
    }

    // Mark the current question as cheated.
    fun setCheatedForCurrentQuestion() {
        cheatFlags[currentIndex] = true
        savedStateHandle.set(CHEAT_FLAGS_KEY, cheatFlags)
    }

    // Check if the current question has been cheated on.
    fun isCheaterForCurrentQuestion(): Boolean = cheatFlags[currentIndex]
}