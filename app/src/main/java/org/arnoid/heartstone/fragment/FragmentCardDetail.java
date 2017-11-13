package org.arnoid.heartstone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.arnoid.heartstone.data.CardComplete;
import org.arnoid.heartstone.usecase.LoadCachedCardUseCase;
import org.arnoid.heartstone.view.util.CardImageProcessorUtil;
import org.arnoid.heartstone.view.util.CardRarityProcessorUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FragmentCardDetail extends Fragment {

    private static final String ARG_CARD_ID = "ARG_CARD_ID";

    private ImageView imgCardImage;
    private ImageView imgCardRarity;
    private TextView txtCardName;
    private TextView txtCardFlavor;
    private Disposable cardDisposable;

    public static FragmentCardDetail instance(String cardId) {
        FragmentCardDetail cardDetail = new FragmentCardDetail();

        Bundle args = new Bundle();
        args.putString(ARG_CARD_ID, cardId);

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
        imgCardRarity = view.findViewById(R.id.img_card_rarity);
        txtCardName = view.findViewById(R.id.txt_card_name);
        txtCardFlavor = view.findViewById(R.id.txt_card_flavor);
    }

    public void onBind(CardComplete cardComplete) {
        CardImageProcessorUtil.updateCardImage(imgCardImage, cardComplete);
        CardRarityProcessorUtil.updateRarity(imgCardRarity, cardComplete);

        txtCardName.setText(cardComplete.getName());
        txtCardFlavor.setText(Html.fromHtml(cardComplete.getFlavor()));
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
                .subscribe(new Consumer<CardComplete>() {
                    @Override
                    public void accept(CardComplete cardComplete) throws Exception {
                        onBind(cardComplete);
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
}
