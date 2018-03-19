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

import org.w3c.dom.Text;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardTable;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */

public class ListAdapter extends CursorAdapter {
    public ListAdapter(Context context, Cursor c, int flags) {
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

        TextView setView = (TextView)view.findViewById(R.id.item_card_set);
        int id_set = cursor.getColumnIndex(CardTable.FIELD_CARDSET);
        String setValue = cursor.getString(id_set);
        if (setValue != null){
            setView.setText("Set: "+setValue);
        }

        TextView viewMechanic = (TextView)view.findViewById(R.id.item_card_mechanics);
        int id_mec = cursor.getColumnIndex(CardTable.FIELD_MECHANICS);
        String mecValue = cursor.getString(id_mec);
        if (mecValue != null &&  !mecValue.isEmpty()){
            viewMechanic.setText("Mechanics: " + mecValue);
        }

        TextView viewClasses = (TextView)view.findViewById(R.id.item_card_classes);
        int id_class = cursor.getColumnIndex(CardTable.FIELD_CLASSES);
        String classValue = cursor.getString(id_class);
        if (classValue != null && !classValue.isEmpty()){
            viewClasses.setText("Classes: " + classValue);
        }

    }
}
