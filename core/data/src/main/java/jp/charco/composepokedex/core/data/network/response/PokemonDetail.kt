package jp.charco.composepokedex.core.data.network.response

import kotlinx.serialization.Serializable

@Serializable
data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<TypeResponse>,
) {

    @Serializable
    data class TypeResponse(
        val slot: Int,
        val type: Type
    )

    @Serializable
    data class Type(
        val name: String
    )

    val imageUrl: String
        get() {
            return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        }
}
