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
          "INSERT OR REPLACE INTO `history` (`videoId`,`title`,`channelName`,`channelId`,`thumbnailUrl`,`durationSeconds`,`positionMs`,`viewCount`,`watchedAt`) VALUES (?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: HistoryEntity) {
        statement.bindText(1, entity.videoId)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.channelName)
        statement.bindText(4, entity.channelId)
        statement.bindText(5, entity.thumbnailUrl)
        statement.bindLong(6, entity.durationSeconds)
        statement.bindLong(7, entity.positionMs)
        statement.bindLong(8, entity.viewCount)
        statement.bindLong(9, entity.watchedAt)
      }
    }
  }

  public override suspend fun insert(entity: HistoryEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __insertAdapterOfHistoryEntity.insert(_connection, entity)
  }

  public override fun getAll(): Flow<List<HistoryEntity>> {
    val _sql: String = "SELECT * FROM history ORDER BY watchedAt DESC"
    return createFlow(__db, false, arrayOf("history")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channelName")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnailUrl")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfPositionMs: Int = getColumnIndexOrThrow(_stmt, "positionMs")
        val _columnIndexOfViewCount: Int = getColumnIndexOrThrow(_stmt, "viewCount")
        val _columnIndexOfWatchedAt: Int = getColumnIndexOrThrow(_stmt, "watchedAt")
        val _result: MutableList<HistoryEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: HistoryEntity
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpPositionMs: Long
          _tmpPositionMs = _stmt.getLong(_columnIndexOfPositionMs)
          val _tmpViewCount: Long
          _tmpViewCount = _stmt.getLong(_columnIndexOfViewCount)
          val _tmpWatchedAt: Long
          _tmpWatchedAt = _stmt.getLong(_columnIndexOfWatchedAt)
          _item =
              HistoryEntity(_tmpVideoId,_tmpTitle,_tmpChannelName,_tmpChannelId,_tmpThumbnailUrl,_tmpDurationSeconds,_tmpPositionMs,_tmpViewCount,_tmpWatchedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(videoId: String): HistoryEntity? {
    val _sql: String = "SELECT * FROM history WHERE videoId = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channelName")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnailUrl")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfPositionMs: Int = getColumnIndexOrThrow(_stmt, "positionMs")
        val _columnIndexOfViewCount: Int = getColumnIndexOrThrow(_stmt, "viewCount")
        val _columnIndexOfWatchedAt: Int = getColumnIndexOrThrow(_stmt, "watchedAt")
        val _result: HistoryEntity?
        if (_stmt.step()) {
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpPositionMs: Long
          _tmpPositionMs = _stmt.getLong(_columnIndexOfPositionMs)
          val _tmpViewCount: Long
          _tmpViewCount = _stmt.getLong(_columnIndexOfViewCount)
          val _tmpWatchedAt: Long
          _tmpWatchedAt = _stmt.getLong(_columnIndexOfWatchedAt)
          _result =
              HistoryEntity(_tmpVideoId,_tmpTitle,_tmpChannelName,_tmpChannelId,_tmpThumbnailUrl,_tmpDurationSeconds,_tmpPositionMs,_tmpViewCount,_tmpWatchedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updatePosition(videoId: String, positionMs: Long) {
    val _sql: String = "UPDATE history SET positionMs = ? WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, positionMs)
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(videoId: String) {
    val _sql: String = "DELETE FROM history WHERE videoId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteAll() {
    val _sql: String = "DELETE FROM history"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
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
