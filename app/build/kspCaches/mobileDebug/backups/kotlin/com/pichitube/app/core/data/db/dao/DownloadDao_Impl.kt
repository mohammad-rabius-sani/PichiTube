package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.PichiTubeTypeConverters
import com.pichitube.app.core.`data`.db.entity.DownloadEntity
import com.pichitube.app.core.`data`.db.entity.DownloadStatus
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
public class DownloadDao_Impl(
  __db: RoomDatabase,
) : DownloadDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfDownloadEntity: EntityInsertAdapter<DownloadEntity>

  private val __pichiTubeTypeConverters: PichiTubeTypeConverters = PichiTubeTypeConverters()

  private val __updateAdapterOfDownloadEntity: EntityDeleteOrUpdateAdapter<DownloadEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfDownloadEntity = object : EntityInsertAdapter<DownloadEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `downloads` (`id`,`video_id`,`title`,`thumbnail_url`,`channel_name`,`file_path`,`audio_path`,`quality`,`status`,`progress_percent`,`size_bytes`,`duration_seconds`,`is_audio_only`,`work_id`,`enqueued_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: DownloadEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.videoId)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.thumbnailUrl)
        statement.bindText(5, entity.channelName)
        statement.bindText(6, entity.filePath)
        statement.bindText(7, entity.audioPath)
        statement.bindText(8, entity.quality)
        val _tmp: String = __pichiTubeTypeConverters.fromDownloadStatus(entity.status)
        statement.bindText(9, _tmp)
        statement.bindLong(10, entity.progressPercent.toLong())
        statement.bindLong(11, entity.sizeBytes)
        statement.bindLong(12, entity.durationSeconds)
        val _tmp_1: Int = if (entity.isAudioOnly) 1 else 0
        statement.bindLong(13, _tmp_1.toLong())
        statement.bindText(14, entity.workId)
        statement.bindLong(15, entity.enqueuedAt)
      }
    }
    this.__updateAdapterOfDownloadEntity = object : EntityDeleteOrUpdateAdapter<DownloadEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `downloads` SET `id` = ?,`video_id` = ?,`title` = ?,`thumbnail_url` = ?,`channel_name` = ?,`file_path` = ?,`audio_path` = ?,`quality` = ?,`status` = ?,`progress_percent` = ?,`size_bytes` = ?,`duration_seconds` = ?,`is_audio_only` = ?,`work_id` = ?,`enqueued_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: DownloadEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.videoId)
        statement.bindText(3, entity.title)
        statement.bindText(4, entity.thumbnailUrl)
        statement.bindText(5, entity.channelName)
        statement.bindText(6, entity.filePath)
        statement.bindText(7, entity.audioPath)
        statement.bindText(8, entity.quality)
        val _tmp: String = __pichiTubeTypeConverters.fromDownloadStatus(entity.status)
        statement.bindText(9, _tmp)
        statement.bindLong(10, entity.progressPercent.toLong())
        statement.bindLong(11, entity.sizeBytes)
        statement.bindLong(12, entity.durationSeconds)
        val _tmp_1: Int = if (entity.isAudioOnly) 1 else 0
        statement.bindLong(13, _tmp_1.toLong())
        statement.bindText(14, entity.workId)
        statement.bindLong(15, entity.enqueuedAt)
        statement.bindLong(16, entity.id)
      }
    }
  }

  public override suspend fun insert(download: DownloadEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfDownloadEntity.insertAndReturnId(_connection, download)
    _result
  }

  public override suspend fun update(download: DownloadEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfDownloadEntity.handle(_connection, download)
  }

  public override fun getAll(): Flow<List<DownloadEntity>> {
    val _sql: String = "SELECT * FROM downloads ORDER BY enqueued_at DESC"
    return createFlow(__db, false, arrayOf("downloads")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "file_path")
        val _columnIndexOfAudioPath: Int = getColumnIndexOrThrow(_stmt, "audio_path")
        val _columnIndexOfQuality: Int = getColumnIndexOrThrow(_stmt, "quality")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfProgressPercent: Int = getColumnIndexOrThrow(_stmt, "progress_percent")
        val _columnIndexOfSizeBytes: Int = getColumnIndexOrThrow(_stmt, "size_bytes")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfIsAudioOnly: Int = getColumnIndexOrThrow(_stmt, "is_audio_only")
        val _columnIndexOfWorkId: Int = getColumnIndexOrThrow(_stmt, "work_id")
        val _columnIndexOfEnqueuedAt: Int = getColumnIndexOrThrow(_stmt, "enqueued_at")
        val _result: MutableList<DownloadEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: DownloadEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpAudioPath: String
          _tmpAudioPath = _stmt.getText(_columnIndexOfAudioPath)
          val _tmpQuality: String
          _tmpQuality = _stmt.getText(_columnIndexOfQuality)
          val _tmpStatus: DownloadStatus
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __pichiTubeTypeConverters.toDownloadStatus(_tmp)
          val _tmpProgressPercent: Int
          _tmpProgressPercent = _stmt.getLong(_columnIndexOfProgressPercent).toInt()
          val _tmpSizeBytes: Long
          _tmpSizeBytes = _stmt.getLong(_columnIndexOfSizeBytes)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpIsAudioOnly: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsAudioOnly).toInt()
          _tmpIsAudioOnly = _tmp_1 != 0
          val _tmpWorkId: String
          _tmpWorkId = _stmt.getText(_columnIndexOfWorkId)
          val _tmpEnqueuedAt: Long
          _tmpEnqueuedAt = _stmt.getLong(_columnIndexOfEnqueuedAt)
          _item =
              DownloadEntity(_tmpId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelName,_tmpFilePath,_tmpAudioPath,_tmpQuality,_tmpStatus,_tmpProgressPercent,_tmpSizeBytes,_tmpDurationSeconds,_tmpIsAudioOnly,_tmpWorkId,_tmpEnqueuedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getByVideoId(videoId: String): DownloadEntity? {
    val _sql: String = "SELECT * FROM downloads WHERE video_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, videoId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "file_path")
        val _columnIndexOfAudioPath: Int = getColumnIndexOrThrow(_stmt, "audio_path")
        val _columnIndexOfQuality: Int = getColumnIndexOrThrow(_stmt, "quality")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfProgressPercent: Int = getColumnIndexOrThrow(_stmt, "progress_percent")
        val _columnIndexOfSizeBytes: Int = getColumnIndexOrThrow(_stmt, "size_bytes")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfIsAudioOnly: Int = getColumnIndexOrThrow(_stmt, "is_audio_only")
        val _columnIndexOfWorkId: Int = getColumnIndexOrThrow(_stmt, "work_id")
        val _columnIndexOfEnqueuedAt: Int = getColumnIndexOrThrow(_stmt, "enqueued_at")
        val _result: DownloadEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpAudioPath: String
          _tmpAudioPath = _stmt.getText(_columnIndexOfAudioPath)
          val _tmpQuality: String
          _tmpQuality = _stmt.getText(_columnIndexOfQuality)
          val _tmpStatus: DownloadStatus
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __pichiTubeTypeConverters.toDownloadStatus(_tmp)
          val _tmpProgressPercent: Int
          _tmpProgressPercent = _stmt.getLong(_columnIndexOfProgressPercent).toInt()
          val _tmpSizeBytes: Long
          _tmpSizeBytes = _stmt.getLong(_columnIndexOfSizeBytes)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpIsAudioOnly: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsAudioOnly).toInt()
          _tmpIsAudioOnly = _tmp_1 != 0
          val _tmpWorkId: String
          _tmpWorkId = _stmt.getText(_columnIndexOfWorkId)
          val _tmpEnqueuedAt: Long
          _tmpEnqueuedAt = _stmt.getLong(_columnIndexOfEnqueuedAt)
          _result =
              DownloadEntity(_tmpId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelName,_tmpFilePath,_tmpAudioPath,_tmpQuality,_tmpStatus,_tmpProgressPercent,_tmpSizeBytes,_tmpDurationSeconds,_tmpIsAudioOnly,_tmpWorkId,_tmpEnqueuedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getCompleted(): Flow<List<DownloadEntity>> {
    val _sql: String = "SELECT * FROM downloads WHERE status = 'DONE'"
    return createFlow(__db, false, arrayOf("downloads")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "file_path")
        val _columnIndexOfAudioPath: Int = getColumnIndexOrThrow(_stmt, "audio_path")
        val _columnIndexOfQuality: Int = getColumnIndexOrThrow(_stmt, "quality")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfProgressPercent: Int = getColumnIndexOrThrow(_stmt, "progress_percent")
        val _columnIndexOfSizeBytes: Int = getColumnIndexOrThrow(_stmt, "size_bytes")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfIsAudioOnly: Int = getColumnIndexOrThrow(_stmt, "is_audio_only")
        val _columnIndexOfWorkId: Int = getColumnIndexOrThrow(_stmt, "work_id")
        val _columnIndexOfEnqueuedAt: Int = getColumnIndexOrThrow(_stmt, "enqueued_at")
        val _result: MutableList<DownloadEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: DownloadEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpAudioPath: String
          _tmpAudioPath = _stmt.getText(_columnIndexOfAudioPath)
          val _tmpQuality: String
          _tmpQuality = _stmt.getText(_columnIndexOfQuality)
          val _tmpStatus: DownloadStatus
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __pichiTubeTypeConverters.toDownloadStatus(_tmp)
          val _tmpProgressPercent: Int
          _tmpProgressPercent = _stmt.getLong(_columnIndexOfProgressPercent).toInt()
          val _tmpSizeBytes: Long
          _tmpSizeBytes = _stmt.getLong(_columnIndexOfSizeBytes)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpIsAudioOnly: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsAudioOnly).toInt()
          _tmpIsAudioOnly = _tmp_1 != 0
          val _tmpWorkId: String
          _tmpWorkId = _stmt.getText(_columnIndexOfWorkId)
          val _tmpEnqueuedAt: Long
          _tmpEnqueuedAt = _stmt.getLong(_columnIndexOfEnqueuedAt)
          _item =
              DownloadEntity(_tmpId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelName,_tmpFilePath,_tmpAudioPath,_tmpQuality,_tmpStatus,_tmpProgressPercent,_tmpSizeBytes,_tmpDurationSeconds,_tmpIsAudioOnly,_tmpWorkId,_tmpEnqueuedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getActive(): Flow<List<DownloadEntity>> {
    val _sql: String = "SELECT * FROM downloads WHERE status IN ('QUEUED', 'DOWNLOADING')"
    return createFlow(__db, false, arrayOf("downloads")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfTitle: Int = getColumnIndexOrThrow(_stmt, "title")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfFilePath: Int = getColumnIndexOrThrow(_stmt, "file_path")
        val _columnIndexOfAudioPath: Int = getColumnIndexOrThrow(_stmt, "audio_path")
        val _columnIndexOfQuality: Int = getColumnIndexOrThrow(_stmt, "quality")
        val _columnIndexOfStatus: Int = getColumnIndexOrThrow(_stmt, "status")
        val _columnIndexOfProgressPercent: Int = getColumnIndexOrThrow(_stmt, "progress_percent")
        val _columnIndexOfSizeBytes: Int = getColumnIndexOrThrow(_stmt, "size_bytes")
        val _columnIndexOfDurationSeconds: Int = getColumnIndexOrThrow(_stmt, "duration_seconds")
        val _columnIndexOfIsAudioOnly: Int = getColumnIndexOrThrow(_stmt, "is_audio_only")
        val _columnIndexOfWorkId: Int = getColumnIndexOrThrow(_stmt, "work_id")
        val _columnIndexOfEnqueuedAt: Int = getColumnIndexOrThrow(_stmt, "enqueued_at")
        val _result: MutableList<DownloadEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: DownloadEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpTitle: String
          _tmpTitle = _stmt.getText(_columnIndexOfTitle)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpFilePath: String
          _tmpFilePath = _stmt.getText(_columnIndexOfFilePath)
          val _tmpAudioPath: String
          _tmpAudioPath = _stmt.getText(_columnIndexOfAudioPath)
          val _tmpQuality: String
          _tmpQuality = _stmt.getText(_columnIndexOfQuality)
          val _tmpStatus: DownloadStatus
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfStatus)
          _tmpStatus = __pichiTubeTypeConverters.toDownloadStatus(_tmp)
          val _tmpProgressPercent: Int
          _tmpProgressPercent = _stmt.getLong(_columnIndexOfProgressPercent).toInt()
          val _tmpSizeBytes: Long
          _tmpSizeBytes = _stmt.getLong(_columnIndexOfSizeBytes)
          val _tmpDurationSeconds: Long
          _tmpDurationSeconds = _stmt.getLong(_columnIndexOfDurationSeconds)
          val _tmpIsAudioOnly: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsAudioOnly).toInt()
          _tmpIsAudioOnly = _tmp_1 != 0
          val _tmpWorkId: String
          _tmpWorkId = _stmt.getText(_columnIndexOfWorkId)
          val _tmpEnqueuedAt: Long
          _tmpEnqueuedAt = _stmt.getLong(_columnIndexOfEnqueuedAt)
          _item =
              DownloadEntity(_tmpId,_tmpVideoId,_tmpTitle,_tmpThumbnailUrl,_tmpChannelName,_tmpFilePath,_tmpAudioPath,_tmpQuality,_tmpStatus,_tmpProgressPercent,_tmpSizeBytes,_tmpDurationSeconds,_tmpIsAudioOnly,_tmpWorkId,_tmpEnqueuedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateStatus(
    videoId: String,
    status: DownloadStatus,
    progress: Int,
  ) {
    val _sql: String = "UPDATE downloads SET status = ?, progress_percent = ? WHERE video_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        val _tmp: String = __pichiTubeTypeConverters.fromDownloadStatus(status)
        _stmt.bindText(_argIndex, _tmp)
        _argIndex = 2
        _stmt.bindLong(_argIndex, progress.toLong())
        _argIndex = 3
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun setCompleted(
    videoId: String,
    path: String,
    audioPath: String,
    status: DownloadStatus,
    sizeBytes: Long,
  ) {
    val _sql: String =
        "UPDATE downloads SET file_path = ?, audio_path = ?, status = ?, size_bytes = ? WHERE video_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, path)
        _argIndex = 2
        _stmt.bindText(_argIndex, audioPath)
        _argIndex = 3
        val _tmp: String = __pichiTubeTypeConverters.fromDownloadStatus(status)
        _stmt.bindText(_argIndex, _tmp)
        _argIndex = 4
        _stmt.bindLong(_argIndex, sizeBytes)
        _argIndex = 5
        _stmt.bindText(_argIndex, videoId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(videoId: String) {
    val _sql: String = "DELETE FROM downloads WHERE video_id = ?"
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

  public companion object {
    public fun getRequiredConverters(): List<KClass<*>> = emptyList()
  }
}
