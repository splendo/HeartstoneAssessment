package nl.splendo.assignment.posytive.helpers.mappers;

import nl.splendo.assignment.posytive.data.Types;
import nl.splendo.assignment.posytive.data.models.Card;
import nl.splendo.assignment.posytive.mvp.ModelMVP;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Enum defining the relationship between an Item Data Type and its Class.
 * Every model supported by the data sources and data repository must define an entry here!
 */
public enum ModelTypeMapper {

    CARD(Types.Item.CARD, Card.class);

    /** The Constant representing the type of Model */
    private String mTypeTag;

    /** The class of the model for this specific entry */
    private Class<? extends ModelMVP> mTypeClass;

     <M extends BaseModel & ModelMVP>
     ModelTypeMapper(String typeTag,
                         Class<M> modelClass) {
        mTypeTag = typeTag;
        mTypeClass = modelClass;
    }

    public String getTypeTag() {
        return mTypeTag;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <M extends BaseModel & ModelMVP> Class<M> getTypeClass() {
        return (Class<M>) mTypeClass;
    }

    public static ModelTypeMapper getByTag(String typeTag) {
        for (ModelTypeMapper entry : values()) {
            if (entry.getTypeTag().equals(typeTag)) return entry;
        }
        return null;
    }

    public static <M extends BaseModel & ModelMVP> ModelTypeMapper getByClass(Class<M> modelClass) {
        try {
            String providedModelType = modelClass.newInstance().getModelType();
            for (ModelTypeMapper entry : values()) {
                if (entry.getTypeTag().equals(providedModelType)) return entry;
            }
        } catch (InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        return null;
    }
}
