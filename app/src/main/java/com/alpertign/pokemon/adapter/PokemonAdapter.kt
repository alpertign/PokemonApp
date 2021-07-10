package com.alpertign.pokemon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.alpertign.pokemon.R
import com.alpertign.pokemon.databinding.ItemListBinding
import com.alpertign.pokemon.model.Pokemon
import com.alpertign.pokemon.util.downloadFromUrl
import com.alpertign.pokemon.util.placeholderProgressBar
import com.alpertign.pokemon.view.ListFragmentDirections
import kotlinx.android.synthetic.main.item_list.view.*

class PokemonAdapter(val pokemonList: ArrayList<Pokemon>): RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() ,PokemonClickListener{

    class PokemonViewHolder(var view : ItemListBinding): RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.item_list,parent,false)
        val view = DataBindingUtil.inflate<ItemListBinding>(inflater,R.layout.item_list,parent,false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.view.pokemon = pokemonList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

    fun updatePokemonList(newPokemonList: List<Pokemon>){
        pokemonList.clear()
        pokemonList.addAll(newPokemonList)
        notifyDataSetChanged()
    }

    override fun onPokemonClicked(v: View) {
        val uuid = v.tv_pokemon_uuid.text.toString().toInt()
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment(uuid)
        Navigation.findNavController(v).navigate(action)
    }
}