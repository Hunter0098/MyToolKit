package com.example.vaibhav.mytoolkit

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.io.FileOutputStream

class SetupAttendance : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setup_attendance)

        val actionbar = supportActionBar
        actionbar!!.title = "Attendance SetupAttendance"
        actionbar.setDisplayHomeAsUpEnabled(true)

        val buttonOnClick = findViewById<Button>(R.id.saveSetupDetails)
        buttonOnClick.setOnClickListener {
            val name = findViewById<EditText>(R.id.EditText1).text.toString() + "\n"
            val collegeId = findViewById<EditText>(R.id.EditText2).text.toString() + "\n"
            val enrollment = findViewById<EditText>(R.id.EditText3).text.toString() + "\n"
            val dob = findViewById<EditText>(R.id.EditText4).text.toString() + "\n"
            val password = findViewById<EditText>(R.id.EditText5).text.toString() + "\n"

            val fileOutputStream:FileOutputStream = openFileOutput("userSetupDetails.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(name.toByteArray())
            fileOutputStream.write(collegeId.toByteArray())
            fileOutputStream.write(enrollment.toByteArray())
            fileOutputStream.write(dob.toByteArray())
            fileOutputStream.write(password.toByteArray())
            fileOutputStream.close()
            finish()    //To end the current activity and return to previous one
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}


