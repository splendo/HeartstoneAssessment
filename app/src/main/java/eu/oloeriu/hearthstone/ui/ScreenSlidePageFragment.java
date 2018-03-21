package eu.oloeriu.hearthstone.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import eu.oloeriu.hearthstone.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScreenSlidePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScreenSlidePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScreenSlidePageFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CARD_ID = "card_id";
    private static final String CARD_NAME = "card_name";
    private static final String CARD_SET = "card_set";
    private static final String CARD_MECHANICS = "card_mechanics";
    private static final String CARD_CLASSES = "card_classes";
    private static final String CARD_IMAGE_URL = "card_image_url";

    // TODO: Rename and change types of parameters
    private String mCardId;
    private String mCardName;

    private String mCardSet;
    private String mCardMechanics;
    private String mCardClasses;
    private String mCardImageUrl;

    private OnFragmentInteractionListener mListener;


    public ScreenSlidePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param cardId   Parameter 1.
     * @param cardName Parameter 2.
     * @return A new instance of fragment ScreenSlidePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ScreenSlidePageFragment newInstance(String cardId,
                                                      String cardName,
                                                      String cardSet,
                                                      String cardMechanics,
                                                      String cardClasses,
                                                      String cardImageUrl) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putString(CARD_ID, cardId);
        args.putString(CARD_NAME, cardName);
        args.putString(CARD_SET, cardSet);
        args.putString(CARD_MECHANICS, cardMechanics);
        args.putString(CARD_CLASSES, cardClasses);
        args.putString(CARD_IMAGE_URL, cardImageUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCardId = getArguments().getString(CARD_ID);
            mCardName = getArguments().getString(CARD_NAME);
            mCardSet = getArguments().getString(CARD_SET);
            mCardMechanics = getArguments().getString(CARD_MECHANICS);
            mCardClasses = getArguments().getString(CARD_CLASSES);
            mCardImageUrl = getArguments().getString(CARD_IMAGE_URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page, container, false);
        ((TextView) rootView.findViewById(R.id.detail_card_name)).setText("Name: " + mCardName);
        if (mCardSet != null) {
            ((TextView) rootView.findViewById(R.id.detail_card_set)).setText("Set: " + mCardSet);
        }
        if (mCardClasses != null) {
            ((TextView) rootView.findViewById(R.id.detail_card_classes)).setText("Classes: " + mCardSet);
        }
        if (mCardMechanics != null) {
            ((TextView) rootView.findViewById(R.id.detail_card_mechanics)).setText("Mechanics: " + mCardMechanics);
        }
        if (mCardImageUrl != null) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
            Glide.with(getActivity()).load(mCardImageUrl)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.small_card_v1))
                    .into(imageView);
        }

        return rootView;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
