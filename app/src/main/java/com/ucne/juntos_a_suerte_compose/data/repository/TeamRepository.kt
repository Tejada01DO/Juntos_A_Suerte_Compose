package com.ucne.juntos_a_suerte_compose.data.repository

import com.ucne.juntos_a_suerte_compose.data.local.dao.TeamDao
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import com.ucne.juntos_a_suerte_compose.data.local.entities.TeamDetails
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TeamRepository @Inject constructor(
    private val teamDao: TeamDao
) {
    suspend fun saveTeam(team: Teams){
        teamDao.Guardar(team)
    }

    suspend fun deleteTeam(team: Teams){
        teamDao.Eliminar(team)
    }

    fun getAll(): Flow<List<Teams>> {
        return teamDao.ObtenerEquipos()
    }

    fun findTeamDetailsByTeamID(id: Int): Flow<List<TeamDetails>> {
        return teamDao.findTeamDetailsByTeamID(id)
    }

    suspend fun insertTeamDetails(teamDetails: TeamDetails) {
        teamDao.insertTeamDetails(teamDetails)
    }

    suspend fun deleteTeamDetails(teamDetails: TeamDetails) {
        teamDao.deleteTeamDetails(teamDetails)
    }

    suspend fun deleteTeamDetailsByTeamID(id: Int) {
        teamDao.deleteTeamDetailsByTeamID(id)
    }
}