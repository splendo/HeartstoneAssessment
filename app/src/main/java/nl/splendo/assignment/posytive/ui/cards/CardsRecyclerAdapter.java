package nl.splendo.assignment.posytive.ui.cards;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.mvp.bindings.CardsBinding;
import nl.splendo.assignment.posytive.ui.generics.ItemsRecyclerAdapter;

import java.util.List;

import spork.Spork;
import spork.android.BindClick;
import spork.android.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static nl.splendo.assignment.posytive.ui.cards.CardGridFragment.GRID_ROW_SPAN;
import static com.raizlabs.android.dbflow.config.FlowManager.getContext;

/**
 * The {@see RecyclerView.Adapter} custom implementation that renders and populates each card Item.
 *
 * It extends a generic custom adapter able to handle MP-patterns for lists in this app, but mainly
 * implements its own view/viewholder pattern.
 * Still: gets the convenience of inheriting from it, and its methods to store/get data by position.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CardsRecyclerAdapter extends ItemsRecyclerAdapter<Card, CardsRecyclerAdapter.ViewHolder> {

    /** Used for logging */
    public static final String TAG = "CardsRecyclerAdapter";

    /** The correct presenter for the list of items, acts as listener */
    private final CardsBinding.ListPresenter mCardsPresenter;

    /**
     * Constructor of the adapter
     *
     * @param presenter the presenter of cards, acting as listener for selection
     * @param items the list of items to display
     */
    public CardsRecyclerAdapter(@NonNull CardsBinding.ListPresenter presenter, List<Card> items) {
        super(presenter, items);
        mCardsPresenter = presenter;
    }

    @Override
    @LayoutRes
    protected int getItemLayoutId() {
        return R.layout.grid_item_card;
    }

    @Override
    public CardsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getItemLayoutId(), parent, false);
        resizeCellVertically(view, parent);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    private void resizeCellVertically(View view, ViewGroup parent) {
        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        int height = parent.getMeasuredHeight() / GRID_ROW_SPAN;
        params.height = height;
        view.setLayoutParams(params);
        view.setMinimumHeight(height);
    }

    @Override
    public void onBindViewHolder(CardsRecyclerAdapter.ViewHolder viewHolder, int position) {

        Card card = mItems.get(position);
        viewHolder.setTitle(card.getTitle());
        viewHolder.setFavorite(card.isFavorite());
        viewHolder.setImage(card.getImg());
    }

    /**
     * The ViewHolder class, needed to implement the ViewHolder pattern required by RecyclerView
     */
    public class ViewHolder extends ItemsRecyclerAdapter.ViewHolder {

        @BindView(R.id.cover) private ImageView vCover;

        @BindView(R.id.fav_indicator) private View vFavorite;

        @DrawableRes private final int mCardBackRes = R.drawable.card_back_default;

        @DrawableRes private final int mCardBackFavoriteRes = R.drawable.card_back_fav;

        private boolean mIsFavorite;

        public ViewHolder(View itemView) {
            super(itemView);
            Spork.bind(this);
        }

        @BindClick(R.id.list_item_layout)
        public void onItemClicked() {
            Card selectedCard = mItems.get(getAdapterPosition());
            mCardsPresenter.onCardSelected(itemView.getContext(), selectedCard);
        }

        public void setFavorite(boolean isFavorite) {
            mIsFavorite = isFavorite;
            vFavorite.setVisibility(isFavorite ? VISIBLE : GONE);
        }

        public void setImage(String coverUrl) {
            int cardBackResource = mIsFavorite ? mCardBackFavoriteRes : mCardBackRes;
            Glide.with(getContext())
                .load(coverUrl)
                .centerCrop()
                .crossFade()
                .placeholder(cardBackResource)
                .error(cardBackResource)
                .into(vCover);
        }
    }
}