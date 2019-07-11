package com.example.vaibhav.mytoolkit

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.webkit.WebView
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Wallpaper animation starts
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        val animDrawable = mainScrollView.background as AnimationDrawable
        animDrawable.setEnterFadeDuration(10)
        animDrawable.setExitFadeDuration(5000)
        animDrawable.start()
        //Wallpaper animation ends

        var timetableIsCorrect = true
        try{
            val path = getExternalFilesDir("Setup")
            val file = File(path, "timetable.txt")
            val fileInputStream:FileInputStream = FileInputStream(file)
            var data = fileInputStream.readBytes().toString(Charset.defaultCharset())
            fileInputStream.close()
            var dataSplit = data.split("\n")
            var noOfEntries = dataSplit.size
            timetableIsCorrect = true
            if(noOfEntries < 70)
            {
                throw Exception("TimeTable either doesn't exist or format is not correct")
            }
        }catch (e:Exception){
            timetableIsCorrect = false
            val alert = AlertDialog.Builder(this, R.style.AppTheme_AlertDialogue)
            alert.setTitle("Timetable not complete")
            alert.setIcon(android.R.drawable.ic_dialog_alert)
            alert.setMessage("Please complete your timetable. There should be at least 70 rows.")
            alert.setPositiveButton("Ok"){ dialogInterface, which ->
                val intent = Intent(this, SetupTimeTable::class.java)
                startActivity(intent)
            }
            val alertDialog: AlertDialog = alert.create()
            alertDialog.setCancelable(false)    //To prevent exiting alert by touching outside the box
            alertDialog.show()
        }

        try {
            val fileInputStream:FileInputStream = openFileInput("userSetupDetails.txt")
            val input = fileInputStream.readBytes().toString(Charset.defaultCharset())
            fileInputStream.close()
        }catch (e:Exception){
            val intent = Intent(this, Setup::class.java)
            startActivity(intent)
        }

        val timeTableOnClick = findViewById<Button>(R.id.button1)
        timeTableOnClick.setOnClickListener{
            if(timetableIsCorrect == true) {
                Toast.makeText(this, "Fetching your timetable", Toast.LENGTH_SHORT).show()
                val intentTimeTable = Intent(this, TimeTable::class.java)
                startActivity(intentTimeTable)
            }
            else{
                Toast.makeText(this, "Please fill the timetable using change timetable option and restart the app", Toast.LENGTH_LONG).show()
            }

        }

        val attendenceOnClick = findViewById<Button>(R.id.button2)
        attendenceOnClick.setOnClickListener {
            Toast.makeText(this, "Please wait while we extract your attendence", Toast.LENGTH_LONG).show()
            val intentAttendence = Intent(this, AttendanceActivity::class.java)
            startActivity(intentAttendence)
        }

        val notesOnClick = findViewById<Button>(R.id.button3)
        notesOnClick.setOnClickListener {
            Toast.makeText(this, "Opening notes..", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MyNotes::class.java)
            startActivity(intent)
        }

        val openWebkioskOnClick = findViewById<Button>(R.id.button4)
        openWebkioskOnClick.setOnClickListener {
            Toast.makeText(this, "Opening WebKiosk", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, WebKioskWebView::class.java)
            startActivity(intent)
        }

        val changeDetailsOnClick = findViewById<Button>(R.id.button7)
        changeDetailsOnClick.setOnClickListener {
            val intent = Intent(this, Setup::class.java)
            startActivity(intent)
        }

        val changeTimeTableOnClick = findViewById<Button>(R.id.button8)
        changeTimeTableOnClick.setOnClickListener {
            val intent = Intent(this, SetupTimeTable::class.java)
            startActivity(intent)
        }
    }
}
