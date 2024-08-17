package networking

import kotlinx.serialization.Serializable

@Serializable
data class CensoredTextDTO(
    val result: String
)