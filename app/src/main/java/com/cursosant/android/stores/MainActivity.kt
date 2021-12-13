package com.cursosant.android.stores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.cursosant.android.stores.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var mBbinding: ActivityMainBinding

    private lateinit var mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBbinding.root)

        mBbinding.btnSave.setOnClickListener {

            val store = StoreEntity (name = mBbinding.etName.text.toString().trim())
            Thread {

                StoreApplication.database.storeDao().addAllStore(store)
            }.start()
            mAdapter.add(store)
        }

        setupRecylcerView()
    }


    private fun setupRecylcerView() {
        mAdapter = StoreAdapter(mutableListOf(), listener = this)
        mGridLayout= GridLayoutManager(this, 2)
        getStore()

        mBbinding.recyclerView.apply {  this: RecyclerView
            setHasFixedSize(true)
            layoutManeger = mGridLayout
            adapter = mAdapter

        }
    }
    private fun getStore(){
        doAsync {
            val store = StoreApplication.database.storeDao().getAllStore()
            uiThread {
                mAdapter.setStore(stores)
            }
        }

    }

    /*
    *
    * Onclicklistener
     */

    override fun onClick(storeEntity: StoreEntity) {

    }

    override fun onFavoriteStore(storeEntity: StoreEntity) {
        storeEntity.isFavorite = !storeEntity.isFavorite
        doAsync {
            StoreApplication.database.storeDao().updateStore(storeEntity)
            uiThread {
                mAdapter.update(storeEntity)
            }
        }
    }

    override fun onDeleteStore(storeEntity: StoreEntity) {
        doAsync {
            StoreApplication.database.storeDao().updateStore(storeEntity)
            uiThread {
                mAdapter.delete(storeEntity)
            }
        }
    }
}
