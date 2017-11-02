package nl.splendo.assignment.posytive.data.models.responses;

import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

import nl.splendo.assignment.posytive.mvp.ModelMVP;

/**
 * No matter what the actual structure of the Json looks like, implementers of this interface
 * contains data that either is, or can be normalized into, a List - of generic type (i.e Card)
 */
public interface ListResponse<M extends BaseModel & ModelMVP> {

    List<M> getDataAsList();

}
