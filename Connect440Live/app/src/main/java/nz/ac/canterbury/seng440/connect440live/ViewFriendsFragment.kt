package nz.ac.canterbury.seng440.connect440live

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import java.net.URLEncoder

class ViewFriendsFragment : Fragment(), FriendsAdapter.OnFriendListener {

    // STEP 5
    private val viewModel: FriendsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_view_friends, container, false)

        // STEP 10
        val friendAdapter = FriendsAdapter(viewModel.friends.value!!,this)
        viewModel.friends.observe(viewLifecycleOwner) { newFriends ->
            friendAdapter.setData(newFriends)
        }

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = friendAdapter
        return view
    }

    override fun onFriendClick(position: Int) {
        val options = arrayOf("Map", "Email", "Text", "Call", "Slack")
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Connect how?")
        builder.setItems(options) { _, optionId ->
            //STEP 11
            dispatchAction(optionId, viewModel.friends.value!![position])
        }
        builder.show()
    }

    fun dispatchAction(optionId: Int, friend: Friend) {
        when (optionId) {
            0 -> {
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
                val intent = Intent(Intent.ACTION_DIAL, uri)
                //val intent = Intent(Intent.ACTION_CALL, uri)
                startActivity(intent)
            }
            4 -> {
                val uri = Uri.parse("slack://user?team=TR8N4694&id=${friend.slackId}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }
}