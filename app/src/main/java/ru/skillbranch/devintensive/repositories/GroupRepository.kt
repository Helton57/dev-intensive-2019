package ru.skillbranch.devintensive.repositories

import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.utils.DataGenerator

object GroupRepository {
//    private val users = CacheManager.loadUsers()

    //    fun loadUsers() = users

    fun loadUsers(): List<User> = DataGenerator.stabUsers
    fun createChat(items: List<UserItem>) {
        val ids = items.map { it.id }
        val users = CacheManager.findUsersById(ids)
        val title = users.map { it.firstName }.joinToString(", ")
        val chat = Chat(
            id = CacheManager.nextChatId(),
            title = title,
            members = users
        )
        CacheManager.insertChat(chat)
    }
}