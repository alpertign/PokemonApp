package com.alpertign.pokemon.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alpertign.pokemon.model.Pokemon
import com.alpertign.pokemon.service.PokemonAPIService
import com.alpertign.pokemon.service.PokemonDatabase
import com.alpertign.pokemon.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class ListViewModel(application: Application) : BaseViewModel(application) {

    private val pokemonAPIService = PokemonAPIService()
    private val disposable =  CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refreshTime = 10 * 60 * 1000 * 1000 * 1000L // 10 minutes

    val pokemons = MutableLiveData<List<Pokemon>>()
    val pokemonError = MutableLiveData<Boolean>()
    val pokemonLoading = MutableLiveData<Boolean>()

    fun refreshData(){
        val updateTime = customPreferences.getTime()
        if(updateTime != null && updateTime != 0L && System.nanoTime() - updateTime < refreshTime){
            getDataFromSQLite()
        }else{
            getDataFromAPI()
        }

    }

    fun refreshFromAPI(){
        getDataFromAPI()
    }

    private fun getDataFromSQLite(){
        pokemonLoading.value = true
        launch {
            val pokemons = PokemonDatabase(getApplication()).pokemonDao().getAllPokemons()
            showPokemons(pokemons)
            println("Data from SQLite")
        }
    }

    private fun getDataFromAPI(){
        pokemonLoading.value = true

        disposable.add(
            pokemonAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Pokemon>>(){
                    override fun onSuccess(t: List<Pokemon>) {
                        storeInSQLite(t)
                        println("Data from API")
                    }

                    override fun onError(e: Throwable) {
                        pokemonLoading.value = false
                        pokemonError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showPokemons(pokemonList: List<Pokemon>){
        pokemons.value = pokemonList
        pokemonError.value = false
        pokemonLoading.value = false
    }

    private fun storeInSQLite(list: List<Pokemon>){
        launch {
            val dao = PokemonDatabase(getApplication()).pokemonDao()
            dao.deleteAllPokemons()
            val listLong = dao.insertAll(*list.toTypedArray())
            var i = 0
            while (i<list.size){
                list[i].uuid = listLong[i].toInt()
                i= i+1
            }
            showPokemons(list)
        }
        customPreferences.saveTime(System.nanoTime())
    }

    override fun onCleared() {
        super.onCleared()

        disposable.clear()
    }
}