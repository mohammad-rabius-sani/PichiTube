package com.pichitube.app.core.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val channelName: String,
    val channelId: String,
    val thumbnailUrl: String,
    val durationSeconds: Long,
    val positionMs: Long = 0L,
    val viewCount: Long = 0L,
    val watchedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "watch_later")
data class WatchLaterEntity(
    @PrimaryKey val videoId: String,
    val title: String,
    val channelName: String,
    val channelId: String,
    val thumbnailUrl: String,
    val durationSeconds: Long,
    val viewCount: Long = 0L,
    val addedAt: Long = System.currentTimeMillis(),
)

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey val channelId: String,
    val channelName: String,
    val channelAvatarUrl: String,
    val subscriberCount: Long = 0L,
    val subscribedAt: Long = System.currentTimeMillis(),
)
