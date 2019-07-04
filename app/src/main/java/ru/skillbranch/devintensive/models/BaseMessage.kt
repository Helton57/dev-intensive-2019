package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.extensions.MessageType
import java.util.*

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {

    abstract fun formatMessage() : String

    companion object AbstractFactory{
        var lastId = -1

        fun makeMessage(from : User?, chat : Chat, date : Date = Date(), type : String, payload : Any?, isIncoming : Boolean = false) : BaseMessage {
            lastId++
            return when(type){
                "image" ->
                    ImageMessage(id = "$lastId", from = from, chat = chat, isIncoming = isIncoming, date = date, image = payload as String)
                else ->
                    TextMessage(id = "$lastId", from = from, chat = chat, isIncoming = isIncoming, date = date, text = payload as String)
            }
        }

//        fun makeMessage(from : User?, chat : Chat, date : Date, type : MessageType, payload : Any?, isIncoming : Boolean = false) : BaseMessage {
//            lastId++
//            return when(type){
//                MessageType.IMAGE ->
//                    ImageMessage(id = "$lastId", from = from, chat = chat, isIncoming = isIncoming, date = date, image = payload as String)
//
//                MessageType.TEXT ->
//                    TextMessage(id = "$lastId", from = from, chat = chat, isIncoming = isIncoming, date = date, text = payload as String)
//            }
//        }
    }
}




