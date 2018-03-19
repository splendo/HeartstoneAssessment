package eu.oloeriu.hearthstone.ui;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.ListView;

import eu.oloeriu.hearthstone.R;
import eu.oloeriu.hearthstone.data.CardTable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link InteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
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

    private ListAdapter mListAdapter;

    public ListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sortOrder sql part of the sorting order.
     * @param selection CursorLoader part for selection
     * @param selectionArgs CursorLoader part for selectionArgs;
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String sortOrder, String selection, String[] selectionArgs) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SORT_ORDER, sortOrder);
        args.putString(ARG_SELECTION, selection);
        args.putStringArray(ARG_SELECTION_ARGS, selectionArgs);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSelection = getArguments().getString(ARG_SELECTION);
            mSortOrder = getArguments().getString(ARG_SORT_ORDER);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = (ListView) view.findViewById(R.id.listView_cards);
        mListAdapter = new ListAdapter(getActivity(), null, 0);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        getLoaderManager().initLoader(CARDS_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = CardTable.CONTENT_URI;
        Loader<Cursor> cursorLoader = new CursorLoader(getContext(), uri, null, mSelection, mSelectionArgs, mSortOrder);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mListAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mListAdapter.swapCursor(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_grid_view:
                mListener.onNavigateGridView();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
