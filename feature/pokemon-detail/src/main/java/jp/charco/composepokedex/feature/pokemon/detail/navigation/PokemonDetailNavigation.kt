package jp.charco.composepokedex.feature.pokemon.detail.navigation

import androidx.annotation.VisibleForTesting
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import jp.charco.composepokedex.feature.pokemon.detail.PokemonDetailScreen

internal const val pokemonNumberArg = "pokemonNumber"

fun NavController.navigateToPokemonDetail(number: String) {
    this.navigate("pokemon/$number")
}

fun NavGraphBuilder.pokemonDetailNavGraph(
) {
    composable(
        route = "pokemon/{$pokemonNumberArg}",
        arguments = listOf(
            navArgument(pokemonNumberArg) { type = NavType.StringType },
        ),
    ) {
        PokemonDetailScreen()
    }
}
