package com.example.quizapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.quizapplication.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private var binding: ActivityResultBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val userName: String? = intent.getStringExtra(Constant.USER_NAME)
        val userScoree: Int = intent.getIntExtra(Constant.USER_SCORE, 0)
        val totalScore: Int = intent.getIntExtra(Constant.TOTAL_SCORE, 0)

        if(userScoree >= 6){
            binding!!.congUser.text = "CONGRATULATIONS ${userName!!.uppercase()}"
            binding!!.scoreUser.text = "Your Score is $userScoree out of $totalScore"
        }else{
            binding!!.congUser.text = "BETTER LUCK NEXT TIME ${userName!!.uppercase()}"
            binding!!.scoreUser.text = "Your Score is $userScoree out of $totalScore"
        }
        binding!!.btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}