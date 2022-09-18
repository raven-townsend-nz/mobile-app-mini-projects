package nz.ac.canterbury.seng440.connect440live

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FriendsAdapter(private var friends: List<Friend>, private val onFriendListener: OnFriendListener)
    : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View, val onFriendListener: OnFriendListener)
        : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val textView: TextView

        init {
            textView = itemView.findViewById(R.id.friend_text)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onFriendListener.onFriendClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.friend_item, parent, false)
        return FriendViewHolder(view, onFriendListener)
    }

    override fun onBindViewHolder(viewHolder: FriendViewHolder, position: Int) {
        viewHolder.textView.text = friends[position].toString()
    }

    override fun getItemCount() = friends.size

    // STEP 9
    fun setData(newFriends: List<Friend>) {
        friends = newFriends
        notifyDataSetChanged()
    }

    interface OnFriendListener {
        fun onFriendClick(position: Int)
    }
}