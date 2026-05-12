package com.example.internship_project.ui.cast

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.internship_project.data.CastEntity
import com.example.internship_project.ui.theme.Gold
import com.example.internship_project.ui.theme.StageBlack

@Composable
fun CastScreen(
    innerPadding: PaddingValues,
    viewModel: CastViewModel,
    isAdmin: Boolean,
    onAdminClick: () -> Unit
) {
    val cast by viewModel.cast.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(StageBlack)
            .padding(innerPadding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Cast List",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Gold,
                    fontWeight = FontWeight.ExtraBold
                )
                if (isAdmin) {
                    Button(
                        onClick = onAdminClick,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Admin / Edit Cast")
                    }
                }
            }
        }
        items(cast, key = { it.id }) { member ->
            CastMemberRow(member = member)
        }
    }
}

@Composable
private fun CastMemberRow(member: CastEntity) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1D1010))
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AsyncImage(
                model = member.imageUrl,
                contentDescription = "${member.name} photo",
                placeholder = ColorPainter(Color(0xFF4B1212)),
                error = ColorPainter(Color(0xFF4B1212)),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(76.dp)
                    .clip(CircleShape)
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = member.role,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Gold
                )
            }
        }
    }
}
