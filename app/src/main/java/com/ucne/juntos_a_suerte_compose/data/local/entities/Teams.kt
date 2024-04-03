package com.ucne.juntos_a_suerte_compose.data.local.entities


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import androidx.room.TypeConverters


@Entity(tableName = "teams")
data class Teams(
    @PrimaryKey(autoGenerate = true)
    val TeamId: Int = 0,
    val name: String = "",
    //val Members: List<Person>
)

/*data class TeamDetails(
    @Embedded val team: Teams,
    @Relation(
        parentColumn = "TeamId",
        entityColumn = "TeamId"
    )
    val Members: List<Person>
)*/
