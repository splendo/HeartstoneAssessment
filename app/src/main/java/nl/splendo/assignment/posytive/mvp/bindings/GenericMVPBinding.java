package nl.splendo.assignment.posytive.mvp.bindings;

import android.content.Context;

import nl.splendo.assignment.posytive.data.repositories.DataCollectionRepository;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import nl.splendo.assignment.posytive.mvp.PresenterMVP;
import nl.splendo.assignment.posytive.mvp.ViewMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * The interface that defines what binds the MVP pattern together in this app.
 * Is the main interface in charge of "forcing developers not to mix carrots with potatoes"
 * Extending/implementing this or the containing interface(s) is the best way to make sure that the
 * the provided Model/View/Presenter supports the same data, hence can talk to each other
 */
@SuppressWarnings("unchecked")
public interface GenericMVPBinding<MD extends GenericMVPBinding.Model,
                                   VD extends GenericMVPBinding.View,
                                   PD extends GenericMVPBinding.Presenter> {

    /**
     * Interface defining the common needs of a View aware of the type of data it supports.
     * Forces MVP classes compatibility
     */
    interface View extends ViewMVP {

        /**
         * Impose View to have a presenter. Allows external classes to set it as well
         *
         * @param presenter a compatible presenter for this type of data
         * @param <V> the View itself
         */
        <V extends View> void setPresenter(Presenter<V> presenter);

        /**
         * Tell view to reload its content, possibly because of UX/Lifecycle changes
         */
        void reload();
    }

    /**
     * Interface defining the needs of a View, specifically one that lists the supported Model
     */
    interface ListerView extends View {

        /**
         * Wrapper around View.setPresenter, that forces binding to the correct data
         *
         * @param presenter a compatible presenter for this type of data
         * @param <V> a View, able to list the Models compatible with interfaces in this class
         */
        <V extends ListerView> void setPresenter(ListPresenter<V> presenter);

        /**
         * Show compatible items on the UI
         * @param items the list of items to display
         * @param <M> the type of items to display, must be compatible with this view
         */
        <M extends BaseModel & ListableModel> void showItems(List<M> items);

        /**
         * Tells the UI to show a specific item of compatible data.
         * Implementation might differ between Tablet and phones, which might need to navigate to
         * a new activity.
         * @param which which item must be displayed on UI
         * @param <M> the type of item to display, must be compatible with this view
         */
        <M extends ListableModel> void showItemDetails(M which);
    }

    /**
     * Interface defining the needs of a View that lists items using pagination
     */
    interface PaginatedListerView extends ListerView {

        /**
         * Called when all the items in the collection have been loaded.
         * Is useless to call for more pages after this.
         */
        void onPaginatedItemsAllLoaded();

    }

    /**
     * Interface defining that this model is compatible with the View(s)/Presenter(s) in this class
     */
    interface Model extends ModelMVP {}

    /**
     * Interface defining that each of this models has a favorite "state"
     */
    interface FavoritableModel extends Model {

        Boolean isFavorite();

        void setFavorite(Boolean favorite);
    }

    /**
     * Interface defining that this model, compatible with the View(s)/Presenter(s) in this class,
     * is also able to be listed in an adapter (as its main title is known and enough for listing)
     */
    interface ListableModel extends Model {

        /**
         * Used to lists or show details for a particular item, regardless of class
         *
         * @return the preferred label that a model uses to describe an item, to show as title
         */
        String getTitle();
    }

    /**
     * Interface defining the common needs of a Presenter aware of the type of data it supports.
     * Forces MVP classes compatibility
     */
    interface Presenter<PV extends View> extends PresenterMVP<PV> {

        <M extends BaseModel & Model> void setDataRepository(Context context, Class<M> dataType);
    }

    /**
     * Interface defining the common needs of a Presenter, specifically one whose main purpose is to
     * list a collection of items of the same type.
     * Forces MVP classes compatibility.
     */
    interface ListPresenter<PV extends View> extends Presenter<PV> {

        /**
         * Used to notify presenter of the selection of one of the items, so it can act upon.
         *
         * @param context in case the presenter needs to make operations involving context at this stage
         * @param which which item was selected
         * @param <M> the type of item selected, must be compatible with this presenter
         */
        <M extends BaseModel & ListableModel> void onItemSelected(Context context, M which);
    }

    <RM extends BaseModel & Model> DataCollectionRepository<RM> getRepository(Class<MD> dataType);

    VD getView();

    PD getPresenter();
}