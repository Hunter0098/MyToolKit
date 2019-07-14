package com.example.vaibhav.mytoolkit


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File
import java.io.FileInputStream
import java.nio.charset.Charset


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Fragment_saturday : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_saturday, container, false)
        val path = activity!!.getExternalFilesDir("SetupAttendance")
        val file = File(path, "timetable.txt")
        val fileInputStream: FileInputStream = FileInputStream(file)
        var data = fileInputStream.readBytes().toString(Charset.defaultCharset())
        var dataInParts = data.split("\n")
        var textview = "textView"
        var textviewNo = 2

        val day = "saturday"
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
            if(textviewNo < 19 && found == 1)
            {
                val resourceIdentifier = resources.getIdentifier((textview+textviewNo.toString()), "id", context!!.getPackageName())
                println(resourceIdentifier)
                val txtView = view.findViewById<TextView>(resourceIdentifier)
                val str = value
                txtView.setText(str)
                textviewNo+=2
            }
        }

        return view
    }


}
