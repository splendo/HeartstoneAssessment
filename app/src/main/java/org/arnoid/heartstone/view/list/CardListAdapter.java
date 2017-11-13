package org.arnoid.heartstone.view.list;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.arnoid.heartstone.R;
import org.arnoid.heartstone.data.Card;

/**
 * Shows list of cards.
 */
public class CardListAdapter extends PagedListAdapter<Card, CardListViewHolder> {

    static final DiffCallback<Card> DIFF_CALLBACK = new DiffCallback<Card>() {
        @Override
        public boolean areItemsTheSame(@NonNull Card oldCard, @NonNull Card newCard) {
            return oldCard.getBaseCard().getCardId().equals(newCard.getBaseCard().getCardId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Card oldCard, @NonNull Card newCard) {
            return oldCard.equals(newCard);
        }
    };

    private final View.OnClickListener onClickListener;

    public CardListAdapter(View.OnClickListener onClickListener) {
        super(DIFF_CALLBACK);
        this.onClickListener = onClickListener;
    }

    @Override
    public CardListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_card_list, parent, false);

        itemView.setOnClickListener(onClickListener);

        return new CardListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardListViewHolder holder, int position) {
        Card card = getItem(position);
        if (card != null) {
            holder.onBind(card);
        } else {
            // Null defines a placeholder item - PagedListAdapter will automatically invalidate
            // this row when the actual object is loaded from the database
            holder.onLoading();
        }
    }

}
