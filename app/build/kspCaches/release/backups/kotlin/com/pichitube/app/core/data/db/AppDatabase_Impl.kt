package com.pichitube.app.core.`data`.db

import androidx.room.InvalidationTracker
import androidx.room.RoomOpenDelegate
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.room.util.TableInfo
import androidx.room.util.TableInfo.Companion.read
import androidx.room.util.dropFtsSyncTriggers
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL
import com.pichitube.app.core.`data`.db.dao.HistoryDao
import com.pichitube.app.core.`data`.db.dao.HistoryDao_Impl
import com.pichitube.app.core.`data`.db.dao.SubscriptionDao
import com.pichitube.app.core.`data`.db.dao.SubscriptionDao_Impl
import com.pichitube.app.core.`data`.db.dao.WatchLaterDao
import com.pichitube.app.core.`data`.db.dao.WatchLaterDao_Impl
import javax.`annotation`.processing.Generated
import kotlin.Lazy
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List
import kotlin.collections.Map
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.MutableSet
import kotlin.collections.Set
import kotlin.collections.mutableListOf
import kotlin.collections.mutableMapOf
import kotlin.collections.mutableSetOf
import kotlin.reflect.KClass

@Generated(value = ["androidx.room.RoomProcessor"])
@Suppress(names = ["UNCHECKED_CAST", "DEPRECATION", "REDUNDANT_PROJECTION", "REMOVAL"])
public class AppDatabase_Impl : AppDatabase() {
  private val _historyDao: Lazy<HistoryDao> = lazy {
    HistoryDao_Impl(this)
  }

  private val _watchLaterDao: Lazy<WatchLaterDao> = lazy {
    WatchLaterDao_Impl(this)
  }

