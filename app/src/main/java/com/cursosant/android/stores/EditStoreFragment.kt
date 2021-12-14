package com.cursosant.android.stores

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.cursosant.android.stores.databinding.FragmentEditStoreBinding
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class EditStoreFragment : Fragment() {
    private lateinit var mBinding: FragmentEditStoreBinding
    private var mActivity: MainActivity? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        mBinding= FragmentEditStoreBinding.inflate(inflater,container, false)

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mActivity= activity as? MainActivity
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity?.supportActionBar?.title= getString(R.string.edit_store_title_add)

        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_save, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                mActivity?.onBackPressed()
                true

            }
            R.id.action_save -> {
                val store = StoreEntity(name = mBinding.etName.text.toString().trim(),
                    phone = mBinding.etPhone.text.toString().trim(),
                    website= mBinding.etWebsite.text.toString().trim())

                doAsync {
                    StoreApplication.database.storeDao().addAllStore(store)
                    uiThread {
                        Snackbar.make(mBinding.root, getString(R.string.edit_store_message_save_success),
                            getString(R.string.edit_store_message_save_success)
                                    Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }

                Snackbar.make(mBinding.root, getString(R.string.edit_store_message_save_success),
                    getString(R.string.edit_store_message_save_success)
                    Snackbar.LENGTH_SHORT)
                    .show()
                true
            }
            else-> return super.onOptionsItemSelected(item)
        }
       // return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        mActivity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mActivity?.supportActionBar?.title = getString(R.string.app_name)
        mActivity?.hideFab(true)

        setHasOptionsMenu(false)
        super.onDestroy()
    }
}