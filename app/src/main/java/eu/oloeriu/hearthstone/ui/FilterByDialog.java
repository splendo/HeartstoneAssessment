package eu.oloeriu.hearthstone.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import java.util.Set;

import eu.oloeriu.hearthstone.data.CardTable;
import eu.oloeriu.hearthstone.tools.Constants;

/**
 * Created by Bogdan Oloeriu on 20/03/2018.
 */

public class FilterByDialog extends DialogFragment {
    private SharedPreferences mSharedPreferences;
    private InteractionListener mListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSharedPreferences = super.getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        return buildFilterByTypeDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);
        if (context instanceof InteractionListener) {
            mListener = (InteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Dialog buildFilterByTypeDialog() {

        Set<String> typesSet = mSharedPreferences.getStringSet(Constants.SHARED_SET_TYPE, null);
        final String[] types = typesSet.toArray(new String[typesSet.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filter by Type")
                .setItems(types, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String type = types[which];
                        String selection = CardTable.FIELD_TYPE + " =? ";
                        String selectionArgs[] = {type};
                        mListener.onUpdateFilter(selection, selectionArgs);
                    }
                });
        return builder.create();

    }
}
