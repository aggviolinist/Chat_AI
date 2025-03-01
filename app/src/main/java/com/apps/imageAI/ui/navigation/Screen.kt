package com.apps.imageAI.ui.navigation

sealed class Screen(val route: String) {
    data object Welcome : Screen("welcome_screen")
    data object Chat : Screen("chat_screen")
    data object RecentChats : Screen("recent_chats")
    data object Subscription : Screen("Subscription_screen")
    data object Language : Screen("language_screen")
}