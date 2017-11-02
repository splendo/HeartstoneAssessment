package nl.splendo.assignment.posytive.helpers.mappers;

import android.support.annotation.Nullable;

import nl.splendo.assignment.posytive.data.Types;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.data.models.responses.CardsListResponse;
import nl.splendo.assignment.posytive.data.models.responses.ListResponse;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Enum defining the relationship between an Item Data Type, its Class, and the path of the embedded json file describing the data
 * Add your entry here to support loading such data from local assets, for a new model.
 * Is not mandatory to embed such data, but suggested in case of small files
 */
public enum ModelEmbeddedDataMapper {

    CARD(Types.Item.CARD, Card.class, CardsListResponse.class, "embedded_data/cards.json");


    /** The Constant representing the type of Model */
    private String mTypeTag;

    /** The class of the model for this specific entry */
    private Class<? extends ModelMVP> mTypeClass;

    /** The class representing the Json parsable content of the file embedded in the entry */
    private Class<? extends ListResponse> mJsonRepresentationClass;

    /** The shared preferences key to persist the model for this specific entry */
    private String mEmbeddedDataPath;

     <M extends BaseModel & ModelMVP>
     ModelEmbeddedDataMapper(String typeTag, Class<M> modelClass, Class jsonRepresentationClass, String embeddedDataPath) {
        mTypeTag = typeTag;
        mTypeClass = modelClass;
        mEmbeddedDataPath = embeddedDataPath;
        mJsonRepresentationClass = (Class<ListResponse<M>>) jsonRepresentationClass;
    }

    public String getTypeTag() {
        return mTypeTag;
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel & ModelMVP> Class<M> getTypeClass() {
        return (Class<M>) mTypeClass;
    }

    @SuppressWarnings("unchecked")
    public <M extends BaseModel & ModelMVP> Class<ListResponse<M>> getJsonRepresentationClass() {
        return (Class<ListResponse<M>>) mJsonRepresentationClass;
    }

    public @Nullable
    String getEmbeddedDataPath() {
        return mEmbeddedDataPath;
    }

    public static ModelEmbeddedDataMapper getByTag(String typeTag) {
        for (ModelEmbeddedDataMapper entry : values()) {
            if (entry.getTypeTag().equals(typeTag)) return entry;
        }
        return null;
    }

    public static <M extends BaseModel & ModelMVP> ModelEmbeddedDataMapper getByClass(Class<M> modelClass) {
        try {
            String providedModelType = modelClass.newInstance().getModelType();
            for (ModelEmbeddedDataMapper entry : values()) {
                if (entry.getTypeTag().equals(providedModelType)) return entry;
            }
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        return null;
    }
}
