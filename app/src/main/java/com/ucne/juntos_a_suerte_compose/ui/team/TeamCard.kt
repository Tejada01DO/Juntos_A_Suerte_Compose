package com.ucne.juntos_a_suerte_compose.ui.team

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ucne.juntos_a_suerte_compose.data.local.dao.TeamDao
import com.ucne.juntos_a_suerte_compose.data.local.entities.TeamDetails
import com.ucne.juntos_a_suerte_compose.data.local.entities.Teams
import com.ucne.juntos_a_suerte_compose.ui.home.MyDivider
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TeamCard(
    teams: Teams,
    teamViewModel: TeamViewModel = hiltViewModel(),
    miembros: List<TeamDetails?>
) {
    val (timer, setTimer) = remember { mutableStateOf(0) }
    LaunchedEffect(true) {
        delay(5000)
        setTimer(1)
    }
    if (timer == 0) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = androidx.compose.ui.Alignment.Center

        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

    else{
        Card(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(text = teams.name, style = MaterialTheme.typography.titleMedium)
                    IconButton(onClick = { teamViewModel.Eliminar(teams) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar equipo")
                    }
                }
                MyDivider()
                miembros.forEach{ person ->
                    if (person != null) {
                        Text(text = "${person.name}", style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(vertical = 4.dp))
                    }
                }
            }
        }
    }
}