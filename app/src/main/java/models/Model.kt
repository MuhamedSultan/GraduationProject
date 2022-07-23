package models

data class Model(
    var name: String? = null,
    var email: String,
    var phone:String?=null,
    var password: String?,
    var status_code: Int? = 200
)
