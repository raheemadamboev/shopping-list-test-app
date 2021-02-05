package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.room.Database
import androidx.room.RoomDatabase
import xyz.teamgravity.shoppinglisttest.helper.constants.ShoppingDatabase
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel

@Database(entities = [ShoppingItemModel::class], version = ShoppingDatabase.DATABASE_VERSION)
abstract class MyDatabase : RoomDatabase() {

    abstract fun dao(): ShoppingDao
}