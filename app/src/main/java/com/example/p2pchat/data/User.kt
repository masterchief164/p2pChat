package com.example.p2pchat.data

import java.io.Serializable
import java.security.PublicKey

data class User(
    val name: String,
    val picture: String,
    val publicKey: PublicKey? = null
    ): Serializable
