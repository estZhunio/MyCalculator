package com.example.mycalculator

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import java.util.Stack



class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var resultTv: TextView
    lateinit var solutionTv: TextView

    lateinit var buttonC: MaterialButton
    lateinit var buttonBrackOpen: MaterialButton
    lateinit var buttonBrackClose: MaterialButton

    lateinit var buttonDivide: MaterialButton
    lateinit var buttonMultiply: MaterialButton
    lateinit var buttonPlus: MaterialButton
    lateinit var buttonMinus: MaterialButton
    lateinit var buttonEquals: MaterialButton

    lateinit var button0: MaterialButton
    lateinit var button1: MaterialButton
    lateinit var button2: MaterialButton
    lateinit var button3: MaterialButton
    lateinit var button4: MaterialButton
    lateinit var button5: MaterialButton
    lateinit var button6: MaterialButton
    lateinit var button7: MaterialButton
    lateinit var button8: MaterialButton
    lateinit var button9: MaterialButton

    lateinit var buttonAC: MaterialButton
    lateinit var buttonDot: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        resultTv = findViewById(R.id.resul_tv)
        solutionTv = findViewById(R.id.solution_tv)
        assign()


    }

    override fun onClick(v: View) {
        var button = v as MaterialButton;
        var buttonText = button.text.toString()

        var dataCalculator = solutionTv.text.toString()

        if (buttonText == "AC") {
            solutionTv.setText("")
            resultTv.setText("0")
            return // Salir temprano de la funcion
        }

        if (buttonText == "=") {
            resultTv.setText(getResul(solutionTv.text.toString()).toString())
            solutionTv.setText(resultTv.text.toString())
            return
        }

        if (buttonText == "C") {
            dataCalculator = dataCalculator.substring(0, dataCalculator.length - 1)
        } else {
            dataCalculator = dataCalculator + buttonText
        }

        solutionTv.setText(dataCalculator)

    }


    fun getResul(expression: String): Double {
        val outputQueue: MutableList<String> = mutableListOf()
        val operatorStack: Stack<String> = Stack()

        val operators = setOf("+", "-", "*", "/")
        val precedence = mapOf("+" to 1, "-" to 1, "*" to 2, "/" to 2)
        val associativity = mapOf("+" to "L", "-" to "L", "*" to "L", "/" to "L")

        // Tokenize the input expression
        val tokens = mutableListOf<String>()
        val numberBuilder = StringBuilder()
        for (char in expression.replace(" ", "")) {
            when {
                char.isDigit() || char == '.' -> {
                    numberBuilder.append(char)
                }

                char.toString() in operators || char == '(' || char == ')' -> {
                    if (numberBuilder.isNotEmpty()) {
                        tokens.add(numberBuilder.toString())
                        numberBuilder.clear()
                    }
                    tokens.add(char.toString())
                }

                else -> {
                    // Invalid character handling (if needed)
                }
            }
        }
        if (numberBuilder.isNotEmpty()) {
            tokens.add(numberBuilder.toString())
        }

        // Process tokens using Shunting Yard algorithm
        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> outputQueue.add(token)
                operators.contains(token) -> {
                    while (operatorStack.isNotEmpty() && operators.contains(operatorStack.peek()) &&
                        ((associativity[token] == "L" && precedence[token]!! <= precedence[operatorStack.peek()]!!) ||
                                (associativity[token] == "R" && precedence[token]!! < precedence[operatorStack.peek()]!!))
                    ) {
                        outputQueue.add(operatorStack.pop())
                    }
                    operatorStack.push(token)
                }

                token == "(" -> operatorStack.push(token)
                token == ")" -> {
                    while (operatorStack.isNotEmpty() && operatorStack.peek() != "(") {
                        outputQueue.add(operatorStack.pop())
                    }
                    if (operatorStack.isNotEmpty() && operatorStack.peek() == "(") {
                        operatorStack.pop()
                    }
                }
            }
        }

        while (operatorStack.isNotEmpty()) {
            outputQueue.add(operatorStack.pop())
        }

        // Evaluate the RPN expression
        val stack: Stack<Double> = Stack()
        for (token in outputQueue) {
            when {
                token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                operators.contains(token) -> {
                    val b = stack.pop()
                    val a = stack.pop()
                    when (token) {
                        "+" -> stack.push(a + b)
                        "-" -> stack.push(a - b)
                        "*" -> stack.push(a * b)
                        "/" -> stack.push(a / b)
                    }
                }
            }
        }

        return stack.pop()

    }


    fun assign() {
        buttonC = findViewById(R.id.button_c)
        buttonC.setOnClickListener(this);
        buttonBrackOpen = findViewById(R.id.button_open_bracket)
        buttonBrackOpen.setOnClickListener(this);
        buttonBrackClose = findViewById(R.id.button_close_bracket)
        buttonBrackClose.setOnClickListener(this);

        buttonDivide = findViewById(R.id.butto_divide)
        buttonDivide.setOnClickListener(this);
        buttonMultiply = findViewById(R.id.button_multiply)
        buttonMultiply.setOnClickListener(this);
        buttonPlus = findViewById(R.id.butto_plus)
        buttonPlus.setOnClickListener(this);
        buttonMinus = findViewById(R.id.butto_minus)
        buttonMinus.setOnClickListener(this);
        buttonEquals = findViewById(R.id.button_equals)
        buttonEquals.setOnClickListener(this);

        button0 = findViewById(R.id.button_0)
        button0.setOnClickListener(this);
        button1 = findViewById(R.id.button_1)
        button1.setOnClickListener(this);
        button2 = findViewById(R.id.button_2)
        button2.setOnClickListener(this);
        button3 = findViewById(R.id.button_3)
        button3.setOnClickListener(this);
        button4 = findViewById(R.id.button_4)
        button4.setOnClickListener(this);
        button5 = findViewById(R.id.button_5)
        button5.setOnClickListener(this);
        button6 = findViewById(R.id.button_6)
        button6.setOnClickListener(this);
        button7 = findViewById(R.id.button_7)
        button7.setOnClickListener(this);
        button8 = findViewById(R.id.button_8)
        button8.setOnClickListener(this);
        button9= findViewById(R.id.button_9)
        button9.setOnClickListener(this);

        buttonAC = findViewById(R.id.button_ac)
        buttonAC.setOnClickListener(this);
        buttonDot= findViewById(R.id.button_dot)
        buttonDot.setOnClickListener(this);

    }

}