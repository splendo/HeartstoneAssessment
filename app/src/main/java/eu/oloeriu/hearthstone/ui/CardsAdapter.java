package eu.oloeriu.hearthstone.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import eu.oloeriu.hearthstone.R;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */

public class CardsAdapter extends CursorAdapter {
    public CardsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // View view = LayoutInflater.from(context).inflate(R.layout.)
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_card, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView = (TextView) view.findViewById(R.id.card_item_name);
        textView.setText("Dummy name");
    }
}
