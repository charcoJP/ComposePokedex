package jp.charco.composepokedex.core.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import jp.charco.composepokedex.core.data.network.response.Pokemon
import kotlinx.coroutines.flow.singleOrNull

class PokemonPagingSource(
    private val pokeRepository: PokeRepository
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        val position = state.anchorPosition ?: return null
        val prevKey = state.closestPageToPosition(position)?.prevKey
        val nextKey = state.closestPageToPosition(position)?.nextKey

        return prevKey?.plus(1) ?: nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val currentKey = params.key ?: 0

        return try {
            val result = pokeRepository.getPokemonList(currentKey)
            val list = result.singleOrNull()
            // TODO: 一旦、結果が空であれば最終ページとする
            val nextKey = if (list.isNullOrEmpty()) null else currentKey + 1

            LoadResult.Page(
                data = list ?: listOf(),
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}