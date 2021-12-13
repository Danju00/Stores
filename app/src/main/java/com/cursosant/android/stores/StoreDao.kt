package com.cursosant.android.stores
import androidx.room.Dao

@Dao
interface StoreDao {
    @Query (value: "SELECT * FROM StoreEntity")
    fun getAllStore(): MutableList<StoreEntity>

    @Insert
    fun addAllStore(storeEntity: StoreEntity)

    @Update
    fun updateStore(storeEntity: StoreEntity)

    @Delete
    fun deleteStore(storeEntity: StoreEntity)

}