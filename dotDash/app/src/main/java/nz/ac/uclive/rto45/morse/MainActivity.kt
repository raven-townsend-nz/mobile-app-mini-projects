package nz.ac.uclive.rto45.morse

import android.media.AudioManager
import android.media.ToneGenerator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main)

        val dashButton: Button = findViewById(R.id.dashButton)
        val dotButton: Button = findViewById(R.id.dotButton)

        val toner = ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)

        val dotTime = 200
        val dashTime = 3 * dotTime

        dashButton.setOnClickListener {
            toner.startTone(ToneGenerator.TONE_SUP_DIAL, dashTime)
        }

        dotButton.setOnClickListener {
            toner.startTone(ToneGenerator.TONE_SUP_DIAL, dotTime)
        }

    }
}