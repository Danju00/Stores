package com.cursosant.android.stores

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cursosant.android.stores.databinding.ItemStoreBinding

class StoreAdapter {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemStoreBinding.bind(view)
    }
}