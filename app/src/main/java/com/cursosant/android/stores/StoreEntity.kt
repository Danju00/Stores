package com.cursosant.android.stores
import androidx.room.Entity
import android.provider.ContactsContract

@Entity (tableName = "StoreEntity")
data class StoreEntity(@PrimaryKey(autoGenerate = true) var id:Long = 0,
                       var name: String,
                       var phone: String = "",
                       var website: String = "",
                       var isFavorite: Boolean = false)
