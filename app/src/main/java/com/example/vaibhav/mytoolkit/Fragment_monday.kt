package com.example.vaibhav.mytoolkit

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.charset.Charset
import kotlinx.android.synthetic.main.fragment_fragment_time_table.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Fragmenttimetable : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_fragment_time_table, container, false)
        val path = activity!!.getExternalFilesDir("Setup")
        val file = File(path, "timetable.txt")
        val fileInputStream:FileInputStream = FileInputStream(file)
        var data = fileInputStream.readBytes().toString(Charset.defaultCharset())
        var dataInParts = data.split("\n")
        var textview = "textView"
        var textviewNo = 2  //First TextView contains time of class and second TextView contains its name. (see fragment_fragment_time_table.xml)

        val day = "monday"
        val resourceIdentifierHeading = resources.getIdentifier((textview+"0"), "id", context!!.getPackageName())
        val txtViewHeading = view.findViewById<TextView>(resourceIdentifierHeading)
        txtViewHeading.setText(day.capitalize())

        var found = 0
        for (value in dataInParts){
            if(value == day)
            {
                found = 1
                continue
            }
            if(textviewNo < 19 && found == 1)   //There are 18 TextViews
            {
                val resourceIdentifier = resources.getIdentifier((textview+textviewNo.toString()), "id", context!!.getPackageName())
                println(resourceIdentifier)
                val txtView = view.findViewById<TextView>(resourceIdentifier)
                val str = value
                txtView.setText(str)
                textviewNo+=2   //We access every second TextView (see resourceIdentifier declaration)
            }
        }

        return view
    }
}
