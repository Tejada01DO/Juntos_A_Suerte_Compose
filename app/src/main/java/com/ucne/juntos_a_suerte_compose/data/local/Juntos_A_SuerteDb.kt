package com.ucne.juntos_a_suerte_compose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ucne.juntos_a_suerte_compose.data.local.dao.PersonDao
import com.ucne.juntos_a_suerte_compose.data.local.dao.TeamDao
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import com.ucne.juntos_a_suerte_compose.data.local.entities.TeamDetails
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams


@Database(entities = [Person::class, Teams::class, TeamDetails::class], version = 12, exportSchema = false)
abstract class Juntos_A_SuerteDb : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun teamDao(): TeamDao
}