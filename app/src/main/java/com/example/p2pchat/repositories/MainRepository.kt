package com.example.p2pchat.repositories

interface MainRepository {
    suspend fun getMessages()

    suspend fun writeMessages()

    suspend fun broadcastMessage()

    suspend fun searchForPeers()
}