package nl.splendo.assignment.posytive.mvp.bindings;

import android.content.Context;

import nl.splendo.assignment.posytive.data.models.Card;

import java.util.List;

/**
 * The interface that exposes the functionality of a Cards's Listing View and Presenter.
 * It just gives a better and more readable name to the more generic interfaces for lists and
 * details by item defined in {@link GenericMVPBinding}
 */
@SuppressWarnings("unchecked")
public interface CardsBinding extends GenericMVPBinding<Card,
                                                        CardsBinding.View,
                                                        CardsBinding.Presenter>{
    /**
     * A MVP view supporting the listing of cards
     */
    interface View extends GenericMVPBinding.View {

        void setTitle(String cardTitle);

        void setFavorite(boolean isFavorite);

        void setFirstMechanic(String name);

        void setFaction(String faction);

        void setRace(String race);

        void setRarity(String rarity);

        void setCollectable(boolean isCollectable);

        void setText(String text);

        void setFlavor(String flavor);
        
        void setPlayerClass(String playerClass);

        void setArtist(String artist);

        void setCardSet(String cardSet);

        void setCost(int cost);

        /**
         * Loads the image representing the card cover, and displays it in an appropriate View
         *
         * It fails gracefully, so is not a problem if is a 404, but the url provided should be a valid one
         *
         * @param coverUrl the full url, including http, where to find the cover image.
         */
        void setCardCover(String coverUrl);
    }

    /**
     * A MVP view supporting the listing of cards
     */
    interface ListerView extends GenericMVPBinding.ListerView {

        void showCardDetails(Card which);
    }

    /**
     * Used by Card model to point out that is listable by the implementers of these inferfaces
     */
    interface ListableModel extends GenericMVPBinding.ListableModel {}

    /**
     * A presenter of cards that supports listing. Might or might not apply custom logic
     * @param <V> a MVP view compatible with this presenter: a card lister
     */
    interface ListPresenter<V extends ListerView> extends GenericMVPBinding.ListPresenter<V> {

        void getCards(Context context);

        void onCardSelected(Context context, Card card);
    }

    /**
     * A presenter of cards that supports listing. Might or might not apply custom logic
     * @param <V> a MVP view compatible with this presenter: a card lister
     */
    interface Presenter<V extends View> extends GenericMVPBinding.Presenter<V> {

        /**
         * Used to ask the presenter to retrieve data and show on UI, if active
         *
         * @param cardId the id of the Card to fetch
         */
        void getCard(Context context, String cardId);


        /**
         * Returns the id of the card known by this presenter
         *
         * @return the id of the configured card
         */
        String getCardId();


        void onFavoriteToggled();
    }
}
