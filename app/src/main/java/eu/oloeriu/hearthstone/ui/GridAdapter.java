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
 * Created by Bogdan Oloeriu on 19/03/2018.
 */

public class GridAdapter extends CursorAdapter{
    public GridAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item_card,parent,false);
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
        int id_gold = cursor.getColumnIndex(CardTable.FIELD_IMGGOLD);
        String urlGold = cursor.getString(id_gold);

        ImageView favoriteIcon = view.findViewById(R.id.favorite_icon);
        int id_favorite = cursor.getColumnIndex(CardTable.FIELD_CARDSFAVORITE);
        int val_favorite = cursor.getInt(id_favorite);
        if (val_favorite == 0){
            favoriteIcon.setVisibility(View.INVISIBLE);
            Glide.with(context).load(url)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.small_card_v1))
                    .into(imageView);
        }else {
            favoriteIcon.setVisibility(View.VISIBLE);
            Glide.with(context).load(urlGold)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.small_card_v1))
                    .into(imageView);
        }
    }
}
