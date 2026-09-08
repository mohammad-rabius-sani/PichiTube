package com.pichitube.app.core.data.db.dao

import androidx.room.*
import com.pichitube.app.core.data.db.entity.HistoryEntity
import com.pichitube.app.core.data.db.entity.WatchLaterEntity
import com.pichitube.app.core.data.db.entity.SubscriptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY watchedAt DESC")
    fun getAll(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM history WHERE videoId = :videoId LIMIT 1")
    suspend fun getById(videoId: String): HistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HistoryEntity)

    @Query("UPDATE history SET positionMs = :positionMs WHERE videoId = :videoId")
    suspend fun updatePosition(videoId: String, positionMs: Long)

    @Query("DELETE FROM history WHERE videoId = :videoId")
    suspend fun deleteById(videoId: String)

    @Query("DELETE FROM history")
    suspend fun deleteAll()
}

@Dao
interface WatchLaterDao {
    @Query("SELECT * FROM watch_later ORDER BY addedAt DESC")
    fun getAll(): Flow<List<WatchLaterEntity>>

    @Query("SELECT COUNT(*) FROM watch_later WHERE videoId = :videoId")
    suspend fun contains(videoId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WatchLaterEntity)

    @Query("DELETE FROM watch_later WHERE videoId = :videoId")
    suspend fun deleteById(videoId: String)

    @Query("DELETE FROM watch_later")
    suspend fun deleteAll()
}

@Dao
interface SubscriptionDao {
    @Query("SELECT * FROM subscriptions ORDER BY channelName ASC")
    fun getAll(): Flow<List<SubscriptionEntity>>

    @Query("SELECT COUNT(*) FROM subscriptions WHERE channelId = :channelId")
    suspend fun isSubscribed(channelId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SubscriptionEntity)

    @Query("DELETE FROM subscriptions WHERE channelId = :channelId")
    suspend fun deleteById(channelId: String)
}
