package nz.ac.canterbury.rto45.connect440compose

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nz.ac.canterbury.rto45.connect440compose.ui.theme.Connect440ComposeTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AlertDialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontWeight
import java.net.URLEncoder

class MainActivity : ComponentActivity() {

    private val friends = listOf<Friend>(
        Friend(
            "Ben Adams",
            "U8U061XEY",
            "Christchurch, New Zealand",
            "benjamin.adams@canterbury.ac.nz",
            "123456789"
        ),
        Friend(
            "Homer Simpson",
            "U80000000",
            "Springfield, USA",
            "homer.simpson@example.org",
            "987654321"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val permissions = arrayOf(Manifest.permission.CALL_PHONE)
        if (!hasPermissions(permissions)) {
            requestPermissions(permissions, 1)
        }

        setContent {
            Connect440ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    var openDialog by remember { mutableStateOf(false) }
                    var selectedFriend by remember { mutableStateOf(friends[0]) }

                    FriendsList(friends, onFriendClick = { friend ->
                        selectedFriend = friend
                        openDialog = true
                    })

                    if (openDialog) {
                        AlertDialog(
                            onDismissRequest = { openDialog = false },
                            title = {
                                Text(
                                    style = MaterialTheme.typography.body1,
                                    fontWeight = FontWeight.Bold,
                                    text = "Connect how?"
                                )
                            },
                            text = {
                                   val options = listOf("Map", "Email", "Text", "Call", "Slack")
                                LazyColumn {
                                    items(options) { option ->
                                        Text(
                                            modifier = Modifier.clickable {
                                                openDialog = false
                                                dispatchAction(option, selectedFriend)
                                            },
                                            style = MaterialTheme.typography.body1,
                                            text = option
                                        )
                                    }
                                }
                            },
                            buttons = {}
                        )
                    }
                }

            }
        }


    }

    private fun dispatchAction(option: String, friend: Friend) {
        when (option) {
            "Map" -> {
                val uri = Uri.parse("geo:0,0?q=${URLEncoder.encode(friend.home, "UTF-8")}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            "Email" -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_EMAIL, friend.email)
                startActivity(intent)
            }
            "Text" -> {
                val uri = Uri.parse("smsto:${friend.phone}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                startActivity(intent)
            }
            "Call" -> {
                val uri = Uri.parse("tel:${friend.phone}")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                startActivity(intent)
            }
            "Slack" -> {
                val uri = Uri.parse("slack://user?team=TR8N4694&id=${friend.slackId}")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
        }
    }


    private fun hasPermissions(permissions: Array<String>): Boolean {
        return permissions.all { checkSelfPermission(it) != PackageManager.PERMISSION_GRANTED }
    }
}

@Composable
fun FriendsList(friends: List<Friend>, onFriendClick: (Friend) -> Unit) {
    LazyColumn {
        items(friends) { friend ->
            Text(
                modifier = Modifier
                    .padding(all = 2.dp)
                    .clickable { onFriendClick(friend) },
                style = MaterialTheme.typography.body1,
                text = friend.name
            )
        }
    }
}


class Friend(
    val name: String,
    val slackId: String,
    val home: String,
    val email: String,
    val phone: String
) {
    override fun toString() = name
}

