package com.example.internship_project.ui.seats

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.internship_project.data.SeatEntity
import com.example.internship_project.ui.theme.Gold
import com.example.internship_project.ui.theme.ReservedRed
import com.example.internship_project.ui.theme.SeatGreen
import com.example.internship_project.ui.theme.StageBlack

@Composable
fun SeatScreen(
    innerPadding: PaddingValues,
    viewModel: SeatViewModel
) {
    val seats by viewModel.seats.collectAsState()
    val reservedCount = seats.count { it.isReserved }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(StageBlack)
            .padding(innerPadding)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Seat Booking",
            style = MaterialTheme.typography.headlineMedium,
            color = Gold,
            fontWeight = FontWeight.ExtraBold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF8F1010)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "STAGE",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LegendItem(color = SeatGreen, label = "Available")
            LegendItem(color = ReservedRed, label = "Reserved")
            Text(
                text = "$reservedCount / 40 booked",
                color = Gold,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(8),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            userScrollEnabled = false
        ) {
            items(seats, key = { it.id }) { seat ->
                SeatTile(
                    seat = seat,
                    onClick = { viewModel.reserveSeat(seat) }
                )
            }
        }
        Surface(
            color = Color(0xFF1D1010),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color(0xFF805B22)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Tap any green seat to reserve it. Red seats are already booked and cannot be changed.",
                modifier = Modifier.padding(14.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun SeatTile(
    seat: SeatEntity,
    onClick: () -> Unit
) {
    val background = if (seat.isReserved) ReservedRed else SeatGreen
    val label = "${('A'.code + seat.row - 1).toChar()}${seat.column}"

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .border(1.dp, Color.Black.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
            .clickable(enabled = !seat.isReserved, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LegendItem(color: Color, label: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(14.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(color)
        )
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
