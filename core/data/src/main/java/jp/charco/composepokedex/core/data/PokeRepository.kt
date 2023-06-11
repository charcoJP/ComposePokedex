package jp.charco.composepokedex.core.data

import jp.charco.composepokedex.core.common.network.ComposePokedexDispatchers
import jp.charco.composepokedex.core.common.network.Dispatcher
import jp.charco.composepokedex.core.data.network.PokeApi
import jp.charco.composepokedex.core.data.network.response.Pokemon
import jp.charco.composepokedex.core.data.network.response.PokemonDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface PokeRepository {
    fun getPokemonList(page: Int): Flow<List<Pokemon>>

    suspend fun getPokemonDetail(pokemonNumber: String): PokemonDetail
}

internal class PokeRepositoryImpl @Inject constructor(
    private val pokeApi: PokeApi,
    @Dispatcher(ComposePokedexDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) : PokeRepository {

    override fun getPokemonList(page: Int): Flow<List<Pokemon>> = flow {
        val result = pokeApi.fetchPokemonList(
            limit = PAGE_SIZE,
            offset = page * PAGE_SIZE
        )
        emit(result.results)
    }.flowOn(ioDispatcher)

    override suspend fun getPokemonDetail(pokemonNumber: String): PokemonDetail {
        return pokeApi.fetchPokemonDetail(pokemonNumber)
    }


    companion object {
        private const val PAGE_SIZE = 20
    }
}