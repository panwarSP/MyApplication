package com.example.myapplication

import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var amountEt: EditText? = null
    var noteEt: EditText? = null
    var nameEt: EditText? = null
    var upiIdEt: EditText? = null
    var send: Button? = null

    var size = 0

    private var gridViewMonthsAdapter: adapter? = null
    private var selectedApp = ""
    private var appIds  = arrayListOf<String>()
    private var appLogos = arrayListOf<Drawable>()
    var i = 0

    val UPI_PAYMENT = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeViews()
        send!!.setOnClickListener { //Getting the values from the EditTexts
            val amount = amountEt!!.text.toString()
            val note = noteEt!!.text.toString()
            val name = nameEt!!.text.toString()
            val upiId = upiIdEt!!.text.toString()
            appIds.clear()
            appLogos.clear()
            payUsingUpi(amount, upiId, name, note)
        }
    }

    fun initializeViews() {
        send = findViewById(R.id.send)
        amountEt = findViewById(R.id.amount_et)
        noteEt = findViewById(R.id.note)
        nameEt = findViewById(R.id.name)
        upiIdEt = findViewById(R.id.upi_id)
    }

    fun payUsingUpi(amount: String?, upiId: String?, name: String?, note: String?) {
        val uri = Uri.parse("upi://pay").buildUpon()
            .appendQueryParameter("pa", upiId)
            .appendQueryParameter("pn", name)
            .appendQueryParameter("tn", note)
            .appendQueryParameter("am", amount)
            .appendQueryParameter("cu", "INR")
            .build()
        val upiPayIntent = Intent(Intent.ACTION_VIEW)
        upiPayIntent.data = uri

        // will always show a dialog to user to choose an app

        val resolveInfoList : MutableList<ResolveInfo> = packageManager.queryIntentActivities(
            upiPayIntent,
            0
        )


        for (resolveInfo in resolveInfoList) {
            //you will get all information you need from `resolveInfo`
            //eg:for package name - resolveInfo.activityInfo.packageName
            Log.d("UPIAPPS", resolveInfo.loadLabel(packageManager).toString())
            Log.d("UPIAPPS", resolveInfo.activityInfo.packageName)
            Log.d("UPIIMAGE", resolveInfo.loadIcon(packageManager).toString())


                appIds.add(resolveInfo.loadLabel(packageManager).toString())
                appLogos.add(resolveInfo.loadIcon(packageManager))


        }

        println("appids = ${appIds}")
        println(i)
        size = appIds.size
        i = size


        recyclerApps.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )




        println("appsize = ${appIds.size}")
        gridViewMonthsAdapter = adapter(this, appIds, appLogos, object : ClickListener {
            override fun onClick(position: Int, month: String) {
                selectedApp =
                    appIds[position]
            }
        })

        if (recyclerApps != null) {
            recyclerApps.adapter = gridViewMonthsAdapter
        }
    }





}