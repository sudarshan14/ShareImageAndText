package com.amlavati.shareimageandtext

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

class MainActivityKotlin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_kotlin)

        val imageView = findViewById<ImageView>(R.id.imageViewKotlin)

        findViewById<Button>(R.id.share).setOnClickListener {
            val bitmapDrawable = imageView.drawable as BitmapDrawable
            val bitmap = bitmapDrawable.bitmap
            shareImageandText(bitmap)
        }
    }


    private fun shareImageandText(bitmap: Bitmap) {
        val uri = getImageToShare(bitmap)
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, "subject")
        intent.putExtra(Intent.EXTRA_TEXT, "I am sharing image")
        intent.type = "image/png"
        startActivity(Intent.createChooser(intent, "share via"))

    }


    private fun getImageToShare(bitmap: Bitmap): Uri? {

        val imageFolder = File(cacheDir, "images")
        var uri: Uri? = null
        try {
            imageFolder.mkdirs()
            val file = File(imageFolder, "share_image.png")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
            outputStream.flush()
            outputStream.close()

            uri = FileProvider.getUriForFile(this, "com.amlavati", file)
        } catch (e: Exception) {
            Toast.makeText(this, "" + e.message, Toast.LENGTH_LONG).show()

        }
        return uri
    }
}