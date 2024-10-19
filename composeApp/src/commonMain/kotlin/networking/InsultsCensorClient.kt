package networking

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import utils.NetworkError
import utils.Result

class InsultsCensorClient(private val httpClient: HttpClient) {
    suspend fun getCatImages(numberOfCatsImage:Int): Result<List<CatDTO>, NetworkError> {
        val response = try {
            httpClient.get(
                urlString = "https://api.thecatapi.com/v1/images/search"
            ){
                parameter("limit",10)
                contentType()
            }
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        }
        //This is expected response json structure
        // { "Result":"You ***" }
        return when (response.status.value) {
            in 200..299 -> {
                val catImages  = response.body<List<CatDTO>>()
                Result.Success(catImages)
            }
            //For authenticated API response
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}