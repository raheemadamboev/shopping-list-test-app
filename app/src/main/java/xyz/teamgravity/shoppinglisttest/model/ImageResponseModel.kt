package xyz.teamgravity.shoppinglisttest.model

data class ImageResponseModel(

    val hits: List<ImageResultModel>,
    val total: Int,
    val totalHits: Int
)