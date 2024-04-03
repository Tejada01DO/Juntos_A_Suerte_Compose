package com.ucne.juntos_a_suerte_compose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person")
data class Person(
    @PrimaryKey(autoGenerate = true)
    val PersonId: Int = 0,
    val name: String = "",
    val TeamId: Int
)
