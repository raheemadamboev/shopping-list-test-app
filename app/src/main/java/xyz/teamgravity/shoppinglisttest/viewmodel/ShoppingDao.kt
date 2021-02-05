package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.lifecycle.LiveData
import androidx.room.*
import xyz.teamgravity.shoppinglisttest.helper.constants.ShoppingDatabase
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ShoppingItemModel)

    @Delete
    suspend fun delete(item: ShoppingItemModel)

    @Query("SELECT * FROM ${ShoppingDatabase.SHOPPING_ITEM_TABLE}")
    fun getShoppingItems(): LiveData<List<ShoppingItemModel>>

    @Query("SELECT SUM(price * amount) FROM ${ShoppingDatabase.SHOPPING_ITEM_TABLE}")
    fun getAllItemPrice() : LiveData<Float>
}