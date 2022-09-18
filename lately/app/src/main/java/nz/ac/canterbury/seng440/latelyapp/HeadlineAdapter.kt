package nz.ac.canterbury.seng440.latelyapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HeadlineAdapter(private var headlines: List<Headline>,
                      private val onHeadlineListener: OnHeadlineListener)
    : RecyclerView.Adapter<HeadlineAdapter.HeadlineViewHolder>() {

    private var selectedIndex = RecyclerView.NO_POSITION

    class HeadlineViewHolder(view: View,
                             val onHeadlineListener: OnHeadlineListener)
        : RecyclerView.ViewHolder(view), View.OnClickListener {
        var headlineText: TextView = view.findViewById(R.id.headlineText)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onHeadlineListener.onHeadlineClick(adapterPosition)
        }

        var isActive: Boolean = false
            set(value) {
                field = value
                itemView.setBackgroundColor(if (field) Color.LTGRAY else Color.TRANSPARENT)
            }
    }

    override fun getItemCount(): Int = headlines.size

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HeadlineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.headline_item, parent, false)
        return HeadlineViewHolder(view, onHeadlineListener)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, i: Int) {
        holder.headlineText.text = headlines[i].text
        holder.isActive = selectedIndex == i
    }

    fun setData(newHeadlines: List<Headline>) {
        headlines = newHeadlines
        notifyDataSetChanged()
    }

    fun setSelectedIndex(index: Int) {
        val oldSelectedIndex = selectedIndex
        selectedIndex = index
        notifyItemChanged(selectedIndex)
        notifyItemChanged(oldSelectedIndex)
    }

    interface OnHeadlineListener {
        fun onHeadlineClick(position: Int)
    }
}


