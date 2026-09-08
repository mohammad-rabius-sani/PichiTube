package com.pichitube.app.navigation

object Routes {
    const val HOME = "home"
    const val SEARCH = "search"
    const val SHORTS = "shorts"
    const val SUBSCRIPTIONS = "subscriptions"
    const val LIBRARY = "library"
    const val SETTINGS = "settings"
    const val PLAYER = "player/{videoId}"
    const val CHANNEL = "channel/{channelId}"

    fun player(videoId: String) = "player/$videoId"
    fun channel(channelId: String) = "channel/$channelId"
}