  private val _subscriptionDao: Lazy<SubscriptionDao> = lazy {
    SubscriptionDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(1,
        "ab2cdc03f2dbc26c1cdda1c950fc2ddf", "384bc66276b185f9145484c4ecdaaf87") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `history` (`videoId` TEXT NOT NULL, `title` TEXT NOT NULL, `channelName` TEXT NOT NULL, `channelId` TEXT NOT NULL, `thumbnailUrl` TEXT NOT NULL, `durationSeconds` INTEGER NOT NULL, `positionMs` INTEGER NOT NULL, `viewCount` INTEGER NOT NULL, `watchedAt` INTEGER NOT NULL, PRIMARY KEY(`videoId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `watch_later` (`videoId` TEXT NOT NULL, `title` TEXT NOT NULL, `channelName` TEXT NOT NULL, `channelId` TEXT NOT NULL, `thumbnailUrl` TEXT NOT NULL, `durationSeconds` INTEGER NOT NULL, `viewCount` INTEGER NOT NULL, `addedAt` INTEGER NOT NULL, PRIMARY KEY(`videoId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `subscriptions` (`channelId` TEXT NOT NULL, `channelName` TEXT NOT NULL, `channelAvatarUrl` TEXT NOT NULL, `subscriberCount` INTEGER NOT NULL, `subscribedAt` INTEGER NOT NULL, PRIMARY KEY(`channelId`))")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'ab2cdc03f2dbc26c1cdda1c950fc2ddf')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `history`")
        connection.execSQL("DROP TABLE IF EXISTS `watch_later`")
        connection.execSQL("DROP TABLE IF EXISTS `subscriptions`")
      }

      public override fun onCreate(connection: SQLiteConnection) {
      }

      public override fun onOpen(connection: SQLiteConnection) {
        internalInitInvalidationTracker(connection)
      }

      public override fun onPreMigrate(connection: SQLiteConnection) {
        dropFtsSyncTriggers(connection)
      }

      public override fun onPostMigrate(connection: SQLiteConnection) {
      }

      public override fun onValidateSchema(connection: SQLiteConnection):
          RoomOpenDelegate.ValidationResult {
        val _columnsHistory: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsHistory.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("channelName", TableInfo.Column("channelName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("channelId", TableInfo.Column("channelId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("thumbnailUrl", TableInfo.Column("thumbnailUrl", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("durationSeconds", TableInfo.Column("durationSeconds", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("positionMs", TableInfo.Column("positionMs", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("viewCount", TableInfo.Column("viewCount", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("watchedAt", TableInfo.Column("watchedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysHistory: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesHistory: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoHistory: TableInfo = TableInfo("history", _columnsHistory, _foreignKeysHistory,
            _indicesHistory)
        val _existingHistory: TableInfo = read(connection, "history")
        if (!_infoHistory.equals(_existingHistory)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |history(com.pichitube.app.core.data.db.entity.HistoryEntity).
              | Expected:
              |""".trimMargin() + _infoHistory + """
              |
              | Found:
              |""".trimMargin() + _existingHistory)
        }
        val _columnsWatchLater: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsWatchLater.put("videoId", TableInfo.Column("videoId", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchLater.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchLater.put("channelName", TableInfo.Column("channelName", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchLater.put("channelId", TableInfo.Column("channelId", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchLater.put("thumbnailUrl", TableInfo.Column("thumbnailUrl", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchLater.put("durationSeconds", TableInfo.Column("durationSeconds", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchLater.put("viewCount", TableInfo.Column("viewCount", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsWatchLater.put("addedAt", TableInfo.Column("addedAt", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysWatchLater: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesWatchLater: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoWatchLater: TableInfo = TableInfo("watch_later", _columnsWatchLater,
            _foreignKeysWatchLater, _indicesWatchLater)
        val _existingWatchLater: TableInfo = read(connection, "watch_later")
        if (!_infoWatchLater.equals(_existingWatchLater)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |watch_later(com.pichitube.app.core.data.db.entity.WatchLaterEntity).
              | Expected:
              |""".trimMargin() + _infoWatchLater + """
              |
              | Found:
              |""".trimMargin() + _existingWatchLater)
        }
        val _columnsSubscriptions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSubscriptions.put("channelId", TableInfo.Column("channelId", "TEXT", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("channelName", TableInfo.Column("channelName", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("channelAvatarUrl", TableInfo.Column("channelAvatarUrl", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("subscriberCount", TableInfo.Column("subscriberCount", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("subscribedAt", TableInfo.Column("subscribedAt", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSubscriptions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSubscriptions: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoSubscriptions: TableInfo = TableInfo("subscriptions", _columnsSubscriptions,
            _foreignKeysSubscriptions, _indicesSubscriptions)
        val _existingSubscriptions: TableInfo = read(connection, "subscriptions")
        if (!_infoSubscriptions.equals(_existingSubscriptions)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |subscriptions(com.pichitube.app.core.data.db.entity.SubscriptionEntity).
              | Expected:
              |""".trimMargin() + _infoSubscriptions + """
              |
              | Found:
              |""".trimMargin() + _existingSubscriptions)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "history", "watch_later",
        "subscriptions")
  }

  public override fun clearAllTables() {
    super.performClear(false, "history", "watch_later", "subscriptions")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(HistoryDao::class, HistoryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(WatchLaterDao::class, WatchLaterDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SubscriptionDao::class, SubscriptionDao_Impl.getRequiredConverters())
    return _typeConvertersMap
  }

  public override fun getRequiredAutoMigrationSpecClasses(): Set<KClass<out AutoMigrationSpec>> {
    val _autoMigrationSpecsSet: MutableSet<KClass<out AutoMigrationSpec>> = mutableSetOf()
    return _autoMigrationSpecsSet
  }

  public override
      fun createAutoMigrations(autoMigrationSpecs: Map<KClass<out AutoMigrationSpec>, AutoMigrationSpec>):
      List<Migration> {
    val _autoMigrations: MutableList<Migration> = mutableListOf()
    return _autoMigrations
  }

  public override fun historyDao(): HistoryDao = _historyDao.value

  public override fun watchLaterDao(): WatchLaterDao = _watchLaterDao.value

  public override fun subscriptionDao(): SubscriptionDao = _subscriptionDao.value
}
