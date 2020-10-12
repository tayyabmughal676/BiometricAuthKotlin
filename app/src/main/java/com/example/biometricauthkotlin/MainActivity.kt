package com.example.biometricauthkotlin

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    //    Initialize
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    // UI
    private lateinit var mBtnAuth: Button
    private lateinit var mImage: ImageView
    private lateinit var mText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//      Context
        executor = ContextCompat.getMainExecutor(this)

        mText = findViewById(R.id.textView)
        mImage = findViewById(R.id.FingerPrintImage)
        mBtnAuth = findViewById(R.id.btnAuth)

        biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        applicationContext,
                        "Authentication error: $errString", Toast.LENGTH_SHORT
                    )
                        .show()
                    mText.text = "Auth Error"
                    mImage.setColorFilter(Color.RED)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(
                        applicationContext,
                        "Authentication succeeded!", Toast.LENGTH_SHORT
                    )
                        .show()
                    mText.text = "Welcome Auth!"
                    mImage.setColorFilter(Color.BLUE)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        applicationContext, "Authentication failed",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    mText.text = "Auth Failed"
                    mImage.setColorFilter(Color.RED)
                }

            })


        promptInfo = BiometricPrompt.PromptInfo.Builder().apply {
            setTitle("Biometric login for my app")
            setSubtitle("Log in using your biometric credential")
            setNegativeButtonText("Use account password")
        }.build()

//        Set click
        mBtnAuth.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }

    }
}