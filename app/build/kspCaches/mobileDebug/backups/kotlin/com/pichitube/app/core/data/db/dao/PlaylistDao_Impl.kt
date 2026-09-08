package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.PlaylistEntity
import com.pichitube.app.core.`data`.db.entity.PlaylistVideoEntity
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
public class PlaylistDao_Impl(
  __db: RoomDatabase,
) : PlaylistDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfPlaylistEntity: EntityInsertAdapter<PlaylistEntity>

  private val __insertAdapterOfPlaylistVideoEntity: EntityInsertAdapter<PlaylistVideoEntity>

  private val __updateAdapterOfPlaylistEntity: EntityDeleteOrUpdateAdapter<PlaylistEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfPlaylistEntity = object : EntityInsertAdapter<PlaylistEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `playlists` (`id`,`user_id`,`name`,`description`,`thumbnail_url`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PlaylistEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.thumbnailUrl)
        statement.bindLong(6, entity.createdAt)
      }
    }
    this.__insertAdapterOfPlaylistVideoEntity = object : EntityInsertAdapter<PlaylistVideoEntity>()
        {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `playlist_videos` (`id`,`playlist_id`,`video_id`,`position`,`added_at`) VALUES (nullif(?, 0),?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: PlaylistVideoEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.playlistId)
        statement.bindText(3, entity.videoId)
        statement.bindLong(4, entity.position.toLong())
        statement.bindLong(5, entity.addedAt)
      }
    }
    this.__updateAdapterOfPlaylistEntity = object : EntityDeleteOrUpdateAdapter<PlaylistEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `playlists` SET `id` = ?,`user_id` = ?,`name` = ?,`description` = ?,`thumbnail_url` = ?,`created_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: PlaylistEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.description)
        statement.bindText(5, entity.thumbnailUrl)
        statement.bindLong(6, entity.createdAt)
        statement.bindLong(7, entity.id)
      }
    }
  }

  public override suspend fun insertPlaylist(playlist: PlaylistEntity): Long =
      performSuspending(__db, false, true) { _connection ->
    val _result: Long = __insertAdapterOfPlaylistEntity.insertAndReturnId(_connection, playlist)
    _result
  }

  public override suspend fun insertPlaylistVideo(item: PlaylistVideoEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfPlaylistVideoEntity.insert(_connection, item)
  }

  public override suspend fun updatePlaylist(playlist: PlaylistEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __updateAdapterOfPlaylistEntity.handle(_connection, playlist)
  }

  public override fun getByUser(userId: Long): Flow<List<PlaylistEntity>> {
    val _sql: String = "SELECT * FROM playlists WHERE user_id = ? ORDER BY created_at DESC"
    return createFlow(__db, false, arrayOf("playlists")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "user_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfThumbnailUrl: Int = getColumnIndexOrThrow(_stmt, "thumbnail_url")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _result: MutableList<PlaylistEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaylistEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpThumbnailUrl: String
          _tmpThumbnailUrl = _stmt.getText(_columnIndexOfThumbnailUrl)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              PlaylistEntity(_tmpId,_tmpUserId,_tmpName,_tmpDescription,_tmpThumbnailUrl,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getVideos(playlistId: Long): Flow<List<PlaylistVideoEntity>> {
    val _sql: String = "SELECT * FROM playlist_videos WHERE playlist_id = ? ORDER BY position ASC"
    return createFlow(__db, false, arrayOf("playlist_videos")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfPlaylistId: Int = getColumnIndexOrThrow(_stmt, "playlist_id")
        val _columnIndexOfVideoId: Int = getColumnIndexOrThrow(_stmt, "video_id")
        val _columnIndexOfPosition: Int = getColumnIndexOrThrow(_stmt, "position")
        val _columnIndexOfAddedAt: Int = getColumnIndexOrThrow(_stmt, "added_at")
        val _result: MutableList<PlaylistVideoEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: PlaylistVideoEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpPlaylistId: Long
          _tmpPlaylistId = _stmt.getLong(_columnIndexOfPlaylistId)
          val _tmpVideoId: String
          _tmpVideoId = _stmt.getText(_columnIndexOfVideoId)
          val _tmpPosition: Int
          _tmpPosition = _stmt.getLong(_columnIndexOfPosition).toInt()
          val _tmpAddedAt: Long
          _tmpAddedAt = _stmt.getLong(_columnIndexOfAddedAt)
          _item = PlaylistVideoEntity(_tmpId,_tmpPlaylistId,_tmpVideoId,_tmpPosition,_tmpAddedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun deletePlaylist(playlistId: Long) {
    val _sql: String = "DELETE FROM playlists WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun removeFromPlaylist(playlistId: Long, videoId: String) {
    val _sql: String = "DELETE FROM playlist_videos WHERE playlist_id = ? AND video_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, playlistId)
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
