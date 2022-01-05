package com.example.happybirthday

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FileUtils {

    companion object {

        fun getFile(context: Context, view: View): File? {
            val bitmap = getBitmapFromView(view)
            return bitmap?.let { bitmapToFile(bitmap, context) }
        }

        private fun getBitmapFromView(view: View): Bitmap? {
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            view.draw(canvas)
            return bitmap
        }

        private fun bitmapToFile(bitmap: Bitmap, context: Context): File? {
            //create a file to write bitmap data
            var file: File? = null
            return try {
                val path = context.filesDir.toString()
                val directoryName = "$path/imagesnanit"
                val fileName = "/$directoryName/anniversary.png"
                file = File(fileName)
                val directory = File(directoryName)
                if (!directory.exists()) {
                    directory.mkdir()
                    if (!file.exists()) {
                        file.parentFile.mkdir()
                        file.createNewFile()
                    }
                }
                //Convert bitmap to byte array
                val bos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
                val bitmapdata = bos.toByteArray()

                //write the bytes in file
                val fos = FileOutputStream(file)
                fos.write(bitmapdata)
                fos.flush()
                fos.close()
                file
            } catch (e: Exception) {
                e.printStackTrace()
                file
            }
        }
    }
}