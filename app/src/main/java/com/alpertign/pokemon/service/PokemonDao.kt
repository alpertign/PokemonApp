package com.alpertign.pokemon.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.alpertign.pokemon.model.Pokemon

@Dao
interface PokemonDao {

    @Insert
    suspend fun insertAll(vararg pokemons: Pokemon): List<Long>

    @Query("SELECT * FROM pokemon")
    suspend fun getAllPokemons(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE uuid = :pokemonId")
    suspend fun getPokemon(pokemonId : Int) : Pokemon

    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemons()

}