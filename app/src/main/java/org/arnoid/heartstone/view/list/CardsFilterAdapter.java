package org.arnoid.heartstone.view.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.arnoid.heartstone.R;
import org.arnoid.heartstone.data.util.CardsFilter;

/**
 * Adapter for filter menu.
 */
public class CardsFilterAdapter extends BaseExpandableListAdapter {

    enum FilterOptions {
        Favourites(R.string.filter_option_favourites),
        Rarity(R.string.filter_option_rarity),
        Classes(R.string.filter_option_classes),
        Types(R.string.filter_option_types),
        Mechanics(R.string.filter_option_mechanics);

        private final int stringRes;

        FilterOptions(int stringRes) {
            this.stringRes = stringRes;
        }

        public int getStringRes() {
            return stringRes;
        }
    }

    private CardsFilter cardsFilter;

    public void setCardsFilter(CardsFilter cardsFilter) {
        this.cardsFilter = cardsFilter;
    }

    public CardsFilterAdapter(CardsFilter cardsFilter) {
        this.cardsFilter = cardsFilter;
    }

    @Override
    public int getGroupCount() {
        return FilterOptions.values().length;
    }

    @Override
    public int getChildrenCount(int i) {
        FilterOptions filterOption = FilterOptions.values()[i];
        switch (filterOption) {
            case Classes:
                return cardsFilter.getClasses().size();
            case Types:
                return cardsFilter.getTypes().size();
            case Favourites:
                return 1;
            case Mechanics:
                return cardsFilter.getMechanics().size();
            case Rarity:
                return cardsFilter.getRarity().size();
            default:
                return 0;
        }
    }

    @Override
    public FilterOptions getGroup(int groupPosition) {
        return FilterOptions.values()[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosiiton) {
        FilterOptions filterOption = FilterOptions.values()[groupPosition];
        switch (filterOption) {
            case Classes:
                return cardsFilter.getClasses().get(childPosiiton);
            case Types:
                return cardsFilter.getTypes().get(childPosiiton);
            case Favourites:
                return cardsFilter.isFavouriteOnly();
            case Mechanics:
                return cardsFilter.getMechanics().get(childPosiiton);
            case Rarity:
                return cardsFilter.getRarity().get(childPosiiton);
            default:
                return null;
        }
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_filter_list_group_header, viewGroup, false);
        }

        FilterOptions filterOption = getGroup(groupPosition);

        ((TextView) view).setText(filterOption.getStringRes());

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosiiton, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_filter_list_checkable, viewGroup, false);
        }

        CheckBox checkBox = (CheckBox) view;

        FilterOptions filterOption = FilterOptions.values()[groupPosition];
        Object childValue = getChild(groupPosition, childPosiiton);
        switch (filterOption) {
            case Favourites:
                handleFavouritesView(checkBox, (Boolean) childValue);
                checkBox.setText(R.string.filter_option_favourites);
                break;
            case Types:
            case Classes:
            case Mechanics:
            case Rarity:
                CardsFilter.Filterable filterable = (CardsFilter.Filterable) childValue;
                handleFilterableCheckbox(checkBox, filterable);
                checkBox.setText(filterable.getName());
                break;
            default:
                return null;
        }

        return view;
    }

    private void handleFilterableCheckbox(CheckBox checkBox, final CardsFilter.Filterable childValue) {
        checkBox.setChecked(childValue.isChecked());
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childValue.setChecked(((CheckBox) view).isChecked());
            }
        });
    }

    private void handleFavouritesView(CheckBox checkBox, Boolean childValue) {
        checkBox.setChecked(childValue);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardsFilter.setFavouriteOnly(((CheckBox) view).isChecked());
            }
        });
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
