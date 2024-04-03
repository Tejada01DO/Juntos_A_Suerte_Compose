package com.ucne.juntos_a_suerte_compose.ui.person

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams
import com.ucne.juntos_a_suerte_compose.data.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PersonState())
    val state = _state.asStateFlow()
    val persons: StateFlow<List<Person>> = personRepository.getAllPersons()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun Guardar(){
        viewModelScope.launch {
            personRepository.savePerson(state.value.person)
        }
    }

    fun Eliminar(){
        viewModelScope.launch {
            personRepository.deletePerson(state.value.person)
        }
    }

    suspend fun SaveList(person: List<Person>){
        personRepository.saveListPerson(person)
    }

    fun onEvent(event: PersonEvent){
        when(event){
            is PersonEvent.NameChanged -> {
                _state.update {
                    it.copy(person = it.person.copy(name = event.name))
                }
            }

            PersonEvent.onSave -> {
                Guardar()
            }

            PersonEvent.onLimpiar -> {
                _state.update {
                    it.copy(person = Person(0,"", 0))
                }
            }

            PersonEvent.onDelete -> {
                Eliminar()
            }
        }
    }
}

data class PersonState(
    val isLoading: Boolean = false,
    val error: String = "",
    val successMessage: String = "",
    val person: Person = Person(0,"", 0)
)

sealed class PersonEvent{
    data class NameChanged(val name: String): PersonEvent()
    object onSave: PersonEvent()
    object onLimpiar: PersonEvent()
    object onDelete: PersonEvent()
}