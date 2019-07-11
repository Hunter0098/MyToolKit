package com.example.vaibhav.mytoolkit

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_my_notes.*
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception
import java.nio.charset.Charset

class MyNotes : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_notes)

        val backButtonInActionbar = supportActionBar
        backButtonInActionbar!!.title = "MyNotes"
        backButtonInActionbar.setDisplayHomeAsUpEnabled(true)

        var edittext = findViewById<EditText>(R.id.editText3)
        var fileName = "MyNotes.txt"
        var fileOutputStream:FileOutputStream
        var fileInputStream:FileInputStream

        try {
            fileInputStream = openFileInput(fileName)
            var dataInFile = fileInputStream.readBytes().toString(Charset.defaultCharset())
            edittext.setText(dataInFile)
        }catch (e:Exception){
            edittext.hint = "File doesn't exist yet. Try entering something\nAll changes will automatically be saved."
        }

        edittext.addTextChangedListener(object:TextWatcher{
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var dataToSave = edittext.text.toString()
                fileOutputStream = openFileOutput(fileName, Context.MODE_PRIVATE)
                fileOutputStream.write(dataToSave.toByteArray())
            }

            override fun afterTextChanged(p0: Editable?) {
                //....
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //....
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
