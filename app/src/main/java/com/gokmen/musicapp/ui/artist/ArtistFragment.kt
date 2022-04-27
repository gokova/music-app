package com.gokmen.musicapp.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.gokmen.musicapp.databinding.FragmentArtistBinding
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.utils.getDecoration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ArtistFragment : Fragment() {

    private var _binding: FragmentArtistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: ArtistFragmentArgs by navArgs()

    private val artistFragmentVM: ArtistFragmentVM by viewModels()

    private var albumAdapter: AlbumAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setArtist()
        artistFragmentVM.findTopAlbums(args.artist)
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setArtist() {
        artistFragmentVM.artist = args.artist
        binding.tvArtistName.text = artistFragmentVM.artist?.name ?: ""
    }

    private fun initRecyclerView() {
        albumAdapter = AlbumAdapter(::onAlbumSelected)

        binding.rvAlbumList.apply {
            addItemDecoration(getDecoration())
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = albumAdapter
        }
    }

    private fun initObservers() {
        artistFragmentVM.showListEmptyWarning.observe(viewLifecycleOwner) {
            if (it) {
                showEmptyListWarning()
            }
        }

        artistFragmentVM.albums.observe(viewLifecycleOwner) { list ->
            binding.tvEmptyList.visibility = View.INVISIBLE
            binding.rvAlbumList.visibility = View.VISIBLE
            albumAdapter?.submitList(list)
        }
    }

    private fun showEmptyListWarning() {
        Timber.d("Could not find any album with given artist")
        binding.rvAlbumList.visibility = View.INVISIBLE
        binding.tvEmptyList.visibility = View.VISIBLE
        artistFragmentVM.listEmptyWarningIsShown()
    }

    private fun onAlbumSelected(selectedAlbum: Album) {
        Timber.d("Selected album is : ${selectedAlbum.name}")
    }
}
