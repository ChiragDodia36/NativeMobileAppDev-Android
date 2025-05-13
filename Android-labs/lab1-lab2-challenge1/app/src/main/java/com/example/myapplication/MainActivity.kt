package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        truebutton = findViewById(R.id.true_button)
//        falsebutton = findViewById(R.id.false_button)
        binding.questionTextView.setOnClickListener{
            currentIndex = (currentIndex+1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
        binding.trueButton.setOnClickListener{
            checkAnswer(true)
        }
        binding.falseButton.setOnClickListener{
        checkAnswer(false)
        }
        binding.nextButton.setOnClickListener{
            currentIndex = (currentIndex+1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }
    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT).show()
    }
}