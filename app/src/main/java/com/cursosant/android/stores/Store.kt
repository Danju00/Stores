package com.cursosant.android.stores

import android.provider.ContactsContract

data class Store(var id:Long = 0,
                 var name: String,
                 var phone: String = "",
                 var website: String = "",
                 var isFavorite: Boolean = false)
