package api

class APIUtils {

    companion object {
        // val API_URL: String = "http://we-care1.herokuapp.com/api/"
        val API_URL: String = "https://grad-project3.000webhostapp.com/api/"
        fun getFileService(): Api {
            return RetrofitClient.getClient(API_URL)!!.create(Api::class.java)
        }
    }
}