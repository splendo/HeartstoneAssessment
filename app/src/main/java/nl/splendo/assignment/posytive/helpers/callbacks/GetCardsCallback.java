package nl.splendo.assignment.posytive.helpers.callbacks;

import nl.splendo.assignment.posytive.data.DataSource;
import nl.splendo.assignment.posytive.data.models.Card;

import java.util.List;

/**
 * Simple callback for asynchronously receiving a list of cards
 */
public interface GetCardsCallback extends DataSource.GetDataCallback<Card> {

    void onSuccess(List<Card> dataItems);
}

