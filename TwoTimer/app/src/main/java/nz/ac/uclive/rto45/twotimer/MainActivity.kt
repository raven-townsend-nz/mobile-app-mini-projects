package nz.ac.uclive.rto45.twotimer

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.icu.util.TimeZone
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var time1: TextView
    private lateinit var time2: TextView
    private lateinit var picker1: Spinner
    private lateinit var picker2: Spinner

    private lateinit var handler: Handler
    private lateinit var updateTask: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        time1 = findViewById(R.id.time1)
        time2 = findViewById(R.id.time2)
        picker1 = findViewById(R.id.zone1)
        picker2 = findViewById(R.id.zone2)

        val timeZones = TimeZone.getAvailableIDs()
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, timeZones)
        picker1.adapter = adapter
        picker2.adapter = adapter

        val listener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                syncTimes()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                syncTimes()
            }
        }
        picker1.onItemSelectedListener = listener
        picker2.onItemSelectedListener = listener

        picker1.setSelection(timeZones.indexOf("Pacific/Auckland"))
        picker2.setSelection(timeZones.indexOf("America/Chicago"))


        handler = Handler(Looper.getMainLooper())

        updateTask = Runnable {
            syncTimes()
            Log.d("FOO", "Updating!")
            handler.postDelayed(updateTask, 1000)
        }
    }

    override fun onStart() {
        super.onStart()
        handler.post(updateTask)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(updateTask)
    }



    private fun syncTimes() {
        val formatter = SimpleDateFormat("d MMMM HH:mm")
        val today = Calendar.getInstance()

        var timeZone = TimeZone.getTimeZone(picker1.selectedItem.toString())
        formatter.timeZone = timeZone
        time1.text = formatter.format(today.time)

        timeZone = TimeZone.getTimeZone(picker2.selectedItem.toString())
        formatter.timeZone = timeZone
        time2.text = formatter.format(today.time)
    }
}

