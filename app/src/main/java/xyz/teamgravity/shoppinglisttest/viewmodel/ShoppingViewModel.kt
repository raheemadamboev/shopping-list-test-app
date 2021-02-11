package xyz.teamgravity.shoppinglisttest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import xyz.teamgravity.shoppinglisttest.api.Event
import xyz.teamgravity.shoppinglisttest.api.Resource
import xyz.teamgravity.shoppinglisttest.model.ImageResponseModel
import xyz.teamgravity.shoppinglisttest.model.ShoppingItemModel
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repository: ParentRepository
) : ViewModel() {

    companion object {
        const val MAX_NAME_LENGTH = 20
        const val MAX_PRICE_LENGTH = 10
    }

    val shoppingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observerTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponseModel>>>()
    val images: LiveData<Event<Resource<ImageResponseModel>>> = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> = _currentImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItemModel>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItemModel>>> = _insertShoppingItemStatus

    private fun setCurrentImageUrl(url: String) = _currentImageUrl.postValue(url)

    private fun insertShoppingItemIntoDb(shoppingItem: ShoppingItemModel) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if (name.isBlank() || amountString.isBlank() || priceString.isBlank()) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The field should not be empty", null)))
            return
        }

        if (name.trim().length > MAX_NAME_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The name length must be in $MAX_NAME_LENGTH", null)))
            return
        }

        if (priceString.trim().length > MAX_PRICE_LENGTH) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("The price length must be in $MAX_PRICE_LENGTH", null)))
            return
        }

        val amount = try {
            amountString.toInt()
        } catch (e: Exception) {
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter valid amount", null)))
            return
        }

        val shoppingItem = ShoppingItemModel(
            name = name,
            amount = amount,
            price = priceString.toFloat(),
            _currentImageUrl.value ?: ""
        )

        insertShoppingItemIntoDb(shoppingItem)

        setCurrentImageUrl("")

        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItemModel) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isBlank()) {
            return
        }

        _images.value = Event(Resource.loading(null))

        viewModelScope.launch {
            val response = repository.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }
}