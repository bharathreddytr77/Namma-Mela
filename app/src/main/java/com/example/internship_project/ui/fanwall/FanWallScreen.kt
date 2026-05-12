package com.example.internship_project.ui.fanwall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import com.example.internship_project.data.CommentEntity
import com.example.internship_project.ui.theme.Gold
import com.example.internship_project.ui.theme.StageBlack
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FanWallScreen(
    innerPadding: PaddingValues,
    viewModel: FanWallViewModel
) {
    val comments by viewModel.comments.collectAsState()
    val commentText by viewModel.commentText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StageBlack)
            .padding(innerPadding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Fan Wall",
            style = MaterialTheme.typography.headlineMedium,
            color = Gold,
            fontWeight = FontWeight.ExtraBold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = commentText,
                onValueChange = viewModel::updateComment,
                label = { Text("Cheer for the troupe") },
                modifier = Modifier.weight(1f),
                minLines = 1,
                maxLines = 3
            )
            Button(
                onClick = viewModel::addComment,
                enabled = commentText.isNotBlank(),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Post")
            }
        }
        if (comments.isEmpty()) {
            Text(
                text = "No fan notes yet.",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 8.dp)
            ) {
                items(comments, key = { it.id }) { comment ->
                    CommentCard(comment)
                }
            }
        }
    }
}

@Composable
private fun CommentCard(comment: CommentEntity) {
    val formatter = SimpleDateFormat("dd MMM, h:mm a", Locale.getDefault())

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1D1010))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = comment.text,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = formatter.format(Date(comment.timestamp)),
                color = Gold,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
