package com.example.photooftheday

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.photooftheday.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    var handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.imageView.animate().rotationBy(750f)
            .setInterpolator(AccelerateDecelerateInterpolator()).setDuration(3000)
            .setListener(object : Animator.AnimatorListener {

                override fun onAnimationEnd(animation: Animator?) {
                   /* startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()*/
                }

                override fun onAnimationRepeat(animation: Animator?) {}
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationStart(animation: Animator?) {}
            })

        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }
}
