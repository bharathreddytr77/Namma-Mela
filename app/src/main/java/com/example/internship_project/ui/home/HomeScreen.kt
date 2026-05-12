package com.example.internship_project.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.internship_project.ui.theme.CurtainRed
import com.example.internship_project.ui.theme.Gold
import com.example.internship_project.ui.theme.StageBlack

@Composable
fun HomeScreen(
    innerPadding: PaddingValues,
    viewModel: HomeViewModel,
    isAdmin: Boolean,
    onAdminClick: () -> Unit
) {
    val play by viewModel.play.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StageBlack)
            .verticalScroll(rememberScrollState())
            .padding(innerPadding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Tonight's Play",
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
                Text("Admin / Edit Tonight's Play")
            }
        }
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1D1010)),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                AsyncImage(
                    model = play.posterUrl,
                    contentDescription = "${play.name} poster",
                    placeholder = ColorPainter(CurtainRed),
                    error = ColorPainter(CurtainRed),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(360.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = play.name.ifBlank { "Untitled Drama" },
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Duration: ${play.duration.ifBlank { "To be announced" }}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
    }
}
