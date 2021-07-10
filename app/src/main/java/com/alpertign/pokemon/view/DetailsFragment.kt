package com.alpertign.pokemon.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.alpertign.pokemon.R
import com.alpertign.pokemon.databinding.FragmentDetailsBinding
import com.alpertign.pokemon.util.downloadFromUrl
import com.alpertign.pokemon.util.placeholderProgressBar
import com.alpertign.pokemon.viewmodel.DetailsViewModel
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    private lateinit var viewModel : DetailsViewModel
    private var pokemonUuid = 0
    private lateinit var dataBinding : FragmentDetailsBinding
    private lateinit var toolbar : Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_details,container,false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            pokemonUuid = DetailsFragmentArgs.fromBundle(it).pokemonUuid
        }

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)
        viewModel.getDataFromRoom(pokemonUuid)

        observeLiveData()

        toolbar = dataBinding.toolbar
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }




    }

    private fun observeLiveData(){
        viewModel.pokemonLiveData.observe(viewLifecycleOwner, Observer { pokemon->
            pokemon?.let {
                dataBinding.selectedPokemon = it

            }
        })
    }


}