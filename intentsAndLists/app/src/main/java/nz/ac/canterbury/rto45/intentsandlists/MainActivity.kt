package nz.ac.canterbury.rto45.intentsandlists

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.net.URLEncoder

class MainActivity : FriendsAdapter.OnFriendListener, AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        val friendAdapter = FriendsAdapter(friends, this)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = friendAdapter

        val permissions = arrayOf(Manifest.permission.CALL_PHONE)
        if (!hasPermissions(permissions)) {
            requestPermissions(permissions, 1)
        }

    }

    fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
    }


    override fun onFriendClick(position: Int) {
        val options = arrayOf("Map", "Email", "Text", "Call", "Stack")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Connect how?")
        builder.setItems(options) { _, optionId ->
            dispatchAction(optionId, friends[position])
        }
        builder.show()
    }

    fun dispatchAction(optionId: Int, friend: Friend) {
        when (optionId) {
            0-> {
                val uri = Uri.parse("geo:0,0?q=${URLEncoder.encode(friend.home, "UTF-8")}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            1 -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_EMAIL, friend.email)
                startActivity(intent)
            }
            2 -> {
                val uri = Uri.parse("smsto:${friend.phone}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                startActivity(intent)
            }
            3 -> {
                val uri = Uri.parse("tel:${friend.phone}")
                val intent = Intent(Intent.ACTION_CALL, uri)
                startActivity(intent)
            }
            4 -> {
                val uri = Uri.parse("slack://user?team=1&id=${friend.slackId}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)

            }
        }
    }

}

class Friend (val name: String,
              val slackId: String,
              val home: String,
              val email: String,
              val phone: String) {
    override fun toString() = name
}



class FriendsAdapter(private val friends: Array<Friend>,
                     private val onFriendListener: OnFriendListener)
    : RecyclerView.Adapter<FriendsAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View, val onFriendListener: OnFriendListener) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

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

    interface OnFriendListener {
        fun onFriendClick(position: Int)
    }

}



private val friends = arrayOf<Friend>(
    Friend("Ben Adams", "bta47", "Christchurch, NZ", "benjamin.adams@canterbury.ac.nz", "#######"),
    Friend("John Smith", "bta47", "Christchurch, NZ", "benjamin.adams@canterbury.ac.nz", "#######"),
    Friend("Raven Townsend", "bta47", "Christchurch, NZ", "benjamin.adams@canterbury.ac.nz", "#######"),
    Friend("Satwik Adams", "bta47", "Christchurch, NZ", "benjamin.adams@canterbury.ac.nz", "#######"),
    Friend("Jessie Adams", "bta47", "Christchurch, NZ", "benjamin.adams@canterbury.ac.nz", "#######"),
    Friend("Sam Smith", "bta47", "Christchurch, NZ", "benjamin.adams@canterbury.ac.nz", "#######"),
)