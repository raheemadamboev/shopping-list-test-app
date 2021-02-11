package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import xyz.teamgravity.shoppinglisttest.api.Resource
import xyz.teamgravity.shoppinglisttest.model.ImageResponseModel
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel

class FakeShoppingRepository : ParentRepository {

    private val shoppingItems = mutableListOf<ShoppingItemModel>()

    private val observeShoppingItems = MutableLiveData<List<ShoppingItemModel>>(shoppingItems)
    private val observerTotalCost = MutableLiveData<Float>()

    private var shouldNetworkError = false

    fun shouldReturnNetworkError(value: Boolean) {
        shouldNetworkError = value
    }

    private fun refreshLiveData() {
        observeShoppingItems.postValue(shoppingItems)
        observerTotalCost.postValue(getTotalPrice())
    }

    private fun getTotalPrice() = shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItemModel) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItemModel) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItemModel>> = observeShoppingItems

    override fun observerTotalPrice(): LiveData<Float> = observerTotalCost

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponseModel> =
        if (shouldNetworkError) Resource.error("Error", null)
        else Resource.success(ImageResponseModel(listOf(), 0, 0))
}