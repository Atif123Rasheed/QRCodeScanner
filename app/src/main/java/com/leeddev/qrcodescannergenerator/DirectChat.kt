package com.leeddev.qrcodescannergenerator

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hbb20.CountryCodePicker
import java.net.URLEncoder

class DirectChat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_direct_chat)

        val btnSend: Button = findViewById(R.id.btn_send_message)
        val txtNumber: EditText = findViewById(R.id.et_mobile_number)
        val txtMsg: EditText = findViewById(R.id.et_massage)
        val countryCode:CountryCodePicker = findViewById(R.id.country_code_picker)

        btnSend.setOnClickListener {
            if (txtNumber.text.isNotEmpty() && txtMsg.text.isNotEmpty()) {
                val packageManager: PackageManager = packageManager
                val intent = Intent(Intent.ACTION_VIEW)
                val url =
                    "https://api.whatsapp.com/send?phone=" +countryCode.selectedCountryCode.toString()+ txtNumber.text.toString() + "&text=" + URLEncoder.encode(
                        txtMsg.text.toString(),
                        "UTF-8"
                    )
                intent.setPackage("com.whatsapp")
                intent.data = Uri.parse(url)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            } else {
                Toast.makeText(this, "Please Enter Message Or Number !!", Toast.LENGTH_LONG).show()
            }
        }
    }
}


