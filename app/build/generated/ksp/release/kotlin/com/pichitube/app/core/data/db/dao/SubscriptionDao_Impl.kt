package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.entity.SubscriptionEntity
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
public class SubscriptionDao_Impl(
  __db: RoomDatabase,
) : SubscriptionDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfSubscriptionEntity: EntityInsertAdapter<SubscriptionEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfSubscriptionEntity = object : EntityInsertAdapter<SubscriptionEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `subscriptions` (`channelId`,`channelName`,`channelAvatarUrl`,`subscriberCount`,`subscribedAt`) VALUES (?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: SubscriptionEntity) {
        statement.bindText(1, entity.channelId)
        statement.bindText(2, entity.channelName)
        statement.bindText(3, entity.channelAvatarUrl)
        statement.bindLong(4, entity.subscriberCount)
        statement.bindLong(5, entity.subscribedAt)
      }
    }
  }

  public override suspend fun insert(entity: SubscriptionEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __insertAdapterOfSubscriptionEntity.insert(_connection, entity)
  }

  public override fun getAll(): Flow<List<SubscriptionEntity>> {
    val _sql: String = "SELECT * FROM subscriptions ORDER BY channelName ASC"
    return createFlow(__db, false, arrayOf("subscriptions")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfChannelId: Int = getColumnIndexOrThrow(_stmt, "channelId")
        val _columnIndexOfChannelName: Int = getColumnIndexOrThrow(_stmt, "channelName")
        val _columnIndexOfChannelAvatarUrl: Int = getColumnIndexOrThrow(_stmt, "channelAvatarUrl")
        val _columnIndexOfSubscriberCount: Int = getColumnIndexOrThrow(_stmt, "subscriberCount")
        val _columnIndexOfSubscribedAt: Int = getColumnIndexOrThrow(_stmt, "subscribedAt")
        val _result: MutableList<SubscriptionEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: SubscriptionEntity
          val _tmpChannelId: String
          _tmpChannelId = _stmt.getText(_columnIndexOfChannelId)
          val _tmpChannelName: String
          _tmpChannelName = _stmt.getText(_columnIndexOfChannelName)
          val _tmpChannelAvatarUrl: String
          _tmpChannelAvatarUrl = _stmt.getText(_columnIndexOfChannelAvatarUrl)
          val _tmpSubscriberCount: Long
          _tmpSubscriberCount = _stmt.getLong(_columnIndexOfSubscriberCount)
          val _tmpSubscribedAt: Long
          _tmpSubscribedAt = _stmt.getLong(_columnIndexOfSubscribedAt)
          _item =
              SubscriptionEntity(_tmpChannelId,_tmpChannelName,_tmpChannelAvatarUrl,_tmpSubscriberCount,_tmpSubscribedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun isSubscribed(channelId: String): Int {
    val _sql: String = "SELECT COUNT(*) FROM subscriptions WHERE channelId = ?"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindText(_argIndex, channelId)
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

  public override suspend fun deleteById(channelId: String) {
    val _sql: String = "DELETE FROM subscriptions WHERE channelId = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
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
