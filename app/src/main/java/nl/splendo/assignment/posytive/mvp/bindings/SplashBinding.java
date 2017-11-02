package nl.splendo.assignment.posytive.mvp.bindings;

import android.content.Context;
import android.support.annotation.Nullable;

import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.mvp.PresenterMVP;
import nl.splendo.assignment.posytive.mvp.ViewMVP;

import java.util.List;

/**
 * The interface that exposes the functionality of a Splash Screen's View and Presenter
 */
public interface SplashBinding {

    interface View extends ViewMVP {

        /**
         * It shows (or triggers navigation towards) the list of cards.
         *
         * @param cards the cards, (pre)loaded in Splash screen
         */
        void showCardsLister(@Nullable List<Card> cards);
    }

    interface Model extends CardsBinding.Model {}

    interface Presenter extends PresenterMVP<View> {

        /**
         * Used to trigger the loading and caching of cards, main purpose of this Presenter
         *
         * @param context context might be needed to fetch data from some of the sources
         */
        void loadCacheCards(Context context);
    }
}