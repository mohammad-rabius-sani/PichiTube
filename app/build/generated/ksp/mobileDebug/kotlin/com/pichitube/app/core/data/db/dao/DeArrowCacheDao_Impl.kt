package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.DeArrowCacheEntity
import javax.`annotation`.processing.Generated
import kotlin.Double
import kotlin.Int
import kotlin.Long
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.List
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class DeArrowCacheDao_Impl(
  __db: RoomDatabase,
) : DeArrowCacheDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfDeArrowCacheEntity: EntityInsertAdapter<DeArrowCacheEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfDeArrowCacheEntity = object : EntityInsertAdapter<DeArrowCacheEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `dearrow_cache` (`id`,`video_id`,`alt_title`,`thumbnail_timestamp`,`thumbnail_url`,`fetched_at`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: DeArrowCacheEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.videoId)
        statement.bindText(3, entity.altTitle)
        statement.bindDouble(4, entity.thumbnailTimestamp)
        statement.bindText(5, entity.thumbnailUrl)
        statement.bindLong(6, entity.fetchedAt)
      }
    }
  }

  public override suspend fun insert(cache: DeArrowCacheEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfDeArrowCacheEntity.insert(_connection, cache)
  }

  public override suspend fun getByVideoId(videoId: String): DeArrowCacheEntity? {
    val _sql: String = "SELECT * FROM dearrow_cache WHERE video_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfAltTitle: Int = getColumnIndexOrThrow(_stmt, "alt_title")
        val _columnIndexOfThumbnailTimestamp: Int = getColumnIndexOrThrow(_stmt,
            "thumbnail_timestamp")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfFetchedAt: Int = getColumnIndexOrThrow(_stmt, "fetched_at")
        val _result: DeArrowCacheEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpAltTitle: String
          _tmpAltTitle = _stmt.getText(_columnIndexOfAltTitle)
          val _tmpThumbnailTimestamp: Double
          _tmpThumbnailTimestamp = _stmt.getDouble(_columnIndexOfThumbnailTimestamp)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpFetchedAt: Long
          _tmpFetchedAt = _stmt.getLong(_columnIndexOfFetchedAt)
          _result =
              DeArrowCacheEntity(_tmpId,_tmpVideoId,_tmpAltTitle,_tmpThumbnailTimestamp,_tmpThumbnailUrl,_tmpFetchedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deleteOlderThan(cutoffMs: Long) {
    val _sql: String = "DELETE FROM dearrow_cache WHERE fetched_at < ?"
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
