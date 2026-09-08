package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.WatchLaterEntity
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
public class WatchLaterDao_Impl(
  __db: RoomDatabase,
) : WatchLaterDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfWatchLaterEntity: EntityInsertAdapter<WatchLaterEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfWatchLaterEntity = object : EntityInsertAdapter<WatchLaterEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `watch_later` (`videoId`,`title`,`channelName`,`channelId`,`thumbnailUrl`,`durationSeconds`,`viewCount`,`addedAt`) VALUES (?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: WatchLaterEntity) {
        statement.bindText(1, entity.videoId)
        statement.bindText(2, entity.title)
        statement.bindText(3, entity.channelName)
        statement.bindText(4, entity.channelId)
        statement.bindText(5, entity.thumbnailUrl)
        statement.bindLong(6, entity.durationSeconds)
        statement.bindLong(7, entity.viewCount)
        statement.bindLong(8, entity.addedAt)
      }
    }
  }

  public override suspend fun insert(entity: WatchLaterEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfWatchLaterEntity.insert(_connection, entity)
  }

  public override fun getAll(): Flow<List<WatchLaterEntity>> {
    val _sql: String = "SELECT * FROM watch_later ORDER BY addedAt DESC"
    return createFlow(__db, false, arrayOf("watch_later")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "videoId")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channelName")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnailUrl")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "durationSeconds")
        val _columnIndexOfViewCount: Int = getColumnIndexOrThrow(_stmt, "viewCount")
        val _columnIndexOfAddedAt: Int = getColumnIndexOrThrow(_stmt, "addedAt")
        val _result: MutableList<WatchLaterEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: WatchLaterEntity
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
          val _tmpViewCount: Long
          _tmpViewCount = _stmt.getLong(_columnIndexOfViewCount)
          val _tmpAddedAt: Long
          _tmpAddedAt = _stmt.getLong(_columnIndexOfAddedAt)
          _item =
              WatchLaterEntity(_tmpVideoId,_tmpTitle,_tmpChannelName,_tmpChannelId,_tmpThumbnailUrl,_tmpDurationSeconds,_tmpViewCount,_tmpAddedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun contains(videoId: String): Int {
    val _sql: String = "SELECT COUNT(*) FROM watch_later WHERE videoId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _result: Int
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp
        } else {
          _result = 0
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteById(videoId: String) {
    val _sql: String = "DELETE FROM watch_later WHERE videoId = ?"
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
    val _sql: String = "DELETE FROM watch_later"
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
