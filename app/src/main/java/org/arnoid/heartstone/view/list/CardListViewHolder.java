package org.arnoid.heartstone.view.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.arnoid.heartstone.R;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.view.util.CardImageProcessorUtil;

public class CardListViewHolder extends RecyclerView.ViewHolder {

    private final TextView txtCardName;
    private final ImageView imgCardImage;
    private final ImageView imgCardFavourite;

    public CardListViewHolder(View itemView) {
        super(itemView);
        txtCardName = itemView.findViewById(R.id.txt_card_name);
        imgCardImage = itemView.findViewById(R.id.img_card_image);
        imgCardFavourite = itemView.findViewById(R.id.img_card_favourite);
    }

    public void onBind(Card card) {
        txtCardName.setText(card.getBaseCard().getName());
        txtCardName.requestLayout();

        if (card.getBaseCard().isFavourite()) {
            imgCardFavourite.setVisibility(View.VISIBLE);
        } else {
            imgCardFavourite.setVisibility(View.GONE);
        }

        CardImageProcessorUtil.updateCardImage(imgCardImage, card.getBaseCard());
    }


    public void onLoading() {
        txtCardName.setText(R.string.cell_card_title_loading);

        CardImageProcessorUtil.onLoading(imgCardImage);
    }


}
