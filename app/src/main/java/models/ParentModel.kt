package models

import java.io.File

data class ParentModel(
    var name: String,
    var age: String,
    var phone: String,
    var address: String,
    var image: File,
    var gender: String
)