package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
//    private lateinit var truebutton : Button
//    private lateinit var falsebutton : Button
    private lateinit var binding : ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        // Handle result
        if (result.resultCode == Activity.RESULT_OK) {
            val didCheat = result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            if (didCheat) {
                // Mark only the current question as having been cheated on.
                quizViewModel.setCheatedForCurrentQuestion()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate(Bundle?)called")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "Got a ViewQuizModel: $quizViewModel")
//        truebutton = findViewById(R.id.true_button)
//        falsebutton = findViewById(R.id.false_button)
        binding.trueButton.setOnClickListener{
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener{
        checkAnswer(false)
        }
        binding.nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        binding.cheatButton.setOnClickListener{
            // Start cheatactivity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)
        }
        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "OnStart()called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "OnResume()called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "OnPause()called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "OnStop()called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "OnDestroy()called")
    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer: Boolean = quizViewModel.currentQuestionAnswer
        // Use the per-question cheat flag to decide which toast to show.
        val messageResId = when {
            quizViewModel.isCheaterForCurrentQuestion() -> R.string.judgement_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }
}