package com.besirkaraoglu.trackinspector.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.besirkaraoglu.trackinspector.R
import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.databinding.FragmentMainBinding
import com.besirkaraoglu.trackinspector.presentation.TrackViewModel
import com.besirkaraoglu.trackinspector.util.RecyclerItemDecoration
import com.besirkaraoglu.trackinspector.util.Resource
import com.besirkaraoglu.trackinspector.util.showIf
import com.besirkaraoglu.trackinspector.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class MainFragment : Fragment(),
MainAdapter.OnTrackClickListener{

    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!
    private val TAG = "Main Fragment"

    @InternalCoroutinesApi
    private val viewModel by activityViewModels<TrackViewModel>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainAdapter = MainAdapter(requireContext(),this)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.toolbar_menu,menu)
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.favoritesFragment -> {
                findNavController().navigate(R.id.favoritesFragment)
                true
            }
            R.id.deleteAll -> {
                viewModel.deleteAllTracks()
                true
            }
            else -> {false}
        }

    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding.recycler){
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mainAdapter
            addItemDecoration(RecyclerItemDecoration(10))
        }

        viewModel.fetchTrackList.observe(viewLifecycleOwner){ result ->
            binding.pb.showIf{ result is Resource.Loading}

            when(result){
                is Resource.Loading -> {
                    Log.d(TAG, "onViewCreated: Loading")
                }
                is Resource.Success -> {mainAdapter.setTrackList(result.data)}
                is Resource.Failure -> {
                    showToast("Tracks didn't loaded. ${result.e}")
                    Log.d(TAG, "onViewCreated: ${result.e}")
                }
            }

        }
        viewModel.fetchFavorites.observe(viewLifecycleOwner){result ->
            binding.pb.showIf { result is Resource.Loading }

            when(result){
                is Resource.Success -> {mainAdapter.setFavList(result.data)}
                is Resource.Failure -> {
                    showToast("Favorite Tracks didn't loaded ${result.e.message}")
                    Log.d(TAG, "onViewCreated: ${result.e}")
                }
            }
        }
    }

    override fun onTrackClick(track: Track, position: Int) {
        val action = MainFragmentDirections.actionMainFragmentToDetailedTrackFragment(track)
        findNavController().navigate(action)
    }

    @ExperimentalCoroutinesApi
    @InternalCoroutinesApi
    override fun onFavButtonClicked(track: Track, position: Int) {
        viewModel.saveOrDeleteFavorite(track)
        mainAdapter.notifyDataSetChanged()
    }
}