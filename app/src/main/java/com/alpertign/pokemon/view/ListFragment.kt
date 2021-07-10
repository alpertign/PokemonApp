package com.alpertign.pokemon.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.alpertign.pokemon.R
import com.alpertign.pokemon.adapter.PokemonAdapter
import com.alpertign.pokemon.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewmodel : ListViewModel
    private val pokemonAdapter = PokemonAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewmodel.refreshData()

        rv_pokemonList.layoutManager = LinearLayoutManager(context)
        rv_pokemonList.adapter = pokemonAdapter

        swipeRefreshLayout.setOnRefreshListener {
            rv_pokemonList.visibility = View.GONE
            listError.visibility = View.GONE
            listLoading.visibility = View.VISIBLE
            viewmodel.refreshFromAPI()
            swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()


    }

    private fun observeLiveData(){
        viewmodel.pokemons.observe(viewLifecycleOwner, Observer { pokemons ->

            pokemons?.let {
                rv_pokemonList.visibility = View.VISIBLE
                pokemonAdapter.updatePokemonList(pokemons)
            }
        })

        viewmodel.pokemonError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                    listError.visibility = View.VISIBLE
                }else{
                    listError.visibility = View.GONE
                }
            }
        })

        viewmodel.pokemonLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                    listLoading.visibility = View.VISIBLE
                    rv_pokemonList.visibility = View.GONE
                    listError.visibility = View.GONE
                }else{
                    listLoading.visibility = View.GONE
                }
            }
        })
    }


}