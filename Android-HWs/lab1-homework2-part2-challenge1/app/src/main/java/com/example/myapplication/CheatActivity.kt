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
class CheatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheatBinding

    private var answerISTrue = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCheatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        answerISTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        binding.showAnswerButton.setOnClickListener{
            val answerText = when{
                answerISTrue -> R.string.true_button
                else -> R.string.false_button
            }
            binding.answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
        }

    private fun setAnswerShownResult(isAnswerShown: Boolean){
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

        companion object{
            fun newIntent(packageContext: Context, answerISTrue: Boolean): Intent{
                return Intent(packageContext, CheatActivity:: class.java).apply {
                    putExtra(EXTRA_ANSWER_IS_TRUE, answerISTrue)
                }
            }
        }
}