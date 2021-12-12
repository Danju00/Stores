package com.cursosant.android.stores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.cursosant.android.stores.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var mBbinding: ActivityMainBinding

    private lateinit var mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBbinding.root)

        setupRecylcerView()
    }

    private fun setupRecylcerView() {
        mAdapter = StoreAdapter(mutableListOf(), listener = this)
        mGridLayout= GridLayoutManager(this, 2)

        mBbinding.recyclerView.apply {  this: RecyclerView
            setHasFixedSize(true)
            layoutManeger = mGridLayout
            adapter = mAdapter

        }
    }

    /*
    *
    * Onclicklistener
     */

    override fun onClick(store: Store) {

    }
}