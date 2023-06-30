package com.asthana.radius

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.asthana.radius.databinding.ActivityLauncherBinding


class Launcher : AppCompatActivity() {

    lateinit var binding: ActivityLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Intent To MainActivity
        android.os.Handler().postDelayed({
            // Start the new activity with a custom animation
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()
        }, 3000)



        val imageView = binding.radiuslogo

// Set initial properties
        imageView.alpha = 0f
        imageView.rotation = -45f

// Create an ObjectAnimator for rotation
        val rotationAnimator = ObjectAnimator.ofFloat(imageView, "rotation", -45f, 0f)
        rotationAnimator.duration = 1000

// Create an ObjectAnimator for fade-in effect
        val alphaAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f)
        alphaAnimator.duration = 2000

// Create an AnimatorSet to combine the animations
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(rotationAnimator, alphaAnimator)
        animatorSet.start()


        val logoTitleTextView = findViewById<TextView>(R.id.logo_title)
        val sloganTextView = findViewById<TextView>(R.id.slogan)

// Create fade-in animation
        val fadeInAnimation = AlphaAnimation(0f, 1f)
        fadeInAnimation.duration = 2000

// Create fade-out animation
        val fadeOutAnimation = AlphaAnimation(1f, 0f)
        fadeOutAnimation.duration = 3000
        fadeOutAnimation.startOffset = 2000 // Delay before starting the fade-out animation
        fadeOutAnimation.fillAfter = true

// Create AnimationSet to combine fade-in and fade-out animations
        val animationSet = AnimationSet(true)
        animationSet.addAnimation(fadeInAnimation)
        animationSet.addAnimation(fadeOutAnimation)

// Apply animation to the TextViews
        logoTitleTextView.startAnimation(animationSet)
        sloganTextView.startAnimation(animationSet)


    }
}