package com.example.vaibhav.mytoolkit

import android.graphics.Color
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import org.jsoup.Jsoup
import org.jsoup.Connection
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_attendance.*
import java.io.FileInputStream
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*

class FetchAttendance : AsyncTask<String, Void, String>(){

    override fun doInBackground(vararg p0: String?): String {
        try {
            val userInfoString = Arrays.toString(p0)
            val userInfoStringParts = userInfoString.split("\n")
            var connection = Jsoup.connect("https://webkiosk.jiit.ac.in/")
            //println(connection)
            var getHtml = connection.get().html()
            var conn = connection.method(Connection.Method.GET).execute()
            val cook = conn.cookies()
            var parsedHtml = Jsoup.parse(getHtml)
            var captchaElement = parsedHtml.select("font[face=\"casteller\"]")
            //println(captchaElement)
            var captchaValue = captchaElement.text()
            //println(captchaValue)
            var login = Jsoup.connect("https://webkiosk.jiit.ac.in/CommonFiles/UserAction.jsp")
                .data("cookieexists", "false")
                .data("InstCode", userInfoStringParts[1])
                .data("UserType", "S")
                .data("MemberCode", userInfoStringParts[2])
                .data("DATE1", userInfoStringParts[3])    //Can be removed
                .data("Password", userInfoStringParts[4])
                .data("txtcap", captchaValue)   //Can be removed
                .cookies(cook)
                .post()

            //println(login)
            var studentAttendence = Jsoup.connect("https://webkiosk.jiit.ac.in/StudentFiles/Academic/StudentAttendanceList.jsp")
                    .cookies(cook).get().html()

            var elements = ""
            parsedHtml = Jsoup.parse(studentAttendence)
            //println(parsedHtml)
            var attendenceString: String = ""
            var attendenceElements = parsedHtml.select("td")
            for (elements in attendenceElements) {
                if (elements.text() == "Exam Code 2019EVESEM 2018ODDSEM 2018EVESEM 2017ODDSEM" || elements.text() == "&nbsp;") {
                    continue
                } else {
                    attendenceString += elements.text() + "\n"
                    //println(elements.text())
                }
            }
            return attendenceString
        }catch (e:Exception){
            return "error"
        }
    }

    override fun onPreExecute() {
        super.onPreExecute()
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
    }
}

class AttendanceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attendance)

        val fileInputStream:FileInputStream = openFileInput("userSetupDetails.txt")
        val userInfo = fileInputStream.readBytes().toString(Charset.defaultCharset())

        //For back button in actionbar
        val backActionBar = supportActionBar
        backActionBar!!.title = "Attendance"
        backActionBar.setDisplayHomeAsUpEnabled(true)

        val asynctask = FetchAttendance()
        val attendanceString = asynctask.execute(userInfo).get()    //Get makes main thread wait thus makes it synchronous
        val delimiter = "\n"
        val attendanceStringParts = attendanceString.split(delimiter)
        //println(attendanceStringParts)

        if(attendanceString == "error")
        {
            Toast.makeText(this, "Wrong login details or Network Error!\nPlease check your network connection", Toast.LENGTH_LONG).show()
            finish()
        }

        var index = ""
        var i = 0
        var counter = 0
        var backgroundColor = 0
        for (i in i..attendanceStringParts.size-1)
        {
            var textview = TextView(this)
            textview.textSize = 15f

            if(i > 4 && i < 11)
            {
                //Sno Subject Lecture+Tutorial(%) Lecture(%) Tutorial(%) -> Indices are skipped
                continue
            }

            if(i >= 11 && i < 59)
            {
                if(counter == 0)
                {
                    index = "\n" + attendanceStringParts[5] + " :"
                    textview.setTextColor(Color.rgb(0, 255, 0))
                }
                else if(counter == 1)
                {
                    index = attendanceStringParts[6] + " :"
                    textview.setTextColor(Color.YELLOW)
                }
                else if(counter == 2)
                {
                    index = attendanceStringParts[7] + " :"
                }
                else if(counter == 3)
                {
                    index = attendanceStringParts[8] + " :"
                }
                else if(counter == 4)
                {
                    index = attendanceStringParts[9] + " :"
                }
                else if(counter == 5)
                {
                    index = attendanceStringParts[10] + " :"
                }
                if(counter < 5)
                {
                    counter++
                }
                else
                {
                    counter = 0
                }
            }
            else
            {
                index = "\n"
            }

            textview.text = index + attendanceStringParts[i]
            attendanceXml.addView(textview)
        }


    }

    //override for back button on action bar
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
