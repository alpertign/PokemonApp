package com.alpertign.pokemon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alpertign.pokemon.model.Pokemon
import com.alpertign.pokemon.service.PokemonDatabase
import kotlinx.coroutines.launch

class DetailsViewModel(application: Application) : BaseViewModel(application) {

    val pokemonLiveData = MutableLiveData<Pokemon>()

    fun getDataFromRoom(uuid : Int){
        launch {

            val dao = PokemonDatabase(getApplication()).pokemonDao()
            val pokemon = dao.getPokemon(uuid)
            pokemonLiveData.value = pokemon

        }
    }
}