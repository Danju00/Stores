package com.cursosant.android.stores

@Database (entities = arrayOf(StoreEntity::class),versicion = 2)
abstract class StoreDatabase : RoomDatabase() {
    abstract  fun storeDao(): StoreDao


}