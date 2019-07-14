package com.example.vaibhav.mytoolkit

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import org.jsoup.Jsoup
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset

class fetchTimeTable : AsyncTask<Void, Void, String>(){

    override fun doInBackground(vararg p0: Void?): String {
        var returnValue:String
        try{
            val connection = Jsoup.connect("https://raw.githubusercontent.com/Hunter0098/MyToolKit/master/TimeTable/timetable.txt")
            val getHtml = connection.get().html()
            val parsedHtml = Jsoup.parse(getHtml)
            val timetableElement = parsedHtml.select("body")
            val timetableValue = timetableElement.text()
            returnValue = timetableValue
        }catch (e:Exception){
            returnValue = "error"
        }

        return returnValue
    }
}

class SetupTimeTable : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setup_time_table)

        val actionbar = supportActionBar
        actionbar!!.title = "Timetable SetupAttendance"
        actionbar.setDisplayHomeAsUpEnabled(true)

        var data:String
        val editText = findViewById<EditText>(R.id.editText)

        try{
            val path = getExternalFilesDir("SetupAttendance")
            val file = File(path, "timetable.txt")
            val fileInputStream: FileInputStream = FileInputStream(file)
            data = fileInputStream.readBytes().toString(Charset.defaultCharset())
            fileInputStream.close()
            editText.setText(data)
        }catch (e:Exception){
            println("Can't read file")
        }

        editText.addTextChangedListener(object:TextWatcher{

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //...
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                data = editText.text.toString()
                val path = getExternalFilesDir("SetupAttendance")
                val file = File(path, "timetable.txt")
                val fileOutputStream = FileOutputStream(file)
                fileOutputStream.write(data.toByteArray())
                fileOutputStream.close()
            }

            override fun afterTextChanged(p0: Editable?) {
                //...
            }
        })

        val downloadButton = findViewById<Button>(R.id.downloadTimetableBtn)

        downloadButton.setOnClickListener {
            val asynctask = fetchTimeTable()
            var timetableString = asynctask.execute().get()
            if(timetableString == "error")
            {
                Toast.makeText(this, "Network error!\nPlease check your internet connection.", Toast.LENGTH_LONG).show()
            }
            else
            {
                timetableString = timetableString.replace(", ", "\n")
                editText.setText(timetableString)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
