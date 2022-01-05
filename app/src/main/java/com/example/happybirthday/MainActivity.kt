package com.example.happybirthday

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var takePictureResultLauncher: ActivityResultLauncher<Intent>? = null
    private var galleryResultLauncher: ActivityResultLauncher<Intent>? = null
    private var context: Context? = null

    companion object {
        const val EXTERNAL_STORAGE = 111
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        supportActionBar?.hide()
        if(savedInstanceState == null){
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
            navController = navHostFragment.navController
        }
    }

    fun navigateTo(action: Int) {
        navController.navigate(action)
    }

    inner class OnIconClicked(
        private val contextFromFragment: Context,
        private val pictureResultLauncher: ActivityResultLauncher<Intent>,
        private val galleryPicResultLauncher: ActivityResultLauncher<Intent>
    ) : View.OnClickListener {
        override fun onClick(v: View?) {
            takePictureResultLauncher = pictureResultLauncher
            galleryResultLauncher = galleryPicResultLauncher
            context = contextFromFragment
            checkPermission()
        }
    }

    fun showShareOptions(
        context: Context,
        viewPicture: View
    ) {
        val file = FileUtils.getFile(context, viewPicture)
        file?.let {
            val uri =
                FileProvider.getUriForFile(
                    context,
                    "com.example.happybirthday.fileprovider",
                    file
                )
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.apply {
                putExtra(Intent.EXTRA_STREAM, uri)
                type = "image/*"
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
        }
    }

    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this@MainActivity,
                READ_EXTERNAL_STORAGE) !=
            PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(READ_EXTERNAL_STORAGE), EXTERNAL_STORAGE)
            } else {
                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(READ_EXTERNAL_STORAGE), EXTERNAL_STORAGE)
            }
        } else {
            context?.let {
                PictureUtils.showChoosePictureDialog(
                    it,
                    takePictureResultLauncher,
                    galleryResultLauncher
                )
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {
                    if ((ContextCompat.checkSelfPermission(this@MainActivity,
                            READ_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_GRANTED)) {
                        context?.let {
                            PictureUtils.showChoosePictureDialog(
                                it,
                                takePictureResultLauncher,
                                galleryResultLauncher
                            )
                        }
                    }
                }
                return
            }
        }
    }

    fun hideKeyboard() {
        val imm: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        view?.let {
            view = View(this)
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}