package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.SponsorSegmentEntity
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

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class SponsorSegmentDao_Impl(
  __db: RoomDatabase,
) : SponsorSegmentDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSponsorSegmentEntity: EntityInsertAdapter<SponsorSegmentEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSponsorSegmentEntity = object :
        EntityInsertAdapter<SponsorSegmentEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `sponsor_segments` (`id`,`video_id`,`start_ms`,`end_ms`,`category`,`fetched_at`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SponsorSegmentEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.videoId)
        statement.bindLong(3, entity.startMs)
        statement.bindLong(4, entity.endMs)
        statement.bindText(5, entity.category)
        statement.bindLong(6, entity.fetchedAt)
      }
    }
  }

  public override suspend fun insertAll(segments: List<SponsorSegmentEntity>): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSponsorSegmentEntity.insert(_connection, segments)
  }

  public override suspend fun getByVideoId(videoId: String): List<SponsorSegmentEntity> {
    val _sql: String = "SELECT * FROM sponsor_segments WHERE video_id = ? ORDER BY start_ms ASC"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfStartMs: Int = getColumnIndexOrThrow(_stmt, "start_ms")
        val _columnIndexOfEndMs: Int = getColumnIndexOrThrow(_stmt, "end_ms")
        val _columnIndexOfCategory: Int = getColumnIndexOrThrow(_stmt, "category")
        val _columnIndexOfFetchedAt: Int = getColumnIndexOrThrow(_stmt, "fetched_at")
        val _result: MutableList<SponsorSegmentEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SponsorSegmentEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpStartMs: Long
          _tmpStartMs = _stmt.getLong(_columnIndexOfStartMs)
          val _tmpEndMs: Long
          _tmpEndMs = _stmt.getLong(_columnIndexOfEndMs)
          val _tmpCategory: String
          _tmpCategory = _stmt.getText(_columnIndexOfCategory)
          val _tmpFetchedAt: Long
          _tmpFetchedAt = _stmt.getLong(_columnIndexOfFetchedAt)
          _item =
              SponsorSegmentEntity(_tmpId,_tmpVideoId,_tmpStartMs,_tmpEndMs,_tmpCategory,_tmpFetchedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun hasFreshData(videoId: String, freshnessCutoff: Long): Boolean {
    val _sql: String =
        "SELECT EXISTS(SELECT 1 FROM sponsor_segments WHERE video_id = ? AND fetched_at > ?)"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        _argIndex = 2
        _stmt.bindLong(_argIndex, freshnessCutoff)
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

  public override suspend fun deleteByVideoId(videoId: String) {
    val _sql: String = "DELETE FROM sponsor_segments WHERE video_id = ?"
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

  public override suspend fun deleteOlderThan(cutoffMs: Long) {
    val _sql: String = "DELETE FROM sponsor_segments WHERE fetched_at < ?"
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
