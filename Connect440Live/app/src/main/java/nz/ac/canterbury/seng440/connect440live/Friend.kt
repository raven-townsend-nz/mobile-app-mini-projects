package nz.ac.canterbury.seng440.connect440live

class Friend (val name: String,
              val slackId: String,
              val home: String,
              val email: String,
              val phone: String) {
    override fun toString() = name
}