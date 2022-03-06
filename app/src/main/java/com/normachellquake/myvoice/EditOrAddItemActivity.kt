package com.normachellquake.myvoice

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class EditOrAddItemActivity : AppCompatActivity() {

    private lateinit var ivItemImageEdit: ImageView
    private lateinit var etItemTextEdit: TextView
    private lateinit var btnAddImage: Button
    private lateinit var btnSaveItem: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_or_add_item)

        ivItemImageEdit = findViewById(R.id.ivItemImageEdit)
        etItemTextEdit = findViewById(R.id.etItemTextEdit)
        btnAddImage = findViewById(R.id.btnAddImage)
        btnSaveItem = findViewById(R.id.btnSaveItem)

        val getAction = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            val uri = it?.data?.data
            ivItemImageEdit.setImageURI(uri)
        }

        btnAddImage.setOnClickListener{
            val intent = Intent(Intent.ACTION_GET_CONTENT).also {
                it.type = "image/*"
            }

            getAction.launch(intent)
        }

        btnSaveItem.setOnClickListener{
            val itemText = etItemTextEdit.text.toString()
//            val image = ivItemImageEdit

//            val item = Item(itemText)

            Intent(this, MainActivity::class.java).also {
//                it.putExtra("EXTRA_ITEM", item)
                it.putExtra("EXTRA_NAME", itemText)
                startActivity(it)
            }
        }
    }

}