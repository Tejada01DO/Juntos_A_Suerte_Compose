package com.ucne.juntos_a_suerte_compose.ui.home

import android.provider.MediaStore.Audio.Genres.Members
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.room.Dao
import androidx.room.Delete
import com.ucne.juntos_a_suerte_compose.data.local.dao.TeamDao
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams
import com.ucne.juntos_a_suerte_compose.data.repository.PersonRepository
import com.ucne.juntos_a_suerte_compose.data.repository.TeamRepository
import com.ucne.juntos_a_suerte_compose.ui.person.PersonCard
import com.ucne.juntos_a_suerte_compose.ui.person.PersonViewModel
import com.ucne.juntos_a_suerte_compose.ui.team.TeamCard
import com.ucne.juntos_a_suerte_compose.ui.team.TeamViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Home(
    teamViewModel: TeamViewModel = hiltViewModel(),
    personViewModel: PersonViewModel = hiltViewModel()
){
    var groupName by remember { mutableStateOf("") }
    var isDialogOpen by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val teams by teamViewModel.teams.collectAsStateWithLifecycle()
    val persons by personViewModel.persons.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "GRUPOS", style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Row(
            Modifier
                .fillMaxWidth()
                .padding(8.dp)) {
            OutlinedTextField(
                value = groupName,
                onValueChange = { groupName = it },
                label = { Text("Nombre del grupo") },
                singleLine = true,
                modifier = Modifier
                    .weight(7f)
                    .fillMaxWidth()
                    .padding(end = 8.dp)
            )

            Button(
                onClick = {
                    val team = Teams(TeamId = 0, name = if(groupName.isBlank()) "Sin nombre" else groupName)
                    coroutineScope.launch {
                        teamViewModel.Guardar(team)
                    }
                    groupName = ""
                },
                modifier = Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .height(68.dp),
                shape = RectangleShape
            ) {
                Text(text = "ADD")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { isDialogOpen = true }) {
                Text(text = "ADD Personas")
            }
            Button(onClick = {
                scope.launch { println("Use el boton")
                    teamViewModel.shufflePeopleIntoTeam(persons) } }) {
                Text(text = "Sortear")
            }
        }
        MyDivider()
        MyDivider()

        for (
            team in teams
        ) {
            val members by teamViewModel.obtenerDetallesEquipo(team.TeamId).collectAsStateWithLifecycle()
            for (member in members){
                println("Miembro: ${member?.personId}")
            }
            TeamCard(
                teams = team,
                miembros = members
            )
        }
    }

    if(isDialogOpen){
        PersonCard(
            onDismissRequest = { isDialogOpen = false },
            onSave = { persons ->
                coroutineScope.launch {
                    personViewModel.SaveList(persons)
                    isDialogOpen = false
                }
            }
        )
    }
}
@Composable
fun MyDivider(){
    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 8.dp))
}