package com.ucne.juntos_a_suerte_compose.ui.team

import android.telecom.Call.Details
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import com.ucne.juntos_a_suerte_compose.data.local.entities.TeamDetails
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams
import com.ucne.juntos_a_suerte_compose.data.repository.PersonRepository
import com.ucne.juntos_a_suerte_compose.data.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {
    private var _uiState = MutableStateFlow(TeamState())
    val teams: StateFlow<List<Teams>> = teamRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun Guardar(team: Teams){
        viewModelScope.launch {
            teamRepository.saveTeam(team)
        }
    }

    fun Eliminar(team: Teams){
        viewModelScope.launch {
            teamRepository.deleteTeam(team)
        }
    }

    fun obtenerDetallesEquipo(teamId: Int) : StateFlow<List<TeamDetails?>>{
        val listaDetalle : StateFlow<List<TeamDetails?>> = teamRepository.findTeamDetailsByTeamID(teamId)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )
        return listaDetalle

        /*viewModelScope.launch{
            var Details = teamRepository.findTeamDetailsByTeamID(teamId)
            if(Details != null){
                _uiState.update { it.copy(teamDetails = Details) }
                listaDetalle = Details
                for (detail in Details){
                    println("Detalle: ${detail.personId}")
                }
                for (detail in listaDetalle){
                    if (detail != null) {
                        println("DetalleLista: ${detail.personId}")
                    }
                }
            }
            else
            {
                listaDetalle = emptyList()
            }
        }
        for (detail in listaDetalle){
            if (detail != null) {
                println("DetalleListaDespues: ${detail.personId}")
            }
        }
        return listaDetalle*/
    }


    suspend fun shufflePeopleIntoTeam(people: List<Person>){
        val shuffledPeople = people.shuffled(Random)

        teams.value.forEach { team -> teamRepository.deleteTeamDetailsByTeamID(team.TeamId) }
        var teamIndex = 0
        var numTeams = teams.value.count()

        for (person in shuffledPeople){
            teamRepository.insertTeamDetails(TeamDetails(0, teams.value[teamIndex].TeamId, person.PersonId, person.name))

            teamIndex = (teamIndex + 1) % numTeams
        }
    }

}

data class TeamState(
    val isLoading: Boolean = false,
    val error: String = "",
    val successMessage: String = "",
    val team: Teams = Teams(),
    val teamDetails: List<TeamDetails> = listOf<TeamDetails>()
)

sealed class TeamEvent{
    data class NameChanged(val name: String): TeamEvent()
    object onSave: TeamEvent()
    object onLimpiar: TeamEvent()
    object onDelete: TeamEvent()
    object onSaveDetails: TeamEvent()
    object onDeleteDetails: TeamEvent()
}