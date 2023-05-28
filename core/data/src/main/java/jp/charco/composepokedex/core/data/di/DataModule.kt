package jp.charco.composepokedex.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.charco.composepokedex.core.data.PokeRepository
import jp.charco.composepokedex.core.data.PokeRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    fun providePokeRepository(pokeRepositoryImpl: PokeRepositoryImpl): PokeRepository

}