package com.example.happybirthday

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher


class PictureUtils {

    companion object {
        fun showChoosePictureDialog(
            context: Context,
            takePictureResultLauncher: ActivityResultLauncher<Intent>?,
            galleryResultLauncher: ActivityResultLauncher<Intent>?
        ) {
            val getImageFrom: AlertDialog.Builder = AlertDialog.Builder(context)
            getImageFrom.setTitle(context.resources.getString(R.string.select))
            val opsChars = arrayOf<CharSequence>(
                context.resources.getString(R.string.take_picture),
                context.resources.getString(R.string.from_gallery)
            )
            getImageFrom.setItems(opsChars) { dialog, which ->
                if (which == 0) { // take picture
                    val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    takePictureResultLauncher?.launch(cameraIntent)
                } else if (which == 1) { // choose from gallery
                    val galleryIntent = Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    galleryIntent.type = "image/*"
                    galleryResultLauncher?.launch(galleryIntent)
                }
                dialog.dismiss()
            }
            getImageFrom.show()
        }
    }
}