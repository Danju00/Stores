package com.cursosant.android.stores
import androidx.room.*

@Dao
interface StoreDao {
    @Query (value = "SELECT * FROM StoreEntity")
    fun getAllStore(): MutableList<StoreEntity>

    @Query(value = " SELECT * FROM StoreEntity where id = id")
    fun getStoreById (id: Long): StoreEntity

    @Insert
    fun addAllStore(storeEntity: StoreEntity): Long

    @Update
    fun updateStore(storeEntity: StoreEntity)

    @Delete
    fun deleteStore(storeEntity: StoreEntity)

}