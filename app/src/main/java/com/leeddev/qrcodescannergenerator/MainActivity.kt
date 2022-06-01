package com.leeddev.qrcodescannergenerator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.leeddev.qrcodescannergenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var binding:ActivityMainBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        onClickListener()
    }
    fun initView(){
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
    }

    fun onClickListener(){
        binding?.qrCodeScanner?.setOnClickListener{
            val intent= Intent(this,QRCodeScanner::class.java)
            startActivity(intent)
        }
        binding?.qrCodeGenerator?.setOnClickListener{
            val intent = Intent(this,QRCodeGenerator::class.java)
            startActivity(intent)
        }
        binding?.whatsappWebscan?.setOnClickListener{
            val intent = Intent(this,WhatsappWeb::class.java)
            startActivity(intent)
            }
        binding?.directChat?.setOnClickListener{
            val intent = Intent(this,DirectChat::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}