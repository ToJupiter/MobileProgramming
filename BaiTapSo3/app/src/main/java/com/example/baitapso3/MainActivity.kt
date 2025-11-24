package com.example.baitapso3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView

    private var currentInput = "0"
    private var firstNumber: Double? = null
    private var operator: String? = null
    private var isNewInput = true
    private var lastResult: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        display.text = currentInput

        setupNumberButtons()
        setupOperatorButtons()
        setupFunctionButtons()
    }

    private fun setupNumberButtons() {
        val numberButtons = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
            R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9
        )

        numberButtons.forEach { buttonId ->
            findViewById<Button>(buttonId).setOnClickListener { view ->
                onNumberClicked(view as Button)
            }
        }

        findViewById<Button>(R.id.btn_decimal).setOnClickListener {
            onDecimalClicked()
        }
    }

    private fun setupOperatorButtons() {
        val operatorButtons = mapOf(
            R.id.btn_add to "+",
            R.id.btn_subtract to "-",
            R.id.btn_multiply to "*",
            R.id.btn_divide to "/"
        )

        operatorButtons.forEach { (buttonId, op) ->
            findViewById<Button>(buttonId).setOnClickListener {
                onOperatorClicked(op)
            }
        }

        findViewById<Button>(R.id.btn_equals).setOnClickListener {
            onEqualsClicked()
        }
    }

    private fun setupFunctionButtons() {
        findViewById<Button>(R.id.btn_ce).setOnClickListener {
            clearEntry()
        }

        findViewById<Button>(R.id.btn_c).setOnClickListener {
            clearAll()
        }

        findViewById<Button>(R.id.btn_backspace).setOnClickListener {
            backspace()
        }

        findViewById<Button>(R.id.btn_sign).setOnClickListener {
            toggleSign()
        }
    }

    private fun onNumberClicked(button: Button) {
        val number = button.text.toString()

        if (isNewInput) {
            currentInput = number
            isNewInput = false
        } else {
            if (currentInput == "0" && number != "0") {
                currentInput = number
            } else if (currentInput != "0" || number != "0") {
                currentInput += number
            }
        }

        updateDisplay()
    }

    private fun onDecimalClicked() {
        if (isNewInput) {
            currentInput = "0."
            isNewInput = false
        } else if (!currentInput.contains(".")) {
            currentInput += "."
        }

        updateDisplay()
    }

    private fun onOperatorClicked(op: String) {
        if (firstNumber == null && lastResult != null) {
            firstNumber = lastResult
            currentInput = "0"
            isNewInput = true
        }

        if (operator != null && !isNewInput) {
            calculateResult()
        }

        if (operator == null) {
            firstNumber = currentInput.toDoubleOrNull() ?: 0.0
        }

        operator = op
        isNewInput = true
    }

    private fun onEqualsClicked() {
        if (operator != null && firstNumber != null && !isNewInput) {
            calculateResult()
        }
    }

    private fun calculateResult() {
        val secondNumber = currentInput.toDoubleOrNull() ?: 0.0

        if (firstNumber == null) return

        var result: Double? = null
        val first = firstNumber!!

        try {
            when (operator) {
                "+" -> result = first + secondNumber
                "-" -> result = first - secondNumber
                "*" -> result = first * secondNumber
                "/" -> {
                    if (secondNumber == 0.0) {
                        Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                        return
                    }
                    result = first / secondNumber
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Calculation error", Toast.LENGTH_SHORT).show()
            clearAll()
            return
        }

        currentInput = formatResult(result!!)
        lastResult = result
        isNewInput = true
        operator = null
        firstNumber = null

        updateDisplay()
    }

    private fun formatResult(result: Double): String {
        return if (result % 1 == 0.0) {
            result.toLong().toString()
        } else {
            DecimalFormat("#.##########").format(result)
        }
    }

    private fun clearEntry() {
        currentInput = "0"
        isNewInput = true
        updateDisplay()
    }

    private fun clearAll() {
        currentInput = "0"
        firstNumber = null
        operator = null
        lastResult = null
        isNewInput = true
        updateDisplay()
    }

    private fun backspace() {
        if (currentInput.length > 1) {
            currentInput = currentInput.dropLast(1)
        } else {
            currentInput = "0"
        }

        if (currentInput.endsWith(".")) {
            currentInput = currentInput.dropLast(1)
        }

        updateDisplay()
    }

    private fun toggleSign() {
        if (currentInput != "0") {
            if (currentInput.startsWith("-")) {
                currentInput = currentInput.substring(1)
            } else {
                currentInput = "-$currentInput"
            }
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        display.text = currentInput
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentInput", currentInput)
        outState.putDouble("firstNumber", firstNumber ?: 0.0)
        outState.putString("operator", operator)
        outState.putDouble("lastResult", lastResult ?: 0.0)
        outState.putBoolean("isNewInput", isNewInput)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentInput = savedInstanceState.getString("currentInput", "0") ?: "0"
        firstNumber = if (savedInstanceState.getDouble("firstNumber") != 0.0)
            savedInstanceState.getDouble("firstNumber") else null
        operator = savedInstanceState.getString("operator")
        lastResult = if (savedInstanceState.getDouble("lastResult") != 0.0)
            savedInstanceState.getDouble("lastResult") else null
        isNewInput = savedInstanceState.getBoolean("isNewInput", true)

        updateDisplay()
    }
}