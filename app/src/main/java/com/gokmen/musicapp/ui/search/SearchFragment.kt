package com.gokmen.musicapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gokmen.musicapp.R
import com.gokmen.musicapp.databinding.FragmentSearchBinding
import com.gokmen.musicapp.models.Artist
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val searchFragmentVM: SearchFragmentVM by viewModels()

    private var artistAdapter: ArtistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initSearchButton()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        artistAdapter = ArtistAdapter(::onArtistSelected)

        binding.rvArtistList.apply {
            addItemDecoration(getDecoration())
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = artistAdapter
        }
    }

    private fun getDecoration(): RecyclerView.ItemDecoration {
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        context?.let { context ->
            ContextCompat.getDrawable(context, R.drawable.horizontal_divider)?.let { drawable ->
                divider.setDrawable(drawable)
            }
        }
        return divider
    }

    private fun initSearchButton() {
        binding.btnSearch.setOnClickListener {
            searchFragmentVM.searchArtist(binding.etSearchText.text?.toString())
        }
    }

    private fun initObservers() {
        searchFragmentVM.showNameWarning.observe(viewLifecycleOwner) {
            if (it) {
                showNameWarning()
            }
        }

        searchFragmentVM.showListEmptyWarning.observe(viewLifecycleOwner) {
            if (it) {
                showEmptyListWarning()
            }
        }

        searchFragmentVM.artists.observe(viewLifecycleOwner) { list ->
            artistAdapter?.submitList(list)
        }
    }

    private fun showNameWarning() {
        Timber.d("Given text is too short for search")
        Snackbar.make(
            binding.etSearchText,
            R.string.short_text_error_message,
            Snackbar.LENGTH_LONG
        ).show()
        searchFragmentVM.nameWarningIsShown()
    }

    private fun showEmptyListWarning() {
        Timber.d("Could not find any artist with given text")
        Snackbar.make(
            binding.etSearchText,
            R.string.artist_not_found_error_message,
            Snackbar.LENGTH_LONG
        ).show()
        searchFragmentVM.listEmptyWarningIsShown()
    }

    private fun onArtistSelected(selectedArtist: Artist) {
        Timber.d("Selected artist is : ${selectedArtist.name}")
    }
}
