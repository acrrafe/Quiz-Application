package com.example.quizapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isNotEmpty
import com.google.android.material.textfield.TextInputLayout

//private val et_Name: TextInputLayout?  = null
//private val startBtn: Button? = null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val et_Name: EditText = findViewById(R.id.et_Name)
        val startBtn: Button = findViewById(R.id.startBtn)

        startBtn.setOnClickListener {
            if (et_Name.text.isNotEmpty()){
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constant.USER_NAME, et_Name.text.toString())
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this, "Please enter your desired name first!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}