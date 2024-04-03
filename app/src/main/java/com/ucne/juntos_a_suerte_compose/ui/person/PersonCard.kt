package com.ucne.juntos_a_suerte_compose.ui.person

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ucne.juntos_a_suerte_compose.data.local.entities.Person
import com.ucne.juntos_a_suerte_compose.data.repository.PersonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PersonCard(personViewModel: PersonViewModel = hiltViewModel(), onDismissRequest: () -> Unit, onSave: (List<Person>) -> Unit){
    var name by remember { mutableStateOf("") }
    val persons by personViewModel.persons.collectAsStateWithLifecycle()
    var newPersons by remember { mutableStateOf(listOf<Person>()) }

    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Personas", style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.Center)
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Nombre o nombres separado por coma") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(text = "Para agregar varias personas, separe los nombres con una coma")
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Button(onClick = {
                        val names = name.split(",")
                        newPersons = newPersons + names.map { Person(name = it, TeamId = 0) }
                        name = ""
                    }) {
                        Text(text = "Agregar")
                    }
                    Button(onClick = {
                        onSave(newPersons)
                        newPersons = listOf()
                    }) {
                        Text(text = "Guardar")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn {
                    items(persons + newPersons) { person ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = person.name)
                            IconButton(onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    personViewModel.Eliminar()
                                }

                                newPersons = newPersons.filter { it != person }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar persona")
                            }
                        }
                    }
                }
            }
        }
    }
}
