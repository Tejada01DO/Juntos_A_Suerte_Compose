package com.ucne.juntos_a_suerte_compose.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Upsert
    suspend fun Guardar(person: Person)

    @Upsert
    suspend fun GuardarLista(person: List<Person>)

    @Delete
    suspend fun Eliminar(person: Person)

    @Query("SELECT * FROM person")
    fun obtenerPersonas(): Flow<List<Person>>

    @Delete
    suspend fun deleteListPerson(person: List<Person>)
}