package org.arnoid.heartstone.view.util;

import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.arnoid.heartstone.R;
import org.arnoid.heartstone.data.Card;

public class CardRarityProcessorUtil {

    private static final String TAG = CardRarityProcessorUtil.class.getSimpleName();

    public static void updateRarity(ImageView imageView, Card card) {
        Card.Rarity rarity = Card.Rarity.parse(card.getRarity());

        switch (rarity) {
            case COMMON:
                updateRarityIcon(imageView, R.drawable.ic_diamond_white, -1);
                break;
            case RARE:
                updateRarityIcon(imageView, R.drawable.ic_diamond, R.color.material_color_light_blue_accent_700);
                break;
            case EPIC:
                updateRarityIcon(imageView, R.drawable.ic_diamond, R.color.material_color_purple_900);
                break;
            case LEGENDARY:
                updateRarityIcon(imageView, R.drawable.ic_diamond, R.color.material_color_orange_900);
                break;
            case UNKNOWN:
                Log.w(TAG, "Unknown rarity type :[" + card.getRarity() + "]");
            case BASIC:
            default:
                imageView.setVisibility(View.INVISIBLE);
        }
    }

    private static void updateRarityIcon(ImageView imageView, int diamondResource, int diamondColorResource) {
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(diamondResource);
        if (diamondColorResource > 0) {
            imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(imageView.getContext(), diamondColorResource)));
        } else {
            imageView.setImageTintList(null);
        }
    }
}
