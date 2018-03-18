package eu.oloeriu.hearthstone.ui;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardTable;

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
        TextView textView = (TextView) view.findViewById(R.id.item_card_name);
        int id_name = cursor.getColumnIndex(CardTable.FIELD_NAME);
        textView.setText(cursor.getString(id_name));

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        int id_url = cursor.getColumnIndex(CardTable.FIELD_IMG);
        String url = cursor.getString(id_url);
        Glide.with(context).load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.small_card_v1))
                .into(imageView);
        //into(imageView);
    }
}
