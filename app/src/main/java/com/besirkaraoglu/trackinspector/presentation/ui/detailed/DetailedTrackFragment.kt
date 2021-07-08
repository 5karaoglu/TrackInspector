package com.besirkaraoglu.trackinspector.presentation.ui.detailed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.fragment.navArgs
import androidx.palette.graphics.Palette
import com.besirkaraoglu.trackinspector.R
import com.besirkaraoglu.trackinspector.data.entity.Track
import com.besirkaraoglu.trackinspector.databinding.FragmentDetailedTrackBinding
import com.besirkaraoglu.trackinspector.util.bitmapLoader
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detailed_track.*


@AndroidEntryPoint
class DetailedTrackFragment : Fragment() {

    private var _binding: FragmentDetailedTrackBinding? = null
    private val binding get() = _binding!!
    private val args : DetailedTrackFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailedTrackBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bind(args.track)
    }

    private fun bind(track: Track){
        with(binding){
            tvTrackName.text = track.name

            Picasso.get()
                .load(track.thumbnail)
                .fit().centerInside()
                .priority(Picasso.Priority.HIGH)
                .into(iv)

            val swatch = bitmapLoader(track.thumbnail)
            if (swatch!= null){
                tvTrackName.setTextColor(swatch.bodyTextColor)
                root.setBackgroundColor(swatch.rgb)
                /*toolbar.setTitleTextColor(swatch.titleTextColor)*/
                /*toolbar.setBackgroundColor(swatch.rgb)*/
            }
        }

    }
}