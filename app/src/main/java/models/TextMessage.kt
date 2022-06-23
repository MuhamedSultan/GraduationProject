package models

import java.util.*

data class TextMessage(
    val text: String,
    val senderId: String,
    val date: Date){
    constructor(): this("", "", Date())
}
