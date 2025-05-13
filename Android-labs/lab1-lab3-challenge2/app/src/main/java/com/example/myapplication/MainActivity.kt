package com.example.myapplication

import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
//    private lateinit var truebutton : Button
//    private lateinit var falsebutton : Button
    private lateinit var binding : ActivityMainBinding
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_ocean, true),
        Question(R.string.question_middleEast, true),
        Question(R.string.question_africa, true),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))
    private var currentIndex = 0
    private var correctAnswer = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "OnCreate(Bundle?)called")
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        truebutton = findViewById(R.id.true_button)
//        falsebutton = findViewById(R.id.false_button)
        binding.trueButton.setOnClickListener{
            checkAnswer(true)
            disableAnswerButtons()
        }
        binding.falseButton.setOnClickListener{
            checkAnswer(false)
            disableAnswerButtons()
        }
        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex+1) % questionBank.size
            if (currentIndex == 0){
                showFinalScore()
            } else{
                updateQuestion()
                enableAnswerButtons()
            }
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
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        var correctAns = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAns){
            correctAnswer += 1
            R.string.correct_toast

        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }
    private fun disableAnswerButtons(){
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false
    }
    private fun enableAnswerButtons(){
        binding.trueButton.isEnabled = true
        binding.falseButton.isEnabled = true
    }
    private fun showFinalScore(){
        val scorePer = (correctAnswer.toFloat()/ questionBank.size)*100
        val pointsMess = "Your Score: (${scorePer.toInt()}%)"
        Toast.makeText(this,pointsMess, Toast.LENGTH_SHORT).show()
        correctAnswer = 0
    }
}