package eu.oloeriu.hearthstone.ui;

/**
 * Created by Bogdan Oloeriu on 18/03/2018.
 */

public interface InteractionListener {
    /**
     * when this method is clalled the main activity navigates
     * to the pager activity and locates the card with the specified id
     *
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *
     * @param cardId
     */
    void onShowDetails(String cardId);
}
