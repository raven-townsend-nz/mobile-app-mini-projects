package nz.ac.canterbury.seng440.latelyapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

const val KEY = "f2ebe89b-b2a7-459a-b247-7236646d3a17"

class MainActivity : AppCompatActivity(), HeadlineAdapter.OnHeadlineListener {

    val parameters = mapOf("api-key" to KEY)
    private val url = parameterizeUrl("https://content.guardianapis.com/search", parameters)

    private lateinit var headlinesPicker: RecyclerView
    private lateinit var headlineAdapter: HeadlineAdapter
    private lateinit var reloadButton: Button
    private var headlines: List<Headline> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        headlinesPicker = findViewById(R.id.headlinesPicker)
        headlineAdapter = HeadlineAdapter(headlines, this)
        headlinesPicker.adapter = headlineAdapter

        //
        // 4. Call reloadHeadlines when Reload button clicked
        //

        reloadButton = findViewById(R.id.reloadButton)
        reloadButton.setOnClickListener {
            reloadHeadlines()
        }

    }

    //
    // 3. Call getJson inside of lifecycleScope.launch
    //

    private fun reloadHeadlines() {
        lifecycleScope.launch {
            val result = getJson(url)
            val articles = result.getJSONObject("response").getJSONArray("results")
            headlines = (0 until articles.length()).map { i ->
                val headline = articles.getJSONObject(i)
                Headline(headline.getString("webTitle"), headline.getString("webUrl"))
            }
            headlineAdapter.setData(headlines)
        }
    }


    override fun onHeadlineClick(position: Int) {
        headlineAdapter.setSelectedIndex(position)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(headlines[position].url))
        startActivity(intent)
    }

}