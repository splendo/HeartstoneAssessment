package nl.splendo.assignment.posytive.mvp;

import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.property.Property;

import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;

/**
 * Basic interface used as placeholder for a Model class, intended as POJO.
 * In the MVP pattern, the data layer will be instead accessed through DataRepository<ModelMVP>
 *
 * Also defines a basic interface to use for binding the components.
 */
@SuppressWarnings("unchecked")
public interface ModelMVP {

    /**
     * By definition, all ModelMVP implementers can be cached in DB.
     * This method is needed to make sure more complex Models (that includes relations) take care of
     * saving in the DB also related attributes.
     *
     * Models might or might not need to implement this, based on the presence of attributes
     *
     * @param dbConfig {@see DatabaseDefinition} is needed to connect to server classes
     */
    void storeAttributes(DatabaseDefinition dbConfig);

    /**
     * By definition, all ModelMVP implementers can be cached in DB.
     * This method is needed to make sure more complex Models (that includes relations) take care of
     * loading from the DB also related attributes, so the model is complete and not missing any field.
     *
     * Models might or might not need to implement this, based on the presence of attributes
     *
     * @param dbConfig {@see DatabaseDefinition} is needed to connect to server classes
     */
    void loadAttributes(DatabaseDefinition dbConfig);

    /**
     * The title of a particular instance extending ModelMVP
     *
     * @return the preferred label that a model uses to describe an item, to show as title
     */
    String getTitle();

    /**
     * Gives a String identifier for the type of data.
     * Useful for logs and for matching the data type with the type info given from API
     *
     * @return the description of type as a String, in one word.
     */
    String getModelType();

    /**
     * Needed to make sure generic classes can easily fetch items in the DB by PrimaryKey
     *
     * @return the column used as Primary key.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    Property getPrimaryColumn();

    /**
     * Needed to persist local states in DB, without worrying about overwrite from API
     */
    ModelMVP makeCopy();

    /**
     * Value related the the column returned in getPrimaryColumn().
     * Needed to make sure generic classes can easily fetch items in the DB by PrimaryKey
     *
     * @return the (String) id of the item. Must be the same contained in the Column returned in
     */
    String getId();
}
