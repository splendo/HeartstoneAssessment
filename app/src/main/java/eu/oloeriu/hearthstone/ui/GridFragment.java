package eu.oloeriu.hearthstone.ui;


import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardTable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, UpdateFilters{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SORT_ORDER = "param_sort_order";
    private static final String ARG_SELECTION = "param_selection";
    private static final String ARG_SELECTION_ARGS = "param_selection_arguments";
    private static final int CARDS_LOADER = 1;

    // TODO: Rename and change types of parameters
    private String mSortOrder;
    private String mSelection;
    private String mSelectionArgs[];

    private InteractionListener mListener;
    private GridAdapter mGridAdapter;

    public GridFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment GridFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GridFragment newInstance(String sortOrder, String selection, String[] selectionArgs) {
        GridFragment fragment = new GridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SORT_ORDER, sortOrder);
        args.putString(ARG_SELECTION, selection);
        args.putStringArray(ARG_SELECTION_ARGS, selectionArgs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSortOrder = getArguments().getString(ARG_SORT_ORDER);
            mSelection = getArguments().getString(ARG_SELECTION);
            mSelectionArgs = getArguments().getStringArray(ARG_SELECTION_ARGS);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_grid, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.grid_view_cards);
        mGridAdapter = new GridAdapter(getActivity(), null,0);
        gridView.setAdapter(mGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                int field_id = cursor.getColumnIndex(CardTable.FIELD__ID);
                String cardId = cursor.getString(field_id);
                int cursorPosition = position;

                mListener.onShowDetails(cardId, cursorPosition);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(CARDS_LOADER,null,this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.grid_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_list_view:
                mListener.onNavigateListView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Uri uri = CardTable.CONTENT_URI;
        Loader<Cursor> cursorLoader = new CursorLoader(getContext(), uri, null, mSelection, mSelectionArgs, mSortOrder);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mGridAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mGridAdapter.swapCursor(null);
    }

    @Override
    public void changeFilters(String selection, String[] selectionArgs) {
        mSelection = selection;
        mSelectionArgs = selectionArgs;
        getLoaderManager().restartLoader(CARDS_LOADER,null,this);
    }
}
