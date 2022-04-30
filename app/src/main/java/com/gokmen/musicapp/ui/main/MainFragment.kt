package com.gokmen.musicapp.ui.main

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
import com.gokmen.musicapp.R
import com.gokmen.musicapp.databinding.FragmentMainBinding
import com.gokmen.musicapp.models.Album
import com.gokmen.musicapp.ui.artist.AlbumAdapter
import com.gokmen.musicapp.utils.getDecoration
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val mainFragmentVM: MainFragmentVM by viewModels()

    private var albumAdapter: AlbumAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
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
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> {
                val action = MainFragmentDirections.openSearchFragment()
                findNavController().navigate(action)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
        mainFragmentVM.albums.observe(viewLifecycleOwner) { list ->
            if (list.isNullOrEmpty().not()) {
                binding.tvEmptyList.visibility = View.INVISIBLE
                binding.rvAlbumList.visibility = View.VISIBLE
                albumAdapter?.submitList(list)
            } else {
                showEmptyListWarning()
            }
        }
    }

    private fun showEmptyListWarning() {
        binding.rvAlbumList.visibility = View.INVISIBLE
        binding.tvEmptyList.visibility = View.VISIBLE
    }

    private fun onAlbumSelected(selectedAlbum: Album) {
        Timber.d("Selected album is : ${selectedAlbum.name}")
    }
}
