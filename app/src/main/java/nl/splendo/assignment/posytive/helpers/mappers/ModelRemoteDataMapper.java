package nl.splendo.assignment.posytive.helpers.mappers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import nl.splendo.assignment.posytive.data.Types;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.data.remote.CardsRemoteDataSource;
import nl.splendo.assignment.posytive.data.remote.RemoteDataSource;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Enum defining the relationship between an Item Data Type, its Class, and its RemoteDataSource
 * Acts as a RemoteDataSourceFactory: data sources of type remote needs stronger typing because of Retrofit
 * Add your entry here to support more models fetch-able from API.
 */
@SuppressWarnings("unchecked")
public enum ModelRemoteDataMapper {

    // This only acts as a summary, make sure to add the entry in method getDataSource()
    CARD(Types.Item.CARD, Card.class, CardsRemoteDataSource.class);

    /** The Constant representing the type of Model */
    private String mTypeTag;

    /** The class of the model for this specific entry */
    private Class<? extends GenericMVPBinding.Model> mTypeClass;

    /** The class of the View (implemented by a Fragment) for this specific type tag and Model */
    private Class<? extends RemoteDataSource> mDataSourceClass;

     <M extends BaseModel & GenericMVPBinding.Model, RDS extends RemoteDataSource<M>>
     ModelRemoteDataMapper(String typeTag,
                           Class<M> modelClass,
                           @NonNull Class<RDS> remoteDataSourceClass) {
        mTypeTag = typeTag;
        mTypeClass = modelClass;
        mDataSourceClass = remoteDataSourceClass;
    }

    public @Nullable
    RemoteDataSource getDataSource(Context context) {
        switch (this) {
            case CARD: return new CardsRemoteDataSource(context);
        }
        return null;
    }

    public String getTypeTag() {
        return mTypeTag;
    }

    public <M extends BaseModel & GenericMVPBinding.Model> Class<M> getTypeClass() {
        return (Class<M>) mTypeClass;
    }

    public @NonNull
    <RDS extends RemoteDataSource> Class<RDS> getDataSourceClass() {
        return (Class<RDS>) mDataSourceClass;
    }

    public static @Nullable
    ModelRemoteDataMapper getByTag(String typeTag) {
        for (ModelRemoteDataMapper entry : values()) {
            if (entry.getTypeTag().equals(typeTag)) return entry;
        }
        return null;
    }

    public static @Nullable
    <M extends BaseModel & GenericMVPBinding.Model> ModelRemoteDataMapper getByClass(Class<M> modelClass) {
        try {
            String providedModelType = modelClass.newInstance().getModelType();
            for (ModelRemoteDataMapper entry : values()) {
                if (entry.getTypeTag().equals(providedModelType)) return entry;
            }
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        return null;
    }
}
