package com.example.xiaomicalculator

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import com.example.xiaomicalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set the requested orientation to portrait.
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        onNumberClicked()
        onOperationClicked()
    }


    private fun onOperationClicked() {

        binding.parenthesesOpen.setOnClickListener { appendText("(") }

        binding.parenthesesClose.setOnClickListener { appendText(")") }

        binding.btnDivision.setOnClickListener {

            if (binding.txtExpression.text.isNotEmpty() && binding.txtExpression.text.toString() != "0") {

                val myChar = binding.txtExpression.text.last()

                if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') {
                    appendText("/")
                }
            }
        }


        binding.btnMultiplication.setOnClickListener {

            if (binding.txtExpression.text.isNotEmpty() && binding.txtExpression.text.toString() != "0") {

                val myChar = binding.txtExpression.text.last()

                if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') {
                    appendText("*")
                }
            }
        }

        binding.btnSubtraction.setOnClickListener {

            if (binding.txtExpression.text.isNotEmpty() && binding.txtExpression.text.toString() != "0") {

                val myChar = binding.txtExpression.text.last()

                if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') {
                    appendText("-")
                }
            }
        }

        binding.btnAddition.setOnClickListener {

            if (binding.txtExpression.text.isNotEmpty() && binding.txtExpression.text.toString() != "0") {

                val myChar = binding.txtExpression.text.last()

                if (myChar != '+' && myChar != '-' && myChar != '*' && myChar != '/') {
                    appendText("+")
                }
            }
        }

        binding.btnEqual.setOnClickListener {

            try {

                val expression = ExpressionBuilder(binding.txtExpression.text.toString()).build()
                val intResult = expression.evaluate()
                val longResult = intResult.toLong()

                if (intResult == longResult.toDouble()) {
                    binding.txtResult.text = "= $longResult"
                } else {
                    binding.txtResult.text = "= $intResult"
                }
            } catch (ex: Exception) {

                binding.txtExpression.text = ""
                binding.txtResult.text = ""
                Toast.makeText(this, "An error occurred !!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnDelete.setOnClickListener {

            val oldText = binding.txtExpression.text.toString()

            if (oldText.isNotEmpty() && oldText != "0" && oldText.length > 1) {
                binding.txtExpression.text = oldText.substring(0, oldText.length - 1)
            } else {
                binding.txtExpression.text = "0"
            }
        }

        binding.btnAc.setOnClickListener {

            binding.txtExpression.text = "0"
            binding.txtResult.text = ""
        }
    }


    private fun onNumberClicked() {

        binding.btnNumber0.setOnClickListener { appendText("0") }

        binding.btnNumber1.setOnClickListener { appendText("1") }

        binding.btnNumber2.setOnClickListener { appendText("2") }

        binding.btnNumber3.setOnClickListener { appendText("3") }

        binding.btnNumber4.setOnClickListener { appendText("4") }

        binding.btnNumber5.setOnClickListener { appendText("5") }

        binding.btnNumber6.setOnClickListener { appendText("6") }

        binding.btnNumber7.setOnClickListener { appendText("7") }

        binding.btnNumber8.setOnClickListener { appendText("8") }

        binding.btnNumber9.setOnClickListener { appendText("9") }

        binding.btnDot.setOnClickListener {

            if (binding.txtExpression.text.toString()
                    .isEmpty() || binding.txtExpression.text.toString() == "0"
                || binding.txtResult.text.toString().isNotEmpty()
            ) {
                appendText("0.")

            } else if (!binding.txtExpression.text.toString().contains(".")) {

                appendText(".")
            } else if (
                binding.txtExpression.text.toString().contains(".")
            ) {

                if (binding.txtExpression.text.last() == '+'
                    || binding.txtExpression.text.last() == '*'
                    || binding.txtExpression.text.last() == '/'
                    || binding.txtExpression.text.last() == '-'
                    || binding.txtExpression.text.last() == '('
                    || binding.txtExpression.text.last() == ')'
                ) {

                    appendText("0.")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun appendText(newText: String) {

        if (binding.txtExpression.text.toString() == "0" || binding.txtResult.text.isNotEmpty()) {
            binding.txtExpression.text = ""
        }
        binding.txtResult.text = ""
        binding.txtExpression.append(newText)


        // To attach to the end of the number in the expression in scroll view
        val viewTreeExpression: ViewTreeObserver = binding.horizonScrollViewExp.viewTreeObserver
        viewTreeExpression.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {

                binding.horizonScrollViewExp.viewTreeObserver.removeOnGlobalLayoutListener(this)
                binding.horizonScrollViewExp.scrollTo(binding.txtExpression.width, 0)
            }
        })

        val viewTreeResult: ViewTreeObserver = binding.horizonScrollViewRes.viewTreeObserver
        viewTreeResult.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {

                binding.horizonScrollViewRes.viewTreeObserver.removeOnGlobalLayoutListener(this)
                binding.horizonScrollViewRes.scrollTo(binding.txtResult.width, 0)
            }
        })
    }
}



















