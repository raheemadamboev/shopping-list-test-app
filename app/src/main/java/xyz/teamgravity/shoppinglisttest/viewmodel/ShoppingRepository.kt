package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.lifecycle.LiveData
import xyz.teamgravity.shoppinglisttest.api.PixabayApi
import xyz.teamgravity.shoppinglisttest.api.Resource
import xyz.teamgravity.shoppinglisttest.model.ImageResponseModel
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val dao: ShoppingDao,
    private val api: PixabayApi
) : ParentRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItemModel) = dao.insert(shoppingItem)

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItemModel) = dao.delete(shoppingItem)

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItemModel>> = dao.getShoppingItems()

    override fun observerTotalPrice(): LiveData<Float> = dao.getAllItemPrice()

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponseModel> = try {
        val respond = api.searchImage(imageQuery)

        if (respond.isSuccessful) {
            if (respond.body() != null) {
                Resource.success(respond.body())
            } else {
                Resource.error("Response is null", null)
            }
        } else {
            Resource.error("Unknown error occurred", null)
        }
    } catch (e: Exception) {
        Resource.error("Couldn't reach the server. Check your internet connection", null)
    }
}