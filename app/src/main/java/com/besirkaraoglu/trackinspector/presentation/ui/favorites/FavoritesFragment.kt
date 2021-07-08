package com.besirkaraoglu.trackinspector.presentation.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.besirkaraoglu.trackinspector.R
import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.databinding.FragmentFavoritesBinding
import com.besirkaraoglu.trackinspector.presentation.TrackViewModel
import com.besirkaraoglu.trackinspector.presentation.ui.main.MainFragmentDirections
import com.besirkaraoglu.trackinspector.util.RecyclerItemDecoration
import com.besirkaraoglu.trackinspector.util.Resource
import com.besirkaraoglu.trackinspector.util.showIf
import com.besirkaraoglu.trackinspector.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesAdapter.OnTrackClickListener {

    private val TAG = "Favorites Fragment"
    private var _binding : FragmentFavoritesBinding? = null
    val binding get() =  _binding!!
    @InternalCoroutinesApi
    private val viewModel: TrackViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesAdapter = FavoritesAdapter(requireContext(), this)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.favorites_menu,menu)
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favoritesFragment -> {
                findNavController().navigate(R.id.favoritesFragment)
                true
            }
            R.id.deleteAllFavorites -> {
                viewModel.deleteAllFavorites()
                true
            }
            else -> {false}
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritesAdapter
            addItemDecoration(RecyclerItemDecoration(10))
        }

        viewModel.fetchFavorites.observe(viewLifecycleOwner){ result ->
            binding.pb.showIf { result is Resource.Loading }

            when(result){
                is Resource.Success -> {favoritesAdapter.setTrackList(result.data)
                favoritesAdapter.setFavList(result.data)}
                is Resource.Failure -> {
                    showToast(result.e.message.toString())
                    Log.d(TAG, "onViewCreated: ${result.e.message}")
                }
            }
        }
    }

    override fun onTrackClick(track: Track, position: Int) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailedTrackFragment(track)
        findNavController().navigate(action)
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onFavButtonClicked(track: Track, position: Int) {
        viewModel.saveOrDeleteFavorite(track)
    }
}