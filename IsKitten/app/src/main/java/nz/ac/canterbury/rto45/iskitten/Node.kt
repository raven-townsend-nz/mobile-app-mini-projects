package nz.ac.canterbury.rto45.iskitten

import android.util.JsonReader
import android.util.JsonWriter

class Node(var text: String, var left: Node? = null, var right: Node? = null) {

    fun isLeaf() = left == null && right == null

    fun question() = if(isLeaf()) "Are you thinking of ${text}" else text

    fun bifurcate(newThing: String, question: String) {
        left = Node(newThing)
        right = Node(text)
        text = question
    }

    fun write(writer: JsonWriter) {
        writer.beginObject()
        writer.name("text").value(text)
        if (!isLeaf()) {
            writer.name("left")
            left?.write(writer)
            writer.name("right")
            right?.write(writer)
        }
        writer.endObject()
    }

    companion object {
        fun read(reader: JsonReader) : Node {
            val node = Node("")

            reader.beginObject()
            while (reader.hasNext()) {
                val key = reader.nextName()
                when (key) {
                    "text" -> node.text = reader.nextString()
                    "left" -> node.left = read(reader)
                    "right" -> node.right = read(reader)
                }
            }
            reader.endObject()

            return node
        }
    }
}