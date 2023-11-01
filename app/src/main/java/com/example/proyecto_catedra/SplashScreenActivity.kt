package com.example.proyecto_catedra

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        val rotatingImage = findViewById<ImageView>(R.id.rotatingImage)
        val rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_animation)

        rotatingImage.startAnimation(rotation)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}