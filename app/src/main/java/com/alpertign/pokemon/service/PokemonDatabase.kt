package com.alpertign.pokemon.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alpertign.pokemon.model.Pokemon

@Database(entities = arrayOf(Pokemon::class),version = 1)
abstract class PokemonDatabase : RoomDatabase(){

    abstract fun pokemonDao() :PokemonDao

    companion object{
        @Volatile private var instance : PokemonDatabase? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also{
                instance = it
            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext, PokemonDatabase::class.java, "pokemondatabase"
        ).build()
    }
}