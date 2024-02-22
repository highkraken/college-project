package com.example.collegeproject.utils

import androidx.compose.ui.graphics.vector.ImageVector

data class AdminBottomBarItem(
    val label: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
)