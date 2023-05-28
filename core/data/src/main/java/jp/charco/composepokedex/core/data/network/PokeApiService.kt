package jp.charco.composepokedex.core.data.network

import jp.charco.composepokedex.core.data.network.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApi {
    @GET("pokemon")
    suspend fun fetchPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): PokemonResponse
}