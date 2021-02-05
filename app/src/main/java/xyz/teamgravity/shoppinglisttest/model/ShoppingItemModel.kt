package xyz.teamgravity.shoppinglisttest.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import xyz.teamgravity.shoppinglisttest.helper.constants.ShoppingDatabase

@Entity(tableName = ShoppingDatabase.SHOPPING_ITEM_TABLE)
data class ShoppingItemModel(

    var name: String,
    var amount: Int,
    var price: Float,
    var imageUrl: String,

    @PrimaryKey(autoGenerate = true)
    val id: Long? = null
)
