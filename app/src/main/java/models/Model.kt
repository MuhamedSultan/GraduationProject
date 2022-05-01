package models

data class Model(
    var name: String? = null,
    var email: String,
    var password: String?,
    var status_code: Int? = 200
)
