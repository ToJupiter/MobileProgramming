package com.example.baitapso6

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextNumber: EditText
    private lateinit var radioGroupTypes: RadioGroup
    private lateinit var listViewNumbers: ListView
    private lateinit var textViewEmpty: TextView
    private val numbers = mutableListOf<Int>()
    private val adapter by lazy { ArrayAdapter(this, android.R.layout.simple_list_item_1, numbers) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNumber = findViewById(R.id.editTextNumber)
        radioGroupTypes = findViewById(R.id.radioGroupTypes)
        listViewNumbers = findViewById(R.id.listViewNumbers)
        textViewEmpty = findViewById(R.id.textViewEmpty)

        listViewNumbers.adapter = adapter

        editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                updateList()
            }
        })

        radioGroupTypes.setOnCheckedChangeListener { _, _ -> updateList() }
        radioGroupTypes.check(R.id.radioEven)
    }

    private fun updateList() {
        numbers.clear()
        try {
            val limit = editTextNumber.text.toString().toInt()
            val selectedId = radioGroupTypes.checkedRadioButtonId

            for (i in 1 until limit) {
                when (selectedId) {
                    R.id.radioEven -> if (i % 2 == 0) numbers.add(i)
                    R.id.radioOdd -> if (i % 2 != 0) numbers.add(i)
                    R.id.radioPrime -> if (isPrime(i)) numbers.add(i)
                    R.id.radioFibonacci -> if (isFibonacci(i)) numbers.add(i)
                    R.id.radioPerfect -> if (isPerfect(i)) numbers.add(i)
                    R.id.radioSquare -> if (isSquare(i)) numbers.add(i)
                }
            }
        } catch (e: NumberFormatException) {
        }

        adapter.notifyDataSetChanged()

        if (numbers.isEmpty()) {
            listViewNumbers.visibility = android.view.View.GONE
            textViewEmpty.visibility = android.view.View.VISIBLE
        } else {
            listViewNumbers.visibility = android.view.View.VISIBLE
            textViewEmpty.visibility = android.view.View.GONE
        }
    }

    private fun isPrime(n: Int): Boolean {
        if (n <= 1) return false
        for (i in 2..Math.sqrt(n.toDouble()).toInt()) {
            if (n % i == 0) return false
        }
        return true
    }

    private fun isFibonacci(n: Int): Boolean {
        return isPerfectSquare(5 * n * n + 4) || isPerfectSquare(5 * n * n - 4)
    }

    private fun isPerfect(n: Int): Boolean {
        if (n <= 1) return false
        var sum = 1
        for (i in 2..n / 2) {
            if (n % i == 0) sum += i
        }
        return sum == n
    }

    private fun isSquare(n: Int): Boolean {
        val sqrt = Math.sqrt(n.toDouble()).toInt()
        return sqrt * sqrt == n
    }

    private fun isPerfectSquare(n: Int): Boolean {
        val sqrt = Math.sqrt(n.toDouble()).toInt()
        return sqrt * sqrt == n
    }
}