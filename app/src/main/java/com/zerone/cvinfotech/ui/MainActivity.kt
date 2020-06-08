package com.zerone.cvinfotech.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zerone.cvinfotech.R
import com.zerone.cvinfotech.permissionUtils.PermissionUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val REQUEST_PICTURE_CAPTURE = 101
    lateinit var bitmap: Bitmap

    private val strPermission = arrayOf(
        "android.permission.CAMERA",
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!PermissionUtils.checkMultiplePermissionsGranted(this, strPermission)) {
            PermissionUtils.requestMultiplePermissions(this, strPermission)
        }

        saveImage.setOnClickListener {
            if(this::bitmap.isInitialized) {
                createDirectoryAndSaveFile(bitmap)
            }
        }

        gallery.setOnClickListener {
            startActivity(Intent(this, GalleryActivity::class.java))
        }

        capture.setOnClickListener {
            openCameraActivity()
        }
    }

    private fun openCameraActivity() {

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(cameraIntent, REQUEST_PICTURE_CAPTURE)
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICTURE_CAPTURE && resultCode == Activity.RESULT_OK) {
            bitmap = data!!.extras!!.get("data") as Bitmap
            image.setImageBitmap(bitmap)
        }
    }


    private fun createDirectoryAndSaveFile(
        imageToSave: Bitmap
    ) {
        val timeStamp: String =
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val imageFileName = "image_" + timeStamp + "_" + System.currentTimeMillis() + "_.jpg"
        val direct = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString()

        val wallpaperDirectory = File(direct)
        wallpaperDirectory.mkdirs()
        val file = File(direct, imageFileName)
        if (file.exists()) {
            file.delete()
        }
        try {
            val out = FileOutputStream(file)
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        Toast.makeText(this,"Saved",Toast.LENGTH_SHORT).show()
    }


}