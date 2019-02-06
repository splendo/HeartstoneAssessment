package laurens.hearthstoneassessment.activities.mainpage.grid

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import laurens.hearthstoneassessment.R
import laurens.hearthstoneassessment.model.CardModel
import laurens.hearthstoneassessment.utils.centerFitInsideWithPlaceHolder
import org.jetbrains.anko.AnkoLogger

class CardViewHolder(val view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

    private val textView = view.findViewById<TextView>(R.id.card_text_view)
    private val imageView = view.findViewById<ImageView>(R.id.card_image_view)

    fun bindTo(cardModel: CardModel) {
        textView.text = cardModel.card.name
        val img = cardModel.card.img
        imageView.centerFitInsideWithPlaceHolder(img)
    }

    fun clear() {
        textView.text = ""
        imageView.centerFitInsideWithPlaceHolder(null)
    }
}