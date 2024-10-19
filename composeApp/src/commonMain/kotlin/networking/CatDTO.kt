package networking

import kotlinx.serialization.Serializable

@Serializable
data class CatDTO(
    val id:String,
    val url:String,
   // val width:Int,
    //val height:Int
)