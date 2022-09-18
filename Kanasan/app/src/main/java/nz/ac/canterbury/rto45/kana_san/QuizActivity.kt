package nz.ac.canterbury.rto45.kana_san

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class QuizActivity : AppCompatActivity() {

    private lateinit var kanaText: TextView
    private lateinit var romajiBox: EditText
    private lateinit var alphabet: Array<String>
    private var currentIndex: Int = 0
    private var round: Int = 0
    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        kanaText = findViewById(R.id.kanaText)
        romajiBox = findViewById(R.id.romajiBox)

        romajiBox.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (romajiBox.text.toString() == Japanese.romaji[currentIndex]) {
                    nextRound()
                    Toast.makeText(this, "Right!", Toast.LENGTH_SHORT).show()
                    score += 10
                } else {
                    Toast.makeText(this, "Wrong!", Toast.LENGTH_SHORT).show()
                    score -= 5
                }
                true
            } else {
                false
            }

        }

        alphabet = intent.getStringArrayExtra("alphabet") as Array<String>

        nextRound()
    }

    fun nextRound() {
        if (round >= 5) {
            val result = Intent()
            result.putExtra("score", score)
            setResult(Activity.RESULT_OK, result)
            finish()
        } else {
            currentIndex = Random.nextInt(alphabet.size)
            kanaText.text = alphabet[currentIndex]

            romajiBox.setText("")
            romajiBox.requestFocus()
            round += 1
        }
    }
}