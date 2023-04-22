package com.example.quizapplication

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.quizapplication.databinding.ActivityQuizQuestionsBinding
import com.example.quizapplication.databinding.CustomQuitQuizDialogBinding


class QuizQuestionsActivity : AppCompatActivity(), OnClickListener {

    private var icon : Drawable? = null
    private var mCurrentPosition = 1
    private var mQuestionList: ArrayList<Question>? = null
    private var selectedOptions: Int = 0
    private var scoreCounter: Int = 0
    private var userName: String? = null
    private var binding: ActivityQuizQuestionsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        // Store the Question in mQuestionList
        mQuestionList = Constant.getQuestions()

        setQuestion()

        userName = intent.getStringExtra(Constant.USER_NAME)

        binding?.optionOne?.setOnClickListener(this)
        binding?.optionTwo?.setOnClickListener(this)
        binding?.optionThree?.setOnClickListener(this)
        binding?.optionFour?.setOnClickListener(this)
        binding?.submitBtn?.setOnClickListener(this)
    }
    // Set the list of questions
    private fun setQuestion() {
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        defaultView()
        binding?.etQuestions?.text = question.question
        binding?.progBar?.progress = mCurrentPosition
        binding?.progCounter?.text = "$mCurrentPosition"
        binding?.imgFlags?.setImageResource(question.image)
        binding?.optionOne?.text = question.optionOne
        binding?.optionTwo?.text = question.optionTwo
        binding?.optionThree?.text = question.optionThree
        binding?.optionFour?.text = question.optionFour
        // Set Button Text
        if(mCurrentPosition == mQuestionList!!.size){
            binding?.submitBtn?.text = "FINISH"
        }else{
            // Enable option when the button text is Submit
            binding!!.optionOne.isEnabled = true
            binding!!.optionTwo.isEnabled = true
            binding!!.optionThree.isEnabled = true
            binding!!.optionFour.isEnabled = true
            binding?.submitBtn?.text = "SUBMIT"
        }
    }

    // We will be setting the default settings of every options in the quiz in order to for us to easily modify them when clicked
    private fun defaultView(){
        // Disable the submit button while
        binding!!.submitBtn.isEnabled = false
        // Create an Array List
        val options = ArrayList<TextView>()
        // Add every option in the Array List
        binding?.optionOne?.let {
            options.add(0, it)
        }
        binding?.optionTwo?.let {
            options.add(1, it)
        }
        binding?.optionThree?.let {
            options.add(2, it)
        }
        binding?.optionFour?.let {
            options.add(3, it)
        }
        // Loop arrayList options and set every option the default settings
        for(option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background = ContextCompat.getDrawable(this, R.drawable.quiz_options_default_background)
        }

    }
    /* This function will be use for changing the color of the option when one of them is clicked
     This will have a parameter of what TextView is click, and the Number of that option */
    private fun selectedOption(tvOption: TextView, selectedOptionNum: Int){
        defaultView()
        selectedOptions = selectedOptionNum
        tvOption.setTextColor(Color.parseColor("#363A43"))
        tvOption.setTypeface(tvOption.typeface, Typeface.BOLD)
        tvOption.background = ContextCompat.getDrawable(this, R.drawable.quiz_option_selected_background)
        // Enable button when an option is selected
        binding!!.submitBtn.isEnabled = true
    }
    // We inherit the OnclickListener and override it's functions and set it in every property in the Application
    override fun onClick(view: View?) {
        when(view?.id){ // Check the ID of the clicked property
            R.id.optionOne -> {
                binding?.optionOne?.let {
                    selectedOption(it, 1)
                }
            }
            R.id.optionTwo -> {
                binding?.optionTwo?.let {
                    selectedOption(it, 2)
                }
            }
            R.id.optionThree -> {
                binding?.optionThree?.let {
                    selectedOption(it, 3)
                }
            }
            R.id.optionFour -> {
                binding?.optionFour?.let {
                    selectedOption(it, 4)
                }
            }
            R.id.submitBtn -> {
                if(selectedOptions == 0){
                    mCurrentPosition++
                    binding?.optionOne?.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    binding?.optionTwo?.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    binding?.optionThree?.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    binding?.optionFour?.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            setQuestion()
                        }else ->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constant.USER_NAME, userName)
                            intent.putExtra(Constant.USER_SCORE, scoreCounter)
                            intent.putExtra(Constant.TOTAL_SCORE, mQuestionList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                }else{
                    val question = mQuestionList?.get(mCurrentPosition - 1)
                    if (question!!.correctAns != selectedOptions){
                        viewAnswer(selectedOptions, R.drawable.wrong_answer_icon_24dp)
                    } else{
                        scoreCounter++
                    }
                    viewAnswer(question.correctAns, R.drawable.correct_answer_icon_24dp)
                    if (mCurrentPosition == mQuestionList!!.size) {
                        binding?.submitBtn?.text = "FINISH"
                    } else {
                        // Disable options when the button text is go to next question
                        binding!!.optionOne.isEnabled = false
                        binding!!.optionTwo.isEnabled = false
                        binding!!.optionThree.isEnabled = false
                        binding!!.optionFour.isEnabled = false
                        binding?.submitBtn?.text = "GO TO NEXT QUESTION"
                    }
                }
                selectedOptions = 0
            }
        }
    }
    // Function for comparing the user answer and the correct answer
    private fun viewAnswer(answer:Int, drawable: Int){
        icon = ContextCompat.getDrawable(this, drawable)
        when(answer){
            1 -> {
                binding?.optionOne?.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
            }
            2 -> {
                binding?.optionTwo?.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
            }
            3 -> {
                binding?.optionThree?.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
            }
            4 -> {
                binding?.optionFour?.setCompoundDrawablesWithIntrinsicBounds(null, null, icon, null)
            }
        }
    }

    override fun onBackPressed() {
        customBackDialog()
    }
    // Custom Dialog when back is pressed while  answering quizzes
    private fun customBackDialog(){
        val customDialog = Dialog(this)
        val dialogBinding = CustomQuitQuizDialogBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCancelable(false)

        dialogBinding.btnYes.setOnClickListener {
            this@QuizQuestionsActivity.finish()
            customDialog.dismiss()
        }
        dialogBinding.btnNo.setOnClickListener {
            customDialog.dismiss()
        }

        customDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}