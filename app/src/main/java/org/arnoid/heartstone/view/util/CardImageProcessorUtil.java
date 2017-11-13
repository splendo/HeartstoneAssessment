package org.arnoid.heartstone.view.util;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.arnoid.heartstone.R;
import org.arnoid.heartstone.data.BaseCard;

/**
 * Helper class for image loading.
 */
public class CardImageProcessorUtil {

    public static void updateCardImage(ImageView imageView, BaseCard card) {
        updateCardImage(imageView, card, true);
    }

    public static void updateCardImage(ImageView imageView, BaseCard card, boolean showPlaceHolder) {
        cancelPendingImageLoad(imageView);

        RequestCreator requestCreator = Picasso.with(imageView.getContext()).load(card.getImg());

        if (showPlaceHolder) {
            requestCreator.placeholder(R.drawable.ic_cards_stack);
        }

        requestCreator
                .error(R.drawable.ic_cards_stack)
                .into(imageView);
    }

    public static void cancelPendingImageLoad(ImageView imageView) {
        Picasso.with(imageView.getContext())
                .cancelRequest(imageView);
    }

    public static void onLoading(ImageView imageView) {

        cancelPendingImageLoad(imageView);

        Picasso.with(imageView.getContext())
                .load(R.drawable.ic_cards_stack)
                .into(imageView);
    }
}
