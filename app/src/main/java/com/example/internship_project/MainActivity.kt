package com.example.internship_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.internship_project.data.ServiceLocator
import com.example.internship_project.ui.admin.AdminEditScreen
import com.example.internship_project.ui.admin.AdminViewModel
import com.example.internship_project.ui.auth.AuthViewModel
import com.example.internship_project.ui.auth.LoginScreen
import com.example.internship_project.ui.auth.UserRole
import com.example.internship_project.ui.cast.CastScreen
import com.example.internship_project.ui.cast.CastViewModel
import com.example.internship_project.ui.fanwall.FanWallScreen
import com.example.internship_project.ui.fanwall.FanWallViewModel
import com.example.internship_project.ui.home.HomeScreen
import com.example.internship_project.ui.home.HomeViewModel
import com.example.internship_project.ui.seats.SeatScreen
import com.example.internship_project.ui.seats.SeatViewModel
import com.example.internship_project.ui.theme.Gold
import com.example.internship_project.ui.theme.Internship_projectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Internship_projectTheme {
                val authViewModel: AuthViewModel = viewModel()
                val authState by authViewModel.uiState.collectAsState()
                val role = authState.role

                if (role == null) {
                    LoginScreen(viewModel = authViewModel)
                } else {
                    key(role) {
                        NammaMelaApp(
                            role = role,
                            onLogout = authViewModel::logout
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun NammaMelaApp(
    role: UserRole,
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val repository = ServiceLocator.repository(context)
    val isAdmin = role == UserRole.ADMIN
    val destinations = if (isAdmin) {
        listOf(
            AppDestination.Home,
            AppDestination.Cast,
            AppDestination.Seats,
            AppDestination.FanWall,
            AppDestination.Admin
        )
    } else {
        listOf(
            AppDestination.Home,
            AppDestination.Cast,
            AppDestination.Seats,
            AppDestination.FanWall
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .heightIn(min = 56.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(start = 16.dp, end = 12.dp, top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isAdmin) "Admin Mode" else "User Mode",
                    color = Gold,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Button(onClick = onLogout) {
                    Text("Logout")
                }
            }
        },
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                destinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Box(
                                modifier = Modifier
                                    .size(28.dp)
                                    .clip(CircleShape)
                                    .background(if (selected) Gold else Color(0xFF4B1212)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = destination.marker,
                                    color = if (selected) Color.Black else Color.White,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        label = { Text(destination.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Home.route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(AppDestination.Home.route) {
                val viewModel: HomeViewModel = viewModel(factory = HomeViewModel.factory(repository))
                HomeScreen(
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                    isAdmin = isAdmin,
                    onAdminClick = { navController.navigate(AppDestination.Admin.route) }
                )
            }
            composable(AppDestination.Cast.route) {
                val viewModel: CastViewModel = viewModel(factory = CastViewModel.factory(repository))
                CastScreen(
                    innerPadding = innerPadding,
                    viewModel = viewModel,
                    isAdmin = isAdmin,
                    onAdminClick = { navController.navigate(AppDestination.Admin.route) }
                )
            }
            composable(AppDestination.Seats.route) {
                val viewModel: SeatViewModel = viewModel(factory = SeatViewModel.factory(repository))
                SeatScreen(innerPadding = innerPadding, viewModel = viewModel)
            }
            composable(AppDestination.FanWall.route) {
                val viewModel: FanWallViewModel = viewModel(factory = FanWallViewModel.factory(repository))
                FanWallScreen(innerPadding = innerPadding, viewModel = viewModel)
            }
            if (isAdmin) {
                composable(AppDestination.Admin.route) {
                    val viewModel: AdminViewModel = viewModel(factory = AdminViewModel.factory(repository))
                    AdminEditScreen(
                        innerPadding = innerPadding,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

private sealed class AppDestination(
    val route: String,
    val label: String,
    val marker: String
) {
    data object Home : AppDestination("home", "Play", "P")
    data object Cast : AppDestination("cast", "Cast", "C")
    data object Seats : AppDestination("seats", "Seats", "S")
    data object FanWall : AppDestination("fan_wall", "Fans", "F")
    data object Admin : AppDestination("admin_edit", "Admin", "A")
}
