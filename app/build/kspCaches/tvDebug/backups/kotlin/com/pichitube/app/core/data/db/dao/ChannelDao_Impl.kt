package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.appendPlaceholders
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.ChannelEntity
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
import kotlin.text.StringBuilder
import kotlinx.coroutines.flow.Flow

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class ChannelDao_Impl(
  __db: RoomDatabase,
) : ChannelDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfChannelEntity: EntityInsertAdapter<ChannelEntity>

  private val __updateAdapterOfChannelEntity: EntityDeleteOrUpdateAdapter<ChannelEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfChannelEntity = object : EntityInsertAdapter<ChannelEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `channels` (`id`,`channel_id`,`name`,`avatar_url`,`banner_url`,`subscriber_count`,`video_count`,`description`,`folder_tag`,`cached_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: ChannelEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.channelId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.avatarUrl)
        statement.bindText(5, entity.bannerUrl)
        statement.bindLong(6, entity.subscriberCount)
        statement.bindLong(7, entity.videoCount)
        statement.bindText(8, entity.description)
        statement.bindText(9, entity.folderTag)
        statement.bindLong(10, entity.cachedAt)
      }
    }
    this.__updateAdapterOfChannelEntity = object : EntityDeleteOrUpdateAdapter<ChannelEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `channels` SET `id` = ?,`channel_id` = ?,`name` = ?,`avatar_url` = ?,`banner_url` = ?,`subscriber_count` = ?,`video_count` = ?,`description` = ?,`folder_tag` = ?,`cached_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: ChannelEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.channelId)
        statement.bindText(3, entity.name)
        statement.bindText(4, entity.avatarUrl)
        statement.bindText(5, entity.bannerUrl)
        statement.bindLong(6, entity.subscriberCount)
        statement.bindLong(7, entity.videoCount)
        statement.bindText(8, entity.description)
        statement.bindText(9, entity.folderTag)
        statement.bindLong(10, entity.cachedAt)
        statement.bindLong(11, entity.id)
      }
    }
  }

  public override suspend fun insert(channel: ChannelEntity): Long = performSuspending(__db, false,
      true) { _connection ->
    val _result: Long = __insertAdapterOfChannelEntity.insertAndReturnId(_connection, channel)
    _result
  }

  public override suspend fun update(channel: ChannelEntity): Unit = performSuspending(__db, false,
      true) { _connection ->
    __updateAdapterOfChannelEntity.handle(_connection, channel)
  }

  public override suspend fun getById(channelId: String): ChannelEntity? {
    val _sql: String = "SELECT * FROM channels WHERE channel_id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, channelId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channel_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "avatar_url")
        val _columnIndexOfBannerUrl: Int = getColumnIndexOrThrow(_stmt, "banner_url")
        val _columnIndexOfSubscriberCount: Int = getColumnIndexOrThrow(_stmt, "subscriber_count")
        val _columnIndexOfVideoCount: Int = getColumnIndexOrThrow(_stmt, "video_count")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfFolderTag: Int = getColumnIndexOrThrow(_stmt, "folder_tag")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cached_at")
        val _result: ChannelEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpAvatarUrl: String
          _tmpAvatarUrl = _stmt.getText(_columnIndexOfAvatarUrl)
          val _tmpBannerUrl: String
          _tmpBannerUrl = _stmt.getText(_columnIndexOfBannerUrl)
          val _tmpSubscriberCount: Long
          _tmpSubscriberCount = _stmt.getLong(_columnIndexOfSubscriberCount)
          val _tmpVideoCount: Long
          _tmpVideoCount = _stmt.getLong(_columnIndexOfVideoCount)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpFolderTag: String
          _tmpFolderTag = _stmt.getText(_columnIndexOfFolderTag)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _result =
              ChannelEntity(_tmpId,_tmpChannelId,_tmpName,_tmpAvatarUrl,_tmpBannerUrl,_tmpSubscriberCount,_tmpVideoCount,_tmpDescription,_tmpFolderTag,_tmpCachedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByIds(channelIds: List<String>): Flow<List<ChannelEntity>> {
    val _stringBuilder: StringBuilder = StringBuilder()
    _stringBuilder.append("SELECT * FROM channels WHERE channel_id IN (")
    val _inputSize: Int = channelIds.size
    appendPlaceholders(_stringBuilder, _inputSize)
    _stringBuilder.append(")")
    val _sql: String = _stringBuilder.toString()
    return createFlow(__db, false, arrayOf("channels")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        for (_item: String in channelIds) {
          _stmt.bindText(_argIndex, _item)
          _argIndex++
        }
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channel_id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "avatar_url")
        val _columnIndexOfBannerUrl: Int = getColumnIndexOrThrow(_stmt, "banner_url")
        val _columnIndexOfSubscriberCount: Int = getColumnIndexOrThrow(_stmt, "subscriber_count")
        val _columnIndexOfVideoCount: Int = getColumnIndexOrThrow(_stmt, "video_count")
        val _columnIndexOfDescription: Int = getColumnIndexOrThrow(_stmt, "description")
        val _columnIndexOfFolderTag: Int = getColumnIndexOrThrow(_stmt, "folder_tag")
        val _columnIndexOfCachedAt: Int = getColumnIndexOrThrow(_stmt, "cached_at")
        val _result: MutableList<ChannelEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item_1: ChannelEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpAvatarUrl: String
          _tmpAvatarUrl = _stmt.getText(_columnIndexOfAvatarUrl)
          val _tmpBannerUrl: String
          _tmpBannerUrl = _stmt.getText(_columnIndexOfBannerUrl)
          val _tmpSubscriberCount: Long
          _tmpSubscriberCount = _stmt.getLong(_columnIndexOfSubscriberCount)
          val _tmpVideoCount: Long
          _tmpVideoCount = _stmt.getLong(_columnIndexOfVideoCount)
          val _tmpDescription: String
          _tmpDescription = _stmt.getText(_columnIndexOfDescription)
          val _tmpFolderTag: String
          _tmpFolderTag = _stmt.getText(_columnIndexOfFolderTag)
          val _tmpCachedAt: Long
          _tmpCachedAt = _stmt.getLong(_columnIndexOfCachedAt)
          _item_1 =
              ChannelEntity(_tmpId,_tmpChannelId,_tmpName,_tmpAvatarUrl,_tmpBannerUrl,_tmpSubscriberCount,_tmpVideoCount,_tmpDescription,_tmpFolderTag,_tmpCachedAt)
          _result.add(_item_1)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun updateFolder(channelId: String, folderTag: String) {
    val _sql: String = "UPDATE channels SET folder_tag = ? WHERE channel_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, folderTag)
        _argIndex = 2
        _stmt.bindText(_argIndex, channelId)
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
