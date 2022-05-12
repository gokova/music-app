package com.gokmen.musicapp.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.gokmen.musicapp.R
import com.gokmen.musicapp.databinding.FragmentAlbumBinding
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.utils.getDecoration
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AlbumFragment : Fragment() {

    private var _binding: FragmentAlbumBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val albumFragmentVM: AlbumFragmentVM by viewModels()

    private var trackAdapter: TrackAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_album, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val saveAction = menu.findItem(R.id.action_save)
        val deleteAction = menu.findItem(R.id.action_delete)
        saveAction.isVisible = albumFragmentVM.album.value?.isSaved?.not() ?: true
        deleteAction.isVisible = albumFragmentVM.album.value?.isSaved ?: false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_save -> {
                albumFragmentVM.saveAlbum()
                true
            }
            R.id.action_delete -> {
                albumFragmentVM.deleteAlbum()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initRecyclerView() {
        trackAdapter = TrackAdapter()

        binding.rvTrackList.apply {
            addItemDecoration(getDecoration())
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = trackAdapter
        }
    }

    private fun initObservers() {
        albumFragmentVM.album.observe(viewLifecycleOwner) { album ->
            activity?.invalidateOptionsMenu()
            bindAlbumInfo(album)
        }

        albumFragmentVM.illegalArgument.observe(viewLifecycleOwner) { illegalArgument ->
            if (illegalArgument) {
                Timber.e("Album must be passed to fragment with key: album")
                Snackbar.make(
                    binding.rvTrackList,
                    R.string.album_not_found_error_message,
                    Snackbar.LENGTH_LONG
                ).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun bindAlbumInfo(album: Album) {
        binding.tvAlbumName.text = album.name
        binding.tvArtistName.text = album.artist
        Glide.with(binding.root)
            .load(album.coverUrl)
            .placeholder(R.drawable.progress_animation)
            .error(R.drawable.ic_album_cover_24)
            .into(binding.ivAlbumCover)

        album.tracks?.let { list ->
            if (album.tracks.isNullOrEmpty().not()) {
                binding.tvEmptyList.visibility = View.INVISIBLE
                binding.rvTrackList.visibility = View.VISIBLE
                trackAdapter?.submitList(list)
            } else {
                showEmptyListWarning()
            }
        }
    }

    private fun showEmptyListWarning() {
        Timber.d("Could not find track information")
        binding.rvTrackList.visibility = View.INVISIBLE
        binding.tvEmptyList.visibility = View.VISIBLE
    }
}
