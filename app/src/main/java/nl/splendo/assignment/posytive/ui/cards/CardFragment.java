package nl.splendo.assignment.posytive.ui.cards;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.data.Types;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.mvp.BaseView;
import nl.splendo.assignment.posytive.mvp.bindings.CardsBinding;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import nl.splendo.assignment.posytive.presenters.CardDetailPresenter;

import org.apache.commons.lang3.StringUtils;
import org.parceler.Parcels;

import spork.Spork;
import spork.android.BindClick;
import spork.android.BindResource;
import spork.android.BindView;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static nl.splendo.assignment.posytive.ui.generics.ItemListFragment.KEY_ITEM;
import static nl.splendo.assignment.posytive.ui.generics.ItemListFragment.KEY_ITEM_ID;

/**
 * The {@see Fragment} that receives card data from its {@link CardsBinding.Presenter} and
 * displays a specific card, with all the details available
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CardFragment extends BaseView implements CardsBinding.View {

    /** Used for logging */
    public static final String TAG = "CardFragment";

    /** Used as currency to add to the cost info */
    public static final String CURRENCY = "$";

    @BindView(R.id.card_title) private CollapsingToolbarLayout vTitle;

    @BindView(R.id.card_artist) private TextView vCardArtist;

    @BindView(R.id.card_cover) private ImageView vCardCover;

    @BindView(R.id.card_faction) private TextView vFaction;

    @BindView(R.id.card_playerClass) private TextView vPlayerClass;

    @BindView(R.id.card_flavor) private TextView vCardFlavor;

    @BindView(R.id.card_race) private TextView vRace;

    @BindView(R.id.card_rarity) private TextView vRarity;

    @BindView(R.id.card_cost) private FloatingActionButton vCost;

    @BindView(R.id.card_fav) private FloatingActionButton vFavorite;

    @BindView(R.id.card_cardset) private TextView vCardSet;

    @BindView(R.id.card_text) private TextView vCardText;

    @BindView(R.id.card_collectable_label) private View vCardCollectable;

    @BindResource(R.string.title_back_to) private String mBackToolbarLabel;

    @Nullable
    @BindView(R.id.progress_layout) protected ViewGroup vProgressBar;

    @Nullable
    @BindView(R.id.loading_data) protected TextView vLoadingLabel;

    @BindResource(R.string.data_loading_card) protected String mCardLoadingLabel;

    /** The Presenter that handles action on this View, and communicate with the Card(s) data model */
    private CardsBinding.Presenter mCardPresenter;

    // newInstance constructor for creating fragment with arguments
    public static CardFragment newInstance(String cardId, @Nullable Card card) {
        CardFragment fragmentNew = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(KEY_ITEM, Parcels.wrap(card));
        args.putString(KEY_ITEM_ID, cardId);
        fragmentNew.setArguments(args);
        return fragmentNew;
    }

    // Store instance variables based on arguments passed

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "CardFragment created");
        Bundle args = getArguments();
        if (args == null || !(args.containsKey(KEY_ITEM_ID) || args.containsKey(KEY_ITEM))) {
            showToastMessage("Not sure which card to list here.. Bye!");
        } else {
            String cardId = args.getString(KEY_ITEM_ID);
            Card cardToDisplay = Parcels.unwrap(args.getParcelable(KEY_ITEM));
            if (cardToDisplay != null) {
                setPresenter(new CardDetailPresenter(this, cardToDisplay));
            } else if (StringUtils.isEmpty(cardId)) {
                setPresenter(new CardDetailPresenter(this, cardId));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, TAG + " is active");
        mCardPresenter.onViewActive(this);
    }

    @Override
    public void onPause() {
        Log.d(TAG, TAG + " is inactive");
        mCardPresenter.onViewInactive();
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Spork.bind(this);
        setLoadingLabel(mCardLoadingLabel);
    }

    @Override
    public void setProgressBar(boolean show) {
        if (vProgressBar != null) {
            if (show) {
                if (vCost != null) vCost.setVisibility(GONE);
                vProgressBar.setVisibility(VISIBLE);
            } else {
                if (vCost != null) vCost.setVisibility(VISIBLE);
                vProgressBar.setVisibility(GONE);
            }
        }
    }

    /**
     * Sets generic label for the loading data progress message, extend to customize
     *
     * @param label the text to show
     */
    protected void setLoadingLabel(String label) {
        if (vLoadingLabel != null) vLoadingLabel.setText(label);
    }

    @Override
    public void setTitle(String title) {
        String toolbarTitle = String.format(mBackToolbarLabel, Types.Item.CARD);
        mActivityInteractionHelper.changeToolBarTitle(toolbarTitle);
        vTitle.setTitle(title);
    }

    @Override
    public void setFavorite(boolean isFavorite) {
        vFavorite.setActivated(isFavorite);
    }

    @Override
    public void setFirstMechanic(String name) {

    }

    @Override
    public void setArtist(String artist) {
        vCardArtist.setText(artist);
    }

    @Override
    public void setFaction(String faction) {
        vFaction.setText(faction);
    }

    @Override
    public void setRace(String race) {
        vRace.setText(race);
    }

    @Override
    public void setFlavor(String flavor) {
        boolean visibleContent = StringUtils.isNotEmpty(flavor);
        if (visibleContent) vCardFlavor.setText(Html.fromHtml(flavor));
        vCardFlavor.setVisibility(visibleContent ? VISIBLE : GONE);
    }

    @Override
    public void setPlayerClass(String playerClass) {
        vPlayerClass.setText(playerClass);
    }

    @Override
    public void setCardSet(String cardSet) {
        vCardSet.setText(cardSet);
    }

    @Override
    public void setCost(int cost) {
        vCost.setImageBitmap(getCostAsDrawable((cost + CURRENCY), 32, Color.BLACK));
        vCost.setVisibility(VISIBLE);
    }

    @Override
    public void setRarity(String rarity) {
        vRarity.setText(rarity);
    }

    @Override
    public void setCollectable(boolean isCollectable) {
        vCardCollectable.setVisibility(isCollectable ? VISIBLE : GONE);
    }

    @Override
    public void setText(String text) {
        boolean visibleContent = StringUtils.isNotEmpty(text);
        if (visibleContent) vCardText.setText(Html.fromHtml(text));
        vCardText.setVisibility(visibleContent ? VISIBLE : GONE);
    }

    @Override
    public void setCardCover(String coverUrl) {
        Glide.with(getContext())
                .load(coverUrl)
                .dontTransform()
                .crossFade()
                .placeholder(R.drawable.card_back_header)
                .error(R.drawable.card_back_header)
                .into(vCardCover);
    }

    @BindClick(R.id.card_fav)
    void onFavoriteClicked() {
        if (mCardPresenter != null) mCardPresenter.onFavoriteToggled();
    }

    @Override
    public <V extends GenericMVPBinding.View> void setPresenter(GenericMVPBinding.Presenter<V> presenter) {
        mCardPresenter = (CardsBinding.Presenter) presenter;
    }

    @Override
    public void reload() {
        mCardPresenter.getCard(getContext(), mCardPresenter.getCardId());
    }

    /**
     * Used to convert the number of pages of this card, provided as String, into something that can
     * be added inside a floating action button (that accepts only drawables): a Bitmap
     *
     * @param costText the text to "render"
     * @param textSize the size to use for the text
     * @param textColor the color to use for the text
     *
     * @return the generated Bitmap, that can be used as drawable
     */
    private Bitmap getCostAsDrawable(String costText, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(costText) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(costText, 0, baseline, paint);
        return image;
    }
}
