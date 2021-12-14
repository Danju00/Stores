package com.cursosant.android.stores

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.cursosant.android.stores.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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
        mGridLayout= GridLayoutManager(this, resources.getInteger(R.integer.main_colums))
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
                updateStore(storeEntity)
            }
        }
    }

    override fun onDeleteStore(store Entity: StoreEntity) {
        val items = arrayOf("Eliinar", "Llamar", "Ir al web site")

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_options_title)
            .setItems(items,  { dialog, i ->
                when (i){
                    0 -> confirmDelete(storeEntity)

                    1 -> dial(storeEntity.phone)
                    2 -> goToWebsite(storeEntity.website)
                }
            })

    }
    private fun confirmDelete (storeEntity: StoreEntity){
        val items = resources.getStringArray(R.array.array_options_item)
        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_delete_title)
            .setPositiveButton(R.string.dialog_delete_confirm,  { dialogInterface, i ->
                doAsync {
                    StoreApplication.database.storeDao().updateStore(storeEntity)
                    uiThread {
                        mAdapter.delete(storeEntity)

                    }
                }
            })
            .setNegativeButton(R.string.dialog_delete_cancel , null).show()
    }
    private fun dial(phone : String){
        val callIntent = Intent().apply {
            action = Intent.ACTION_DIAL
            data = Uri.parse(uriString: "tel:$phone")
        }

       startIntent(callIntent)

    }
     private fun goToWebsite(website: ContactsContract.CommonDataKinds.Website: String){
         if (website.isEmpty()) {
             Toast.makeText(this,R.string.main_error_no_website, Toast.LENGTH_SHORT).show()

         } else {
             val websiteIntent = Intent().apply {
                 action = Intent.ACTION_VIEW
                 data = Uri.parse(website)
             }

             startIntent(websiteIntent)


         }
     }
    private fun startIntent (intent: Intent){
        if (websiteIntent.resolverActivity(packageManager) != null)
        startActivity(websiteIntent)
    else
        Toast.makeText(this, R.string.main_error_no_resolve, Toast.LENGTH_LONG).show()
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
        mAdapter.update(storeEntity)

    }
}
