package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.lifecycle.LiveData
import retrofit2.Response
import xyz.teamgravity.shoppinglisttest.api.Resource
import xyz.teamgravity.shoppinglisttest.model.ImageResponseModel
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel

interface ParentRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItemModel)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItemModel)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItemModel>>

    fun observerTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponseModel>
}