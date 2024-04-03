package com.ucne.juntos_a_suerte_compose.data.repository

import com.ucne.juntos_a_suerte_compose.data.local.dao.PersonDao
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val personDao: PersonDao
) {
    suspend fun savePerson(person: Person){
        personDao.Guardar(person)
    }

    suspend fun deletePerson(person: Person){
        personDao.Eliminar(person)
    }

    fun getAllPersons(): Flow<List<Person>>{
        return personDao.obtenerPersonas()
    }

    suspend fun deleteListPerson(person: List<Person>){
        personDao.deleteListPerson(person)
    }

    suspend fun saveListPerson(person: List<Person>){
        personDao.GuardarLista(person)
    }
}