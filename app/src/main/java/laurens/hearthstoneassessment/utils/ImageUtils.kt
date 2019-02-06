package laurens.hearthstoneassessment.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso
import laurens.hearthstoneassessment.R

fun ImageView.centerFitInsideWithPlaceHolder(img: String?) {
    Picasso.get()
        .load(img)
        .fit()
        .centerInside()
        .placeholder(R.drawable.placeholder)
        .into(this)
}