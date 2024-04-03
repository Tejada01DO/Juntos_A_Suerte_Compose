package com.ucne.juntos_a_suerte_compose.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import com.ucne.juntos_a_suerte_compose.data.local.entities.TeamDetails
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDao {
    @Upsert
    suspend fun Guardar(team: Teams)

    @Delete
    suspend fun Eliminar(team: Teams)

    @Query("SELECT * FROM teams")
    fun ObtenerEquipos(): Flow<List<Teams>>

    //Transaction
    //@Query("SELECT * FROM teams WHERE TeamId = :teamId")
    //fun ObtenerDetallesEquipo(teamId: Int): TeamDetails

    @Upsert
    suspend fun insertTeamDetails(teamDetails: TeamDetails)

    @Delete
    suspend fun deleteTeamDetails(teamDetails: TeamDetails)

    @Query(""" SELECT * FROM team_details WHERE teamId=:id """)
    fun findTeamDetailsByTeamID(id: Int): Flow<List<TeamDetails>>

    @Query("DELETE FROM team_details WHERE TeamId = :id")
    suspend fun deleteTeamDetailsByTeamID(id: Int)
}