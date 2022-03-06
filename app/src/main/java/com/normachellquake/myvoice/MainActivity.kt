package com.normachellquake.myvoice

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private lateinit var ivItemImage: ImageView
    private lateinit var tvItemText: TextView
    private lateinit var btnAddImage: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivItemImage = findViewById(R.id.ivItemImage)
        tvItemText = findViewById(R.id.tvItemText)
        btnAddImage = findViewById(R.id.btnAddImage)

        val getAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val uri = it?.data?.data
            ivItemImage.setImageURI(uri)
            tvItemText.text = "New Pic"
        }

        btnAddImage.setOnClickListener{
            requestPermissions()
            val intent = Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
            }

            getAction.launch(intent)
        }
    }

    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasReadExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    private fun hasCameraPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    private fun hasRecordAudioPermission() =
        ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED

    private fun requestPermissions() {
        var permissionsToRequest = mutableListOf<String>()
        if (!hasWriteExternalStoragePermission()){
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!hasReadExternalStoragePermission()){
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!hasCameraPermission()){
            permissionsToRequest.add(Manifest.permission.CAMERA)
        }
        if (!hasRecordAudioPermission()){
            permissionsToRequest.add(Manifest.permission.RECORD_AUDIO)
        }

        if(permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toTypedArray(), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 0 && grantResults.isNotEmpty()){
            for(i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("PermissionsRequest", "${permissions[i]} granted")
                }
            }
        }
    }
}