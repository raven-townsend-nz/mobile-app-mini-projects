package nz.ac.canterbury.seng440.connect440live

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation

class EntryFragment : Fragment() {

    // STEP 5
    private val viewModel: FriendsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_entry, container, false)

        // STEP 6
        viewModel.numFriends.observe(viewLifecycleOwner) { num ->
            val welcomeTextView: TextView = view.findViewById(R.id.welcomeTextView)
            welcomeTextView.text = "You have connected with ${num} friends"
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.buttonViewFriends)?.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_entryFragment_to_viewFriendsFragment, null)
        )

        // STEP 7
        view.findViewById<Button>(R.id.buttonAddFriend)?.setOnClickListener {
            addNewFriend()
        }
    }

    // STEP 8
    private fun addNewFriend() {
        val builder = AlertDialog.Builder(context)

        val form = layoutInflater.inflate(R.layout.dialog_add_friend, null, false)
        builder.setView(form)

        val nameBox: EditText = form.findViewById(R.id.nameBox)
        val slackBox: EditText = form.findViewById(R.id.slackBox)
        val homeBox: EditText = form.findViewById(R.id.homeBox)
        val phoneBox: EditText = form.findViewById(R.id.phoneBox)
        val emailBox: EditText = form.findViewById(R.id.emailBox)

        builder.setPositiveButton("Add") { _, _ ->
            val newFriend = Friend(
                nameBox.text.toString(),
                slackBox.text.toString(),
                homeBox.text.toString(),
                phoneBox.text.toString(),
                emailBox.text.toString()
            )
            viewModel.addFriend(newFriend)
        }

        builder.show()
    }

}