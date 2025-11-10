package com.example.baitapso5
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var editTextAmount1: EditText
    private lateinit var editTextAmount2: EditText
    private lateinit var spinnerCurrency1: Spinner
    private lateinit var spinnerCurrency2: Spinner

    private val currencies = arrayOf("USD", "EUR", "JPY", "GBP", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD")
    private val rates = doubleArrayOf(1.0, 0.85, 110.15, 0.73, 1.31, 1.25, 0.92, 6.46, 8.54, 1.41)
    private var isUpdating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextAmount1 = findViewById(R.id.editTextAmount1)
        editTextAmount2 = findViewById(R.id.editTextAmount2)
        spinnerCurrency1 = findViewById(R.id.spinnerCurrency1)
        spinnerCurrency2 = findViewById(R.id.spinnerCurrency2)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCurrency1.adapter = adapter
        spinnerCurrency2.adapter = adapter

        spinnerCurrency1.setSelection(0)
        spinnerCurrency2.setSelection(1)

        editTextAmount1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!isUpdating) convertCurrency(true)
            }
        })

        editTextAmount2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (!isUpdating) convertCurrency(false)
            }
        })

        spinnerCurrency1.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                convertCurrency(true)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        spinnerCurrency2.onItemSelectedListener = object : android.widget.AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: android.widget.AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                convertCurrency(true)
            }
            override fun onNothingSelected(parent: android.widget.AdapterView<*>) {}
        }

        editTextAmount1.setText("1.0")
    }

    private fun convertCurrency(fromFirst: Boolean) {
        try {
            val amount1 = editTextAmount1.text.toString().toDoubleOrNull() ?: 0.0
            val amount2 = editTextAmount2.text.toString().toDoubleOrNull() ?: 0.0
            val position1 = spinnerCurrency1.selectedItemPosition
            val position2 = spinnerCurrency2.selectedItemPosition

            isUpdating = true
            if (fromFirst) {
                val result = amount1 * rates[position2] / rates[position1]
                editTextAmount2.setText(String.format("%.2f", result))
            } else {
                val result = amount2 * rates[position1] / rates[position2]
                editTextAmount1.setText(String.format("%.2f", result))
            }
            isUpdating = false
        } catch (e: Exception) {
            isUpdating = false
        }
    }
}