package org.arnoid.heartstone.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.arnoid.heartstone.HeartstoneApplication;
import org.arnoid.heartstone.R;
import org.arnoid.heartstone.data.Card;
import org.arnoid.heartstone.usecase.LoadCachedCardUseCase;
import org.arnoid.heartstone.usecase.UpdateCardFavouriteStatusUseCase;
import org.arnoid.heartstone.view.util.CardImageProcessorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Displays detailed card data.
 */
public class FragmentCardDetail extends Fragment implements View.OnClickListener {

    private static final String TAG = FragmentCardDetail.class.getSimpleName();
    private static final String ARG_CARD_ID = "ARG_CARD_ID";

    private ImageView imgCardImage;
    private TextView txtCardName;
    private TextView txtCardFlavor;
    private Disposable cardDisposable;
    private ImageButton imgbtnCardBookmark;
    private Card lastKnownCard;

    public static FragmentCardDetail instance(String cardId) {
        FragmentCardDetail cardDetail = new FragmentCardDetail();

        Bundle args = new Bundle();
        args.putString(ARG_CARD_ID, cardId);

        Log.d(TAG, "Instantiating fragment for card [" + cardId + "]");

        cardDetail.setArguments(args);

        return cardDetail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_details, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgCardImage = view.findViewById(R.id.img_card_image);
        txtCardName = view.findViewById(R.id.txt_card_name);
        txtCardFlavor = view.findViewById(R.id.txt_card_flavor);
        imgbtnCardBookmark = view.findViewById(R.id.imgbtn_bookmark_card);

        imgbtnCardBookmark.setOnClickListener(this);
    }

    public void onBind(Card card) {
        this.lastKnownCard = card;

        CardImageProcessorUtil.updateCardImage(imgCardImage, card.getBaseCard(), false);

        updateWithFavourite(card);

        String name = card.getBaseCard().getName();
        if (name == null) {
            txtCardName.setText("");
        } else {
            txtCardName.setText(Html.fromHtml(name));
        }

        String flavor = card.getBaseCard().getFlavor();
        if (flavor == null) {
            txtCardFlavor.setText("");
        } else {
            txtCardFlavor.setText(Html.fromHtml(flavor));
        }
    }

    private void updateWithFavourite(Card card) {
        if (card.getBaseCard().isFavourite()) {
            imgbtnCardBookmark.setImageResource(R.drawable.ic_bookmark);
        } else {
            imgbtnCardBookmark.setImageResource(R.drawable.ic_bookmark_outline);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (cardDisposable != null) {
            cardDisposable.dispose();
        }

        Bundle arguments = getArguments();

        String cardId = arguments.getString(ARG_CARD_ID);

        cardDisposable = new LoadCachedCardUseCase(HeartstoneApplication.database())
                .setCardId(cardId)
                .execute()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Card>() {
                    @Override
                    public void accept(Card card) throws Exception {
                        onBind(card);
                    }
                });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (cardDisposable != null) {
            cardDisposable.dispose();
        }
    }

    @Override
    public void onClick(final View view) {
        if (view.getId() == R.id.imgbtn_bookmark_card) {
            view.setEnabled(false);
            lastKnownCard.getBaseCard().setFavourite(!lastKnownCard.getBaseCard().isFavourite());

            UpdateCardFavouriteStatusUseCase useCase = new UpdateCardFavouriteStatusUseCase(HeartstoneApplication.database());

            useCase.setCardToUpdate(lastKnownCard);
            useCase.execute()
                    .doOnError(new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            view.post(new Runnable() {
                                @Override
                                public void run() {
                                    view.setEnabled(true);
                                }
                            });
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Action() {
                        @Override
                        public void run() throws Exception {
                            view.setEnabled(true);
                        }
                    });
        }
    }
}
