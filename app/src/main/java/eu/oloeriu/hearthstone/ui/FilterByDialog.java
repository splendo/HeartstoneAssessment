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
    private String mFilter;

    private static final String FILTER_VALUE = "param_filter_value";

    public static FilterByDialog newInstance(FILTER filter){
        FilterByDialog dialog = new FilterByDialog();
        Bundle args = new Bundle();
        args.putString(FILTER_VALUE, filter.toString());
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mSharedPreferences = super.getContext().getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        mFilter = getArguments().getString(FILTER_VALUE);
        FILTER filter = FILTER.valueOf(mFilter);
        switch (filter) {
            case TYPE:
                return buildFilterByTypeDialog();
            case MECHANICS:
                return buildFilterByMechanics();
            case CLASSES:
                return buildFilterByClasses();
            default:
                throw new IllegalStateException("Please finish this");
        }

    }

    private Dialog buildFilterByClasses() {
        Set<String> classesSet = mSharedPreferences.getStringSet(Constants.SHARED_SET_CLASSES, null);
        final String[] classesAray = classesSet.toArray(new String[classesSet.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filter by Type")
                .setItems(classesAray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String classValue = classesAray[which];
                        String selection = "instr("+ CardTable.FIELD_CLASSES + ", ? )> 0";
                        String selectionArgs[] = {classValue};
                        mListener.onUpdateFilter(selection, selectionArgs);
                    }
                });
        return builder.create();
    }

    private Dialog buildFilterByMechanics() {
        Set<String> mechanicSet = mSharedPreferences.getStringSet(Constants.SHARED_SET_MECHANICS, null);
        final String[] mechanics = mechanicSet.toArray(new String[mechanicSet.size()]);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Filter by Type")
                .setItems(mechanics, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mechanicValue = mechanics[which];
                        String selection = "instr("+ CardTable.FIELD_MECHANICS + ", ? )> 0";
                        String selectionArgs[] = {mechanicValue};
                        mListener.onUpdateFilter(selection, selectionArgs);
                    }
                });
        return builder.create();
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

    public static enum FILTER {
        TYPE, MECHANICS, CLASSES, CARD_SET
    }
}
