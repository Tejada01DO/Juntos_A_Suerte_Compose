package com.ucne.juntos_a_suerte_compose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "team_details")
data class TeamDetails (
    @PrimaryKey(autoGenerate = true)
    val DetailId: Int = 0,
    val teamId: Int,
    val personId : Int,
    val name: String
)