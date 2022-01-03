package com.example.happybirthday

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        supportActionBar?.hide()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    fun navigateTo(action: Int) {
        navController.navigate(action)
    }

    inner class OnIconClicked(
        private val context: Context,
        private val takePictureResultLauncher: ActivityResultLauncher<Intent>,
        private val galleryResultLauncher: ActivityResultLauncher<Intent>
    ) : View.OnClickListener {
        override fun onClick(v: View?) {
            PictureUtils.showChoosePictureDialog(
                context,
                takePictureResultLauncher,
                galleryResultLauncher
            )
        }
    }

}