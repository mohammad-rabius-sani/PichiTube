package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.SubscriptionEntity
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
public class SubscriptionDao_Impl(
  __db: RoomDatabase,
) : SubscriptionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSubscriptionEntity: EntityInsertAdapter<SubscriptionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSubscriptionEntity = object : EntityInsertAdapter<SubscriptionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `subscriptions` (`id`,`user_id`,`channel_id`,`channel_name`,`avatar_url`,`subscriber_count`,`folder_name`,`subscribed_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SubscriptionEntity) {
        statement.bindLong(1, entity.id)
        statement.bindLong(2, entity.userId)
        statement.bindText(3, entity.channelId)
        statement.bindText(4, entity.channelName)
        statement.bindText(5, entity.avatarUrl)
        statement.bindLong(6, entity.subscriberCount)
        statement.bindText(7, entity.folderName)
        statement.bindLong(8, entity.subscribedAt)
      }
    }
  }

  public override suspend fun insert(subscription: SubscriptionEntity): Unit =
      performSuspending(__db, false, true) { _connection ->
    __insertAdapterOfSubscriptionEntity.insert(_connection, subscription)
  }

  public override fun getByUser(userId: Long): Flow<List<SubscriptionEntity>> {
    val _sql: String = "SELECT * FROM subscriptions WHERE user_id = ? ORDER BY subscribed_at DESC"
    return createFlow(__db, false, arrayOf("subscriptions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "user_id")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channel_id")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "avatar_url")
        val _columnIndexOfSubscriberCount: Int = getColumnIndexOrThrow(_stmt, "subscriber_count")
        val _columnIndexOfFolderName: Int = getColumnIndexOrThrow(_stmt, "folder_name")
        val _columnIndexOfSubscribedAt: Int = getColumnIndexOrThrow(_stmt, "subscribed_at")
        val _result: MutableList<SubscriptionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SubscriptionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpAvatarUrl: String
          _tmpAvatarUrl = _stmt.getText(_columnIndexOfAvatarUrl)
          val _tmpSubscriberCount: Long
          _tmpSubscriberCount = _stmt.getLong(_columnIndexOfSubscriberCount)
          val _tmpFolderName: String
          _tmpFolderName = _stmt.getText(_columnIndexOfFolderName)
          val _tmpSubscribedAt: Long
          _tmpSubscribedAt = _stmt.getLong(_columnIndexOfSubscribedAt)
          _item =
              SubscriptionEntity(_tmpId,_tmpUserId,_tmpChannelId,_tmpChannelName,_tmpAvatarUrl,_tmpSubscriberCount,_tmpFolderName,_tmpSubscribedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getChannelIds(userId: Long): List<String> {
    val _sql: String = "SELECT channel_id FROM subscriptions WHERE user_id = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun isSubscribed(userId: Long, channelId: String): Flow<Boolean> {
    val _sql: String =
        "SELECT EXISTS(SELECT 1 FROM subscriptions WHERE user_id = ? AND channel_id = ?)"
    return createFlow(__db, false, arrayOf("subscriptions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindText(_argIndex, channelId)
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

  public override fun getFolders(userId: Long): Flow<List<String>> {
    val _sql: String = "SELECT DISTINCT folder_name FROM subscriptions WHERE user_id = ?"
    return createFlow(__db, false, arrayOf("subscriptions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        val _result: MutableList<String> = mutableListOf()
        while (_stmt.step()) {
          val _item: String
          _item = _stmt.getText(0)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override fun getByFolder(userId: Long, folder: String): Flow<List<SubscriptionEntity>> {
    val _sql: String = "SELECT * FROM subscriptions WHERE user_id = ? AND folder_name = ?"
    return createFlow(__db, false, arrayOf("subscriptions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindText(_argIndex, folder)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfUserId: Int = getColumnIndexOrThrow(_stmt, "user_id")
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channel_id")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channel_name")
        val _columnIndexOfAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "avatar_url")
        val _columnIndexOfSubscriberCount: Int = getColumnIndexOrThrow(_stmt, "subscriber_count")
        val _columnIndexOfFolderName: Int = getColumnIndexOrThrow(_stmt, "folder_name")
        val _columnIndexOfSubscribedAt: Int = getColumnIndexOrThrow(_stmt, "subscribed_at")
        val _result: MutableList<SubscriptionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SubscriptionEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpUserId: Long
          _tmpUserId = _stmt.getLong(_columnIndexOfUserId)
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpAvatarUrl: String
          _tmpAvatarUrl = _stmt.getText(_columnIndexOfAvatarUrl)
          val _tmpSubscriberCount: Long
          _tmpSubscriberCount = _stmt.getLong(_columnIndexOfSubscriberCount)
          val _tmpFolderName: String
          _tmpFolderName = _stmt.getText(_columnIndexOfFolderName)
          val _tmpSubscribedAt: Long
          _tmpSubscribedAt = _stmt.getLong(_columnIndexOfSubscribedAt)
          _item =
              SubscriptionEntity(_tmpId,_tmpUserId,_tmpChannelId,_tmpChannelName,_tmpAvatarUrl,_tmpSubscriberCount,_tmpFolderName,_tmpSubscribedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(userId: Long, channelId: String) {
    val _sql: String = "DELETE FROM subscriptions WHERE user_id = ? AND channel_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 2
        _stmt.bindText(_argIndex, channelId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun moveToFolder(
    userId: Long,
    channelId: String,
    newFolder: String,
  ) {
    val _sql: String =
        "UPDATE subscriptions SET folder_name = ? WHERE user_id = ? AND channel_id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, newFolder)
        _argIndex = 2
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 3
        _stmt.bindText(_argIndex, channelId)
        _stmt.step()
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun renameFolder(
    userId: Long,
    oldFolder: String,
    newFolder: String,
  ) {
    val _sql: String =
        "UPDATE subscriptions SET folder_name = ? WHERE user_id = ? AND folder_name = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, newFolder)
        _argIndex = 2
        _stmt.bindLong(_argIndex, userId)
        _argIndex = 3
        _stmt.bindText(_argIndex, oldFolder)
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
