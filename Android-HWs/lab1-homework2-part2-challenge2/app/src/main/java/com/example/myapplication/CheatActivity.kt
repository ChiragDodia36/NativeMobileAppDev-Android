package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityCheatBinding

private const val EXTRA_ANSWER_IS_TRUE = "com.example.myapplication.answer_is_true"
const val EXTRA_ANSWER_SHOWN = "com.example.myapplication.answer_shown"
private const val KEY_ANSWER_SHOWN = "answer_shown_key"

class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding
    private var answerIsTrue = false
    private var isAnswerShown = false
    private var answerISTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerISTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        // Restore cheat state if available
        if (savedInstanceState != null) {
            isAnswerShown = savedInstanceState.getBoolean(KEY_ANSWER_SHOWN, false)
            if (isAnswerShown) {
                // User had already viewed the answer, so update the UI accordingly.
                showAnswer()
            }
        }

        binding.showAnswerButton.setOnClickListener {
            showAnswer()
        }
    }

    private fun showAnswer() {
        val answerText = if (answerIsTrue) R.string.true_button else R.string.false_button
        binding.answerTextView.setText(answerText)
        isAnswerShown = true
        setAnswerShownResult(true)
    }


    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(KEY_ANSWER_SHOWN, isAnswerShown)
    }

        companion object{
            fun newIntent(packageContext: Context, answerISTrue: Boolean): Intent{
                return Intent(packageContext, CheatActivity:: class.java).apply {
                    putExtra(EXTRA_ANSWER_IS_TRUE, answerISTrue)
                }
            }
        }
}