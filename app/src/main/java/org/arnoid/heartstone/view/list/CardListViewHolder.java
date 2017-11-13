package org.arnoid.heartstone.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.arnoid.heartstone.R;
import org.arnoid.heartstone.data.CardComplete;
import org.arnoid.heartstone.view.util.CardImageProcessorUtil;
import org.arnoid.heartstone.view.util.CardRarityProcessorUtil;

public class CardListViewHolder extends RecyclerView.ViewHolder {

    private final TextView txtCardName;
    private final ImageView imgCardImage;
    private final ImageView imgCardRarity;

    public CardListViewHolder(View itemView) {
        super(itemView);
        txtCardName = itemView.findViewById(R.id.txt_card_name);
        imgCardImage = itemView.findViewById(R.id.img_card_image);
        imgCardRarity = itemView.findViewById(R.id.img_card_rarity);
    }

    public void onBind(CardComplete cardComplete) {
        txtCardName.setText(cardComplete.getName());
        txtCardName.requestLayout();

        CardRarityProcessorUtil.updateRarity(imgCardRarity, cardComplete);
        CardImageProcessorUtil.updateCardImage(imgCardImage, cardComplete);
    }


    public void onLoading() {
        txtCardName.setText(R.string.cell_card_title_loading);

        CardImageProcessorUtil.onLoading(imgCardImage);
    }


}
