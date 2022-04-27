package com.gokmen.musicapp.utils

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.gokmen.musicapp.R

internal fun RecyclerView.getDecoration(): RecyclerView.ItemDecoration {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    context?.let { context ->
        ContextCompat.getDrawable(context, R.drawable.horizontal_divider)?.let { drawable ->
            divider.setDrawable(drawable)
        }
    }
    return divider
}
