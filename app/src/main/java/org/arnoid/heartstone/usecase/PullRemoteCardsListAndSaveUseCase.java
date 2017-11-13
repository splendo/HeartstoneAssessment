package org.arnoid.heartstone.usecase;

import org.arnoid.heartstone.controller.DatabaseController;
import org.arnoid.heartstone.controller.NetworkController;
import org.arnoid.heartstone.data.CardComplete;
import org.arnoid.heartstone.data.CardSets;
import org.reactivestreams.Publisher;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

public class RemoteCardsListUseCase implements UseCase<Completable> {

    private final NetworkController networkController;
    private final DatabaseController databaseController;

    public RemoteCardsListUseCase(NetworkController networkController, DatabaseController databaseController) {
        this.networkController = networkController;
        this.databaseController = databaseController;
    }

    @Override
    public Completable execute() {
        return networkController.getCardsList()
                .flatMap(new Function<CardSets, Publisher<List<CardComplete>>>() {
                    @Override
                    public Publisher<List<CardComplete>> apply(CardSets cardSets) throws Exception {
                        return Flowable.fromIterable(cardSets.values());
                    }
                })
                .flatMap(new Function<List<CardComplete>, Publisher<CardComplete>>() {
                    @Override
                    public Publisher<CardComplete> apply(List<CardComplete> cardCompletes) throws Exception {
                        return Flowable.fromIterable(cardCompletes);
                    }
                })
                .toList()
                .toFlowable()
                .flatMapCompletable(databaseController.saveAll());
    }

}
