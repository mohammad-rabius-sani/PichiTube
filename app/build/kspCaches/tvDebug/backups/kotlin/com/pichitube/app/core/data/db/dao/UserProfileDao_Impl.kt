package com.pichitube.app.core.`data`.db.dao

import androidx.room.EntityDeleteOrUpdateAdapter
import androidx.room.EntityInsertAdapter
import androidx.room.RoomDatabase
import androidx.room.coroutines.createFlow
import androidx.room.util.getColumnIndexOrThrow
import androidx.room.util.performSuspending
import androidx.sqlite.SQLiteStatement
import com.pichitube.app.core.`data`.db.PichiTubeTypeConverters
import com.pichitube.app.core.`data`.db.entity.ProfileType
import com.pichitube.app.core.`data`.db.entity.UserProfileEntity
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
public class UserProfileDao_Impl(
  __db: RoomDatabase,
) : UserProfileDao {
  private val __db: RoomDatabase

  private val __insertAdapterOfUserProfileEntity: EntityInsertAdapter<UserProfileEntity>

  private val __pichiTubeTypeConverters: PichiTubeTypeConverters = PichiTubeTypeConverters()

  private val __updateAdapterOfUserProfileEntity: EntityDeleteOrUpdateAdapter<UserProfileEntity>
  init {
    this.__db = __db
    this.__insertAdapterOfUserProfileEntity = object : EntityInsertAdapter<UserProfileEntity>() {
      protected override fun createQuery(): String =
          "INSERT OR REPLACE INTO `user_profiles` (`id`,`name`,`avatar_emoji`,`profile_type`,`is_pin_locked`,`pin_hash`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?)"

      protected override fun bind(statement: SQLiteStatement, entity: UserProfileEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.avatarEmoji)
        val _tmp: String = __pichiTubeTypeConverters.fromProfileType(entity.profileType)
        statement.bindText(4, _tmp)
        val _tmp_1: Int = if (entity.isPinLocked) 1 else 0
        statement.bindLong(5, _tmp_1.toLong())
        statement.bindText(6, entity.pinHash)
        statement.bindLong(7, entity.createdAt)
      }
    }
    this.__updateAdapterOfUserProfileEntity = object :
        EntityDeleteOrUpdateAdapter<UserProfileEntity>() {
      protected override fun createQuery(): String =
          "UPDATE OR ABORT `user_profiles` SET `id` = ?,`name` = ?,`avatar_emoji` = ?,`profile_type` = ?,`is_pin_locked` = ?,`pin_hash` = ?,`created_at` = ? WHERE `id` = ?"

      protected override fun bind(statement: SQLiteStatement, entity: UserProfileEntity) {
        statement.bindLong(1, entity.id)
        statement.bindText(2, entity.name)
        statement.bindText(3, entity.avatarEmoji)
        val _tmp: String = __pichiTubeTypeConverters.fromProfileType(entity.profileType)
        statement.bindText(4, _tmp)
        val _tmp_1: Int = if (entity.isPinLocked) 1 else 0
        statement.bindLong(5, _tmp_1.toLong())
        statement.bindText(6, entity.pinHash)
        statement.bindLong(7, entity.createdAt)
        statement.bindLong(8, entity.id)
      }
    }
  }

  public override suspend fun insert(profile: UserProfileEntity): Long = performSuspending(__db,
      false, true) { _connection ->
    val _result: Long = __insertAdapterOfUserProfileEntity.insertAndReturnId(_connection, profile)
    _result
  }

  public override suspend fun update(profile: UserProfileEntity): Unit = performSuspending(__db,
      false, true) { _connection ->
    __updateAdapterOfUserProfileEntity.handle(_connection, profile)
  }

  public override fun getAll(): Flow<List<UserProfileEntity>> {
    val _sql: String = "SELECT * FROM user_profiles ORDER BY id ASC"
    return createFlow(__db, false, arrayOf("user_profiles")) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfAvatarEmoji: Int = getColumnIndexOrThrow(_stmt, "avatar_emoji")
        val _columnIndexOfProfileType: Int = getColumnIndexOrThrow(_stmt, "profile_type")
        val _columnIndexOfIsPinLocked: Int = getColumnIndexOrThrow(_stmt, "is_pin_locked")
        val _columnIndexOfPinHash: Int = getColumnIndexOrThrow(_stmt, "pin_hash")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _result: MutableList<UserProfileEntity> = mutableListOf()
        while (_stmt.step()) {
          val _item: UserProfileEntity
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpAvatarEmoji: String
          _tmpAvatarEmoji = _stmt.getText(_columnIndexOfAvatarEmoji)
          val _tmpProfileType: ProfileType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfProfileType)
          _tmpProfileType = __pichiTubeTypeConverters.toProfileType(_tmp)
          val _tmpIsPinLocked: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsPinLocked).toInt()
          _tmpIsPinLocked = _tmp_1 != 0
          val _tmpPinHash: String
          _tmpPinHash = _stmt.getText(_columnIndexOfPinHash)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _item =
              UserProfileEntity(_tmpId,_tmpName,_tmpAvatarEmoji,_tmpProfileType,_tmpIsPinLocked,_tmpPinHash,_tmpCreatedAt)
          _result.add(_item)
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun getById(id: Long): UserProfileEntity? {
    val _sql: String = "SELECT * FROM user_profiles WHERE id = ? LIMIT 1"
    return performSuspending(__db, true, false) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
        val _columnIndexOfId: Int = getColumnIndexOrThrow(_stmt, "id")
        val _columnIndexOfName: Int = getColumnIndexOrThrow(_stmt, "name")
        val _columnIndexOfAvatarEmoji: Int = getColumnIndexOrThrow(_stmt, "avatar_emoji")
        val _columnIndexOfProfileType: Int = getColumnIndexOrThrow(_stmt, "profile_type")
        val _columnIndexOfIsPinLocked: Int = getColumnIndexOrThrow(_stmt, "is_pin_locked")
        val _columnIndexOfPinHash: Int = getColumnIndexOrThrow(_stmt, "pin_hash")
        val _columnIndexOfCreatedAt: Int = getColumnIndexOrThrow(_stmt, "created_at")
        val _result: UserProfileEntity?
        if (_stmt.step()) {
          val _tmpId: Long
          _tmpId = _stmt.getLong(_columnIndexOfId)
          val _tmpName: String
          _tmpName = _stmt.getText(_columnIndexOfName)
          val _tmpAvatarEmoji: String
          _tmpAvatarEmoji = _stmt.getText(_columnIndexOfAvatarEmoji)
          val _tmpProfileType: ProfileType
          val _tmp: String
          _tmp = _stmt.getText(_columnIndexOfProfileType)
          _tmpProfileType = __pichiTubeTypeConverters.toProfileType(_tmp)
          val _tmpIsPinLocked: Boolean
          val _tmp_1: Int
          _tmp_1 = _stmt.getLong(_columnIndexOfIsPinLocked).toInt()
          _tmpIsPinLocked = _tmp_1 != 0
          val _tmpPinHash: String
          _tmpPinHash = _stmt.getText(_columnIndexOfPinHash)
          val _tmpCreatedAt: Long
          _tmpCreatedAt = _stmt.getLong(_columnIndexOfCreatedAt)
          _result =
              UserProfileEntity(_tmpId,_tmpName,_tmpAvatarEmoji,_tmpProfileType,_tmpIsPinLocked,_tmpPinHash,_tmpCreatedAt)
        } else {
          _result = null
        }
        _result
      } finally {
        _stmt.close()
      }
    }
  }

  public override suspend fun delete(id: Long) {
    val _sql: String = "DELETE FROM user_profiles WHERE id = ?"
    return performSuspending(__db, false, true) { _connection ->
      val _stmt: SQLiteStatement = _connection.prepare(_sql)
      try {
        var _argIndex: Int = 1
        _stmt.bindLong(_argIndex, id)
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
