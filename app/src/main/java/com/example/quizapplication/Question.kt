package com.example.quizapplication

  /* This will serve as blueprints where each variable in this class will be used to create
    a different questions with users answers and the correct answer
   */
data class Question(
    val id: Int,
    val question: String,
    val image: Int,
    val optionOne: String,
    val optionTwo: String,
    val optionThree: String,
    val optionFour: String,
    val correctAns: Int
)
