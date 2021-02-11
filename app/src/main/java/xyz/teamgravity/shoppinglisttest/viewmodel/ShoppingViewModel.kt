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
        const val MAX_LENGTH_LENGTH = 20
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

    fun setCurrentImageUrl(url: String) = _currentImageUrl.postValue(url)

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItemModel) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {

    }

    fun deleteShoppingItem(shoppingItem: ShoppingItemModel) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    fun searchForImage(imageQuery: String) {

    }
}