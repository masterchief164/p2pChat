package com.example.p2pchat.data

import java.security.PublicKey

data class User(
    val name: String,
    val picture: String,
    val publicKey: PublicKey? = null
    )
