package nl.splendo.assignment.posytive.ui.generics;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nl.splendo.assignment.posytive.R;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import spork.Spork;
import spork.android.BindClick;
import spork.android.BindView;
import spork.android.ViewProvider;

/**
 * The {@link RecyclerView.Adapter} that renders and populates each Item
 *
 * Read M as the Model (i.e. Card)
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ItemsRecyclerAdapter<M extends BaseModel & GenericMVPBinding.ListableModel,
                                  VH extends ItemsRecyclerAdapter.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    /** The list of items in scope: which is dictated by the class using this */
    protected List<M> mItems;

    /** The correct presenter for the list of items, acts as listener */
    protected final GenericMVPBinding.ListPresenter mPresenter;

    /**
     * Constructor of the adapter
     *
     * @param presenter the presenter of listed items, acting as listener for selection
     * @param items the list of items to display
     */
    public ItemsRecyclerAdapter(@NonNull GenericMVPBinding.ListPresenter presenter, List<M> items) {
        mItems = items;
        mPresenter = presenter;
    }

    /**
     * Separates the layout selecting from the rest of the view/viewholder creation.
     * Allows extenders to define their own, using ViewHolder, with a different layout
     *
     * @return the id of the layout to inflate as item view.
     */
    protected @LayoutRes
    int getItemLayoutId() {
        return R.layout.list_item_squared;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(getItemLayoutId(), parent, false);

        VH viewHolder = (VH) new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {

        M item = mItems.get(position);

        viewHolder.setTitle(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void clear() {
        int size = getItemCount();
        mItems.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<M> items) {
        int prevSize = getItemCount();
        mItems.addAll(items);
        notifyItemRangeInserted(prevSize, items.size());
    }

    /**
     * The ViewHolder class, needed to implement the ViewHolder pattern required by RecyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements ViewProvider {

        @BindView(R.id.title) protected TextView vTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            Spork.bind(this);
        }

        @BindClick(R.id.list_item_layout)
        public void onItemClicked() {
            mPresenter.onItemSelected(itemView.getContext(), mItems.get(getAdapterPosition()));
        }

        @Override
        public View getView() {
            return itemView;
        }

        public void setTitle(String title) {
            vTitle.setText(title);
        }
    }
}
