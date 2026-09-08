package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.VideoEntity
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
public class VideoDao_Impl(
  __db: RoomDatabase,
) : VideoDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfVideoEntity: EntityInsertAdapter<VideoEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfVideoEntity = object : EntityInsertAdapter<VideoEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `videos` (`id`,`video_id`,`title`,`thumbnail_url`,`channel_id`,`channel_name`,`channel_avatar_url`,`view_count`,`duration_seconds`,`published_at`,`is_short`,`is_live`,`description`,`cached_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: VideoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.videoId)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.thumbnailUrl)
        statement.bindText(5, entity.channelId)
        statement.bindText(6, entity.channelName)
        statement.bindText(7, entity.channelAvatarUrl)
        statement.bindLong(8, entity.viewCount)
        statement.bindLong(9, entity.durationSeconds)
        statement.bindLong(10, entity.publishedAt)
        val _tmp: Int = if (entity.isShort) 1 else 0
        statement.bindLong(11, _tmp.toLong())
        val _tmp_1: Int = if (entity.isLive) 1 else 0
        statement.bindLong(12, _tmp_1.toLong())
        statement.bindText(13, entity.description)
        statement.bindLong(14, entity.cachedAt)
      }
    }
  }

  public override suspend fun insertAll(videos: List<VideoEntity>): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfVideoEntity.insert(_connection, videos)
  }

  public override suspend fun getById(videoId: String): VideoEntity? {
    val _sql: String = "SELECT * FROM videos WHERE video_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channel_id")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfChannelAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "channel_avatar_url")
        val _columnIndexOfViewCount: Int = getColumnIndexOrThrow(_stmt, "view_count")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfPublishedAt: Int = getColumnIndexOrThrow(_stmt, "published_at")
        val _columnIndexOfIsShort: Int = getColumnIndexOrThrow(_stmt, "is_short")
        val _columnIndexOfIsLive: Int = getColumnIndexOrThrow(_stmt, "is_live")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cached_at")
        val _result: VideoEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpChannelAvatarUrl: String
          _tmpChannelAvatarUrl = _stmt.getText(_columnIndexOfChannelAvatarUrl)
          val _tmpViewCount: Long
          _tmpViewCount = _stmt.getLong(_columnIndexOfViewCount)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpPublishedAt: Long
          _tmpPublishedAt = _stmt.getLong(_columnIndexOfPublishedAt)
          val _tmpIsShort: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsShort).toInt()
          _tmpIsShort = _tmp != 0
          val _tmpIsLive: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsLive).toInt()
          _tmpIsLive = _tmp_1 != 0
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _result =
              VideoEntity(_tmpId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelId,_tmpChannelName,_tmpChannelAvatarUrl,_tmpViewCount,_tmpDurationSeconds,_tmpPublishedAt,_tmpIsShort,_tmpIsLive,_tmpDescription,_tmpCachedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getAllNonShorts(): Flow<List<VideoEntity>> {
    val _sql: String = "SELECT * FROM videos WHERE is_short = 0 ORDER BY cached_at DESC LIMIT 100"
    return createFlow(__db, false, arrayOf("videos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channel_id")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfChannelAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "channel_avatar_url")
        val _columnIndexOfViewCount: Int = getColumnIndexOrThrow(_stmt, "view_count")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfPublishedAt: Int = getColumnIndexOrThrow(_stmt, "published_at")
        val _columnIndexOfIsShort: Int = getColumnIndexOrThrow(_stmt, "is_short")
        val _columnIndexOfIsLive: Int = getColumnIndexOrThrow(_stmt, "is_live")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cached_at")
        val _result: MutableList<VideoEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: VideoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpChannelAvatarUrl: String
          _tmpChannelAvatarUrl = _stmt.getText(_columnIndexOfChannelAvatarUrl)
          val _tmpViewCount: Long
          _tmpViewCount = _stmt.getLong(_columnIndexOfViewCount)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpPublishedAt: Long
          _tmpPublishedAt = _stmt.getLong(_columnIndexOfPublishedAt)
          val _tmpIsShort: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsShort).toInt()
          _tmpIsShort = _tmp != 0
          val _tmpIsLive: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsLive).toInt()
          _tmpIsLive = _tmp_1 != 0
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _item =
              VideoEntity(_tmpId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelId,_tmpChannelName,_tmpChannelAvatarUrl,_tmpViewCount,_tmpDurationSeconds,_tmpPublishedAt,_tmpIsShort,_tmpIsLive,_tmpDescription,_tmpCachedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getShorts(): Flow<List<VideoEntity>> {
    val _sql: String = "SELECT * FROM videos WHERE is_short = 1 ORDER BY cached_at DESC LIMIT 50"
    return createFlow(__db, false, arrayOf("videos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channel_id")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfChannelAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "channel_avatar_url")
        val _columnIndexOfViewCount: Int = getColumnIndexOrThrow(_stmt, "view_count")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfPublishedAt: Int = getColumnIndexOrThrow(_stmt, "published_at")
        val _columnIndexOfIsShort: Int = getColumnIndexOrThrow(_stmt, "is_short")
        val _columnIndexOfIsLive: Int = getColumnIndexOrThrow(_stmt, "is_live")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cached_at")
        val _result: MutableList<VideoEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: VideoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpChannelAvatarUrl: String
          _tmpChannelAvatarUrl = _stmt.getText(_columnIndexOfChannelAvatarUrl)
          val _tmpViewCount: Long
          _tmpViewCount = _stmt.getLong(_columnIndexOfViewCount)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpPublishedAt: Long
          _tmpPublishedAt = _stmt.getLong(_columnIndexOfPublishedAt)
          val _tmpIsShort: Boolean
          val _tmp: Int
          _tmp = _stmt.getLong(_columnIndexOfIsShort).toInt()
          _tmpIsShort = _tmp != 0
          val _tmpIsLive: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsLive).toInt()
          _tmpIsLive = _tmp_1 != 0
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _item =
              VideoEntity(_tmpId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelId,_tmpChannelName,_tmpChannelAvatarUrl,_tmpViewCount,_tmpDurationSeconds,_tmpPublishedAt,_tmpIsShort,_tmpIsLive,_tmpDescription,_tmpCachedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteOlderThan(cutoffMs: Long) {
    val _sql: String = "DELETE FROM videos WHERE cached_at < ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
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
