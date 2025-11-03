package com.example.baitapso4

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var maleRadio: RadioButton
    private lateinit var femaleRadio: RadioButton
    private lateinit var birthdayInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var termsCheckbox: CheckBox
    private lateinit var registerButton: Button

    // Tag for logging lifecycle events
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate: Activity created")

        // Initialize views
        bindViews()

        // Set up event listeners
        setupEventListeners()
    }

    private fun bindViews() {
        firstNameInput = findViewById(R.id.firstNameInput)
        lastNameInput = findViewById(R.id.lastNameInput)
        maleRadio = findViewById(R.id.maleRadioGroup) // Using the grouped version
        femaleRadio = findViewById(R.id.femaleRadioGroup) // Using the grouped version
        birthdayInput = findViewById(R.id.birthdayInput)
        addressInput = findViewById(R.id.addressInput)
        emailInput = findViewById(R.id.emailInput)
        termsCheckbox = findViewById(R.id.termsCheckbox)
        registerButton = findViewById(R.id.registerButton)
    }

    private fun setupEventListeners() {
        registerButton.setOnClickListener {
            handleRegistration()
        }

        // Optional: Add text change listeners for real-time validation
        setupTextChangeListeners()
    }

    private fun setupTextChangeListeners() {
        // Example: Clear error when user starts typing
        firstNameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateFirstName()
        }

        lastNameInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateLastName()
        }

        emailInput.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) validateEmail()
        }
    }

    private fun handleRegistration() {
        if (validateForm()) {
            // Show success message
            Snackbar.make(
                registerButton,
                "Registration successful for ${firstNameInput.text} ${lastNameInput.text}",
                Snackbar.LENGTH_LONG
            ).show()

            // Optionally clear form after successful registration
            clearForm()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        // Validate First Name
        if (!validateFirstName()) isValid = false

        // Validate Last Name
        if (!validateLastName()) isValid = false

        // Validate Gender selection
        if (!validateGender()) isValid = false

        // Validate Birthday
        if (!validateBirthday()) isValid = false

        // Validate Address
        if (!validateAddress()) isValid = false

        // Validate Email
        if (!validateEmail()) isValid = false

        // Validate Terms agreement
        if (!validateTerms()) isValid = false

        return isValid
    }

    private fun validateFirstName(): Boolean {
        val firstName = firstNameInput.text.toString().trim()
        return if (firstName.isEmpty()) {
            firstNameInput.error = "First name is required"
            false
        } else if (firstName.length < 2) {
            firstNameInput.error = "First name must be at least 2 characters"
            false
        } else {
            firstNameInput.error = null
            true
        }
    }

    private fun validateLastName(): Boolean {
        val lastName = lastNameInput.text.toString().trim()
        return if (lastName.isEmpty()) {
            lastNameInput.error = "Last name is required"
            false
        } else if (lastName.length < 2) {
            lastNameInput.error = "Last name must be at least 2 characters"
            false
        } else {
            lastNameInput.error = null
            true
        }
    }

    private fun validateGender(): Boolean {
        val isMaleSelected = maleRadio.isChecked
        val isFemaleSelected = femaleRadio.isChecked

        return if (!isMaleSelected && !isFemaleSelected) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun validateBirthday(): Boolean {
        val birthday = birthdayInput.text.toString().trim()
        return if (birthday.isEmpty()) {
            birthdayInput.error = "Birthday is required"
            false
        } else {
            birthdayInput.error = null
            true
        }
    }

    private fun validateAddress(): Boolean {
        val address = addressInput.text.toString().trim()
        return if (address.isEmpty()) {
            addressInput.error = "Address is required"
            false
        } else if (address.length < 5) {
            addressInput.error = "Address must be at least 5 characters"
            false
        } else {
            addressInput.error = null
            true
        }
    }

    private fun validateEmail(): Boolean {
        val email = emailInput.text.toString().trim()
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS

        return if (email.isEmpty()) {
            emailInput.error = "Email is required"
            false
        } else if (!emailPattern.matcher(email).matches()) {
            emailInput.error = "Invalid email format"
            false
        } else {
            emailInput.error = null
            true
        }
    }

    private fun validateTerms(): Boolean {
        return if (!termsCheckbox.isChecked) {
            Toast.makeText(this, "Please agree to Terms of Use", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    private fun clearForm() {
        firstNameInput.text.clear()
        lastNameInput.text.clear()
        maleRadio.isChecked = false
        femaleRadio.isChecked = false
        birthdayInput.text.clear()
        addressInput.text.clear()
        emailInput.text.clear()
        termsCheckbox.isChecked = false
    }

    // Lifecycle methods with logging

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart: Activity started")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity resumed")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: Activity stopped")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity destroyed")
    }

    // Optional: Save instance state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG, "onSaveInstanceState: Saving instance state")

        // Save form data if needed
        outState.putString("firstName", firstNameInput.text.toString())
        outState.putString("lastName", lastNameInput.text.toString())
        outState.putBoolean("maleSelected", maleRadio.isChecked)
        outState.putBoolean("femaleSelected", femaleRadio.isChecked)
        outState.putString("birthday", birthdayInput.text.toString())
        outState.putString("address", addressInput.text.toString())
        outState.putString("email", emailInput.text.toString())
        outState.putBoolean("termsAccepted", termsCheckbox.isChecked)
    }

    // Optional: Restore instance state
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "onRestoreInstanceState: Restoring instance state")

        // Restore form data if needed
        firstNameInput.setText(savedInstanceState.getString("firstName", ""))
        lastNameInput.setText(savedInstanceState.getString("lastName", ""))
        maleRadio.isChecked = savedInstanceState.getBoolean("maleSelected", false)
        femaleRadio.isChecked = savedInstanceState.getBoolean("femaleSelected", false)
        birthdayInput.setText(savedInstanceState.getString("birthday", ""))
        addressInput.setText(savedInstanceState.getString("address", ""))
        emailInput.setText(savedInstanceState.getString("email", ""))
        termsCheckbox.isChecked = savedInstanceState.getBoolean("termsAccepted", false)
    }
}