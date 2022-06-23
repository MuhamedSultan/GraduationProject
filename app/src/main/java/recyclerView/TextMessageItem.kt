package recyclerView

import android.content.Context
import android.text.format.DateFormat
import com.example.we_care.R
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_text_message.*
import models.TextMessage

class TextMessageItem(private val textMessage: TextMessage, val messageId: String, val context: Context) :
    Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.text_view_message.text = textMessage.text
        viewHolder.text_view_time.text = DateFormat.format("HH:mm a", textMessage.date).toString()
    }

    override fun getLayout(): Int {
        return R.layout.item_text_message
    }
}