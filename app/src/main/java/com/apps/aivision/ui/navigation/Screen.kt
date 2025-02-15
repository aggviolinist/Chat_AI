package com.apps.aivision.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome_screen")
    object Chat : Screen("chat_screen")
    object RecentChats : Screen("recent_chats")
    object Subscription : Screen("Subscription_screen")
    object Language : Screen("language_screen")
}