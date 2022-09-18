package nz.ac.canterbury.rto45.kana_san

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

const val QUIZ = 100

class MainActivity : AppCompatActivity() {

    private lateinit var scoreText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        scoreText = findViewById(R.id.scoreText)
        val hiraganaButton: Button = findViewById(R.id.hiraganaButton)
        val katakanaButton: Button = findViewById(R.id.katakanaButton)

        katakanaButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("alphabet", Japanese.katakana)
            startActivityForResult(intent, QUIZ)
        }

        hiraganaButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("alphabet", Japanese.hiragana)
            startActivityForResult(intent, QUIZ)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == QUIZ && resultCode == Activity.RESULT_OK) {
            scoreText.text = "Last score: ${data?.getIntExtra("score", 0)}"
        }
    }


}