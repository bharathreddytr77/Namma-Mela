package com.example.internship_project.ui.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.internship_project.ui.theme.Gold
import com.example.internship_project.ui.theme.StageBlack

@Composable
fun AdminEditScreen(
    innerPadding: PaddingValues,
    viewModel: AdminViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StageBlack)
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Admin Edit",
            style = MaterialTheme.typography.headlineMedium,
            color = Gold,
            fontWeight = FontWeight.ExtraBold
        )
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1D1010)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Tonight's Play",
                    style = MaterialTheme.typography.titleLarge,
                    color = Gold,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = state.playName,
                    onValueChange = viewModel::updatePlayName,
                    label = { Text("Play name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.duration,
                    onValueChange = viewModel::updateDuration,
                    label = { Text("Duration") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = state.posterUrl,
                    onValueChange = viewModel::updatePosterUrl,
                    label = { Text("Poster URL") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Text(
            text = "Cast",
            style = MaterialTheme.typography.titleLarge,
            color = Gold,
            fontWeight = FontWeight.Bold
        )
        state.cast.forEach { member ->
            CastEditor(
                member = member,
                onNameChange = { viewModel.updateCastName(member.id, it) },
                onRoleChange = { viewModel.updateCastRole(member.id, it) },
                onImageUrlChange = { viewModel.updateCastImageUrl(member.id, it) }
            )
        }
        if (state.savedMessage.isNotBlank()) {
            Text(
                text = state.savedMessage,
                color = Gold,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Back")
            }
            Button(
                onClick = viewModel::saveChanges,
                enabled = state.isLoaded,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text("Save")
            }
        }
        OutlinedButton(
            onClick = viewModel::saveAsNewShow,
            enabled = state.isLoaded,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save as New Show & Reset Seats/Fans")
        }
        OutlinedButton(
            onClick = viewModel::clearFanComments,
            enabled = state.isLoaded,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear Fan Wall Comments")
        }
    }
}

@Composable
private fun CastEditor(
    member: CastEditState,
    onNameChange: (String) -> Unit,
    onRoleChange: (String) -> Unit,
    onImageUrlChange: (String) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1D1010)),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Actor ${member.id}",
                style = MaterialTheme.typography.titleMedium,
                color = Gold,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = member.name,
                onValueChange = onNameChange,
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = member.role,
                onValueChange = onRoleChange,
                label = { Text("Role") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = member.imageUrl,
                onValueChange = onImageUrlChange,
                label = { Text("Image URL") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
