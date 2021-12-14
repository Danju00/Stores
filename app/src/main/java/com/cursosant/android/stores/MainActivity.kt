package com.cursosant.android.stores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.cursosant.android.stores.databinding.ActivityMainBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity(), OnClickListener, MainAux {
    private lateinit var mBbinding: ActivityMainBinding

    private lateinit var mAdapter: StoreAdapter
    private lateinit var mGridLayout: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBbinding.root)

       /* mBbinding.btnSave.setOnClickListener {

            val store = StoreEntity (name = mBbinding.etName.text.toString().trim())
            Thread {

                StoreApplication.database.storeDao().addAllStore(store)
            }.start()
            mAdapter.add(store)
        }*/
        mBbinding.fab.setOnClickListener {  launchEditFragment() }

        setupRecylcerView()
    }

    private fun launchEditFragment(args: Bundle? = null) {
        val fragment= EditStoreFragment()
        if (args!= null) fragment.arguments= args

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.containerMain, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()


        hideFab()
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

    override fun onClick(storeId: Long) {
        val args = Bundle()
        args.putLong(getString(R.string.arg_id), storeId)

        launchEditFragment(args)

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

    /**
     * MainAux
     */
    override fun hideFab(isVisible: Boolean) {
        if(isVisible) mBbinding.fab.show() else mBbinding.fab.hide()
    }

    override fun addStore(storeEntity: StoreEntity) {
        mAdapter.add(storeEntity)

    }

    override fun updateStore(storeEntity: StoreEntity) {

    }
}
