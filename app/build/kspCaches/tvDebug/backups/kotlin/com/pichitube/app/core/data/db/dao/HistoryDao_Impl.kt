package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.HistoryEntity
import javax.`annotation`.processing.Generated
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.collections.MutableList
import kotlin.collections.mutableListOf
import kotlin.reflect.KClass
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class HistoryDao_Impl(
  __db: RoomDatabase,
) : HistoryDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfHistoryEntity: EntityInsertAdapter<HistoryEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfHistoryEntity = object : EntityInsertAdapter<HistoryEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `history` (`id`,`user_id`,`video_id`,`title`,`thumbnail_url`,`channel_name`,`duration_seconds`,`watched_at`,`position_ms`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: HistoryEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindText(3, entity.videoId)
        statement.bindText(4, entity.title)
        statement.bindText(5, entity.thumbnailUrl)
        statement.bindText(6, entity.channelName)
        statement.bindLong(7, entity.durationSeconds)
        statement.bindLong(8, entity.watchedAt)
        statement.bindLong(9, entity.positionMs)
      }
    }
  }

  public override suspend fun insert(history: HistoryEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfHistoryEntity.insert(_connection, history)
  }

  public override fun getByUser(userId: Long): Flow<List<HistoryEntity>> {
    val _sql: String = "SELECT * FROM history WHERE user_id = ? ORDER BY watched_at DESC LIMIT 200"
    return createFlow(__db, false, arrayOf("history")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "user_id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfWatchedAt: Int = getColumnIndexOrThrow(_stmt, "watched_at")
        val _columnIndexOfPositionMs: Int = getColumnIndexOrThrow(_stmt, "position_ms")
        val _result: MutableList<HistoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HistoryEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpWatchedAt: Long
          _tmpWatchedAt = _stmt.getLong(_columnIndexOfWatchedAt)
          val _tmpPositionMs: Long
          _tmpPositionMs = _stmt.getLong(_columnIndexOfPositionMs)
          _item =
              HistoryEntity(_tmpId,_tmpUserId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelName,_tmpDurationSeconds,_tmpWatchedAt,_tmpPositionMs)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getEntry(userId: Long, videoId: String): HistoryEntity? {
    val _sql: String = "SELECT * FROM history WHERE user_id = ? AND video_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "user_id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfWatchedAt: Int = getColumnIndexOrThrow(_stmt, "watched_at")
        val _columnIndexOfPositionMs: Int = getColumnIndexOrThrow(_stmt, "position_ms")
        val _result: HistoryEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpWatchedAt: Long
          _tmpWatchedAt = _stmt.getLong(_columnIndexOfWatchedAt)
          val _tmpPositionMs: Long
          _tmpPositionMs = _stmt.getLong(_columnIndexOfPositionMs)
          _result =
              HistoryEntity(_tmpId,_tmpUserId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelName,_tmpDurationSeconds,_tmpWatchedAt,_tmpPositionMs)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePosition(
    userId: Long,
    videoId: String,
    positionMs: Long,
    watchedAt: Long,
  ) {
    val _sql: String =
        "UPDATE history SET position_ms = ?, watched_at = ? WHERE user_id = ? AND video_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, positionMs)
        _argIndex = 2
        _stmt.bindLong(_argIndex, watchedAt)
        _argIndex = 3
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 4
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(userId: Long, videoId: String) {
    val _sql: String = "DELETE FROM history WHERE user_id = ? AND video_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun clearAll(userId: Long) {
    val _sql: String = "DELETE FROM history WHERE user_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteOlderThan(userId: Long, cutoffMs: Long) {
    val _sql: String = "DELETE FROM history WHERE user_id = ? AND watched_at < ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, cutoffMs)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
