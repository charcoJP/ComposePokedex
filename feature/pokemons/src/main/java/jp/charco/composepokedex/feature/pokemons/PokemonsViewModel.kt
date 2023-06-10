package jp.charco.composepokedex.feature.pokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.charco.composepokedex.core.data.PokeRepository
import jp.charco.composepokedex.core.data.PokemonPagingSource
import jp.charco.composepokedex.core.data.network.response.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(
    pokeRepository: PokeRepository
) : ViewModel() {

    val pagingFlow: Flow<PagingData<Pokemon>> =
        Pager(PagingConfig(pageSize = 20, prefetchDistance = 5)) {
            PokemonPagingSource(pokeRepository)
        }.flow.cachedIn(viewModelScope)
}
