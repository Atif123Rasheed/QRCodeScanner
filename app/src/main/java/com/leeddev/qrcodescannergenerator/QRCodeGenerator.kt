package com.leeddev.qrcodescannergenerator

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.leeddev.qrcodescannergenerator.databinding.ActivityQrcodeGeneratorBinding
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class QRCodeGenerator : AppCompatActivity() {


    private var binding: ActivityQrcodeGeneratorBinding? = null

    private val externalStoragePermissionRequestCode = 1
    private var qrImage: Bitmap? = null
    private val editText: EditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        onClickListener()


        if (!checkPermissionForExternalStorage()) {
            requestPermissionForExternalStorage()
        }
    }

    private fun initView() {
        binding = ActivityQrcodeGeneratorBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    private fun onClickListener() {
        binding?.btnQrcodeGenerate?.setOnClickListener {
            if (editText?.text.toString().isNotEmpty()) {
                generateQRCode()
            } else {
                editText?.error = "Insert Text"
            }
        }
        binding?.btnSave?.setOnClickListener {
            if (!checkPermissionForExternalStorage()) {
                Toast.makeText(this, "Storage Permission Required", Toast.LENGTH_SHORT).show()
            } else {
                if (qrImage != null) {
                    saveImage(qrImage!!)
                }
            }
        }
    }

    private fun generateQRCode() {

        qrImage = net.glxn.qrgen.android.QRCode.from(editText?.text.toString()).bitmap()
        if (qrImage != null) {
            binding?.ivQrcode?.setImageBitmap(qrImage)
            binding?.btnSave?.visibility = View.VISIBLE
        }
    }

    private fun checkPermissionForExternalStorage(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionForExternalStorage() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        ) {
            Toast.makeText(this, "Storage Permission Required", Toast.LENGTH_SHORT).show()
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                externalStoragePermissionRequestCode
            )
        }
    }

    private fun saveImage(image: Bitmap): String {

        var savedImagePath: String? = null
        val imageFileName = "QR" + getTimeStamp() + ".jpg"
        val storageDirectory = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
            "/QRGenerator"
        )
        var success = true
        if (!storageDirectory.exists()) {
            success = storageDirectory.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDirectory, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fileOut = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fileOut)
                fileOut.close()
            }
            catch (e: IOException) {
                e.printStackTrace()
            }
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val file = File(savedImagePath)
            val contentUrl = Uri.fromFile(file)
            mediaScanIntent.data = contentUrl
            sendBroadcast(mediaScanIntent)
            Toast.makeText(
                this,
                "QR Image saved to Into folder: QRGenerator in gallery",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show()
        }
        return savedImagePath!!
    }

    fun getTimeStamp(): String? {
        val tsLong = System.currentTimeMillis() / 1000
        return tsLong.toString()
    }
}