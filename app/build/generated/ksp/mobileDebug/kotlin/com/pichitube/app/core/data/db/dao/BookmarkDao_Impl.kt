package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.BookmarkEntity
import javax.`annotation`.processing.Generated
import kotlin.Boolean
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
public class BookmarkDao_Impl(
  __db: RoomDatabase,
) : BookmarkDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfBookmarkEntity: EntityInsertAdapter<BookmarkEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfBookmarkEntity = object : EntityInsertAdapter<BookmarkEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `bookmarks` (`id`,`user_id`,`video_id`,`title`,`thumbnail_url`,`channel_name`,`duration_seconds`,`added_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: BookmarkEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindText(3, entity.videoId)
        statement.bindText(4, entity.title)
        statement.bindText(5, entity.thumbnailUrl)
        statement.bindText(6, entity.channelName)
        statement.bindLong(7, entity.durationSeconds)
        statement.bindLong(8, entity.addedAt)
      }
    }
  }

  public override suspend fun insert(bookmark: BookmarkEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfBookmarkEntity.insert(_connection, bookmark)
  }

  public override fun getByUser(userId: Long): Flow<List<BookmarkEntity>> {
    val _sql: String = "SELECT * FROM bookmarks WHERE user_id = ? ORDER BY added_at DESC"
    return createFlow(__db, false, arrayOf("bookmarks")) { _connection ->
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
        val _columnIndexOfAddedAt: Int = getColumnIndexOrThrow(_stmt, "added_at")
        val _result: MutableList<BookmarkEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: BookmarkEntity
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
          val _tmpAddedAt: Long
          _tmpAddedAt = _stmt.getLong(_columnIndexOfAddedAt)
          _item =
              BookmarkEntity(_tmpId,_tmpUserId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelName,_tmpDurationSeconds,_tmpAddedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun isBookmarked(userId: Long, videoId: String): Flow<Boolean> {
    val _sql: String = "SELECT EXISTS(SELECT 1 FROM bookmarks WHERE user_id = ? AND video_id = ?)"
    return createFlow(__db, false, arrayOf("bookmarks")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindText(_argIndex, videoId)
        val _result: Boolean
        if (_stmt.step()) {
          val _tmp: Int
          _tmp = _stmt.getLong(0).toInt()
          _result = _tmp != 0
        } else {
          _result = false
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(userId: Long, videoId: String) {
    val _sql: String = "DELETE FROM bookmarks WHERE user_id = ? AND video_id = ?"
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
