package com.haxxan.demoqrscanner

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val QR_CODE_REQUEST_CODE: Int = 786
    private lateinit var context: Context
    private lateinit var btnScan: Button
    private lateinit var tvInfo: TextView

    private lateinit var qrLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnScan = findViewById(R.id.btnScan)
        tvInfo = findViewById(R.id.tvInfo)
        context = this

        initLauncher()


        btnScan.setOnClickListener { scanQR() }

    }

    private fun initLauncher() {
        qrLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val qrCodeData = result.data?.getStringExtra("QR_CODE_DATA")
                    tvInfo.text = qrCodeData
                }
            }
    }

    private fun scanQR() {
        val scannerIntent = Intent(this, QrCodeScannerActivity::class.java)
        qrLauncher.launch(scannerIntent)
 }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QR_CODE_REQUEST_CODE && resultCode == RESULT_OK) {
            val qrCodeData = data?.getStringExtra("QR_CODE_DATA")
            tvInfo.text = "Result : " + qrCodeData
        }
    }

}