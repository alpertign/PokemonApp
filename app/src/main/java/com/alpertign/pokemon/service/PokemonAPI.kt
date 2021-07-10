package com.alpertign.pokemon.service

import com.alpertign.pokemon.model.Pokemon
import io.reactivex.Single
import retrofit2.http.GET

interface PokemonAPI {

    //https://gist.githubusercontent.com/DavidCorrado/8912aa29d7c4a5fbf03993b32916d601/raw/681ef0b793ab444f2d81f04f605037fb44814125/pokemon.json

    @GET("DavidCorrado/8912aa29d7c4a5fbf03993b32916d601/raw/681ef0b793ab444f2d81f04f605037fb44814125/pokemon.json")
    fun getPokemons():Single<List<Pokemon>>
}