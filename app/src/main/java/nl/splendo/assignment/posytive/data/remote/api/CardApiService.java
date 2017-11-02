package nl.splendo.assignment.posytive.data.remote.api;

import nl.splendo.assignment.posytive.data.models.Card;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * The interface defining methods to fetch card data from the API endpoint using Retrofit lib
 */
public interface CardApiService {

    @GET Call<Map<String, List<Card>>> getCards(@Url String url);

}
