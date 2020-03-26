package com.codel.covid

import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.codel.covid.databinding.ActivitySplashScreenBinding
import com.codel.covid.main.MainActivity

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideUi()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen)

        val animator = AnimatorInflater.loadAnimator(this, R.animator.scale)
        animator.setTarget(binding.stayAr)
        animator.start()

        val animator2 = AnimatorInflater.loadAnimator(this, R.animator.scale)
        animator2.setTarget(binding.stayEn)
        animator2.start()
        Glide
            .with(this)
            .load(R.drawable.logo)
            .into(binding.logoImageView)


        object : CountDownTimer(1500, 1500) {
            override fun onFinish() {
                transitionToLoginScreen()
            }

            override fun onTick(millisUntilFinished: Long) {
            }

        }.start()


    }

    private fun hideUi() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


    private fun transitionToLoginScreen() {
        val logoPair =
            Pair<View, String>(
                binding.logoImageView, resources.getString(R.string.logo_trans)
            )
        val gradientBackgroundPair =
            Pair<View, String>(
                binding.layout,
                resources.getString(R.string.bg_trans)
            )


        val activityOptionsCompat =
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                this@SplashScreenActivity,
                logoPair, gradientBackgroundPair
            )


        val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
        startActivity(intent, activityOptionsCompat.toBundle())
        finish()
    }

}
