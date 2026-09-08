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
import com.pichitube.app.core.`data`.db.dao.BookmarkDao
import com.pichitube.app.core.`data`.db.dao.BookmarkDao_Impl
import com.pichitube.app.core.`data`.db.dao.ChannelDao
import com.pichitube.app.core.`data`.db.dao.ChannelDao_Impl
import com.pichitube.app.core.`data`.db.dao.DeArrowCacheDao
import com.pichitube.app.core.`data`.db.dao.DeArrowCacheDao_Impl
import com.pichitube.app.core.`data`.db.dao.DownloadDao
import com.pichitube.app.core.`data`.db.dao.DownloadDao_Impl
import com.pichitube.app.core.`data`.db.dao.HistoryDao
import com.pichitube.app.core.`data`.db.dao.HistoryDao_Impl
import com.pichitube.app.core.`data`.db.dao.PlaylistDao
import com.pichitube.app.core.`data`.db.dao.PlaylistDao_Impl
import com.pichitube.app.core.`data`.db.dao.SponsorSegmentDao
import com.pichitube.app.core.`data`.db.dao.SponsorSegmentDao_Impl
import com.pichitube.app.core.`data`.db.dao.SubscriptionDao
import com.pichitube.app.core.`data`.db.dao.SubscriptionDao_Impl
import com.pichitube.app.core.`data`.db.dao.UserProfileDao
import com.pichitube.app.core.`data`.db.dao.UserProfileDao_Impl
import com.pichitube.app.core.`data`.db.dao.VideoDao
import com.pichitube.app.core.`data`.db.dao.VideoDao_Impl
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
public class PichiTubeDatabase_Impl : PichiTubeDatabase() {
  private val _videoDao: Lazy<VideoDao> = lazy {
    VideoDao_Impl(this)
  }

  private val _channelDao: Lazy<ChannelDao> = lazy {
    ChannelDao_Impl(this)
  }

  private val _subscriptionDao: Lazy<SubscriptionDao> = lazy {
    SubscriptionDao_Impl(this)
  }

  private val _historyDao: Lazy<HistoryDao> = lazy {
    HistoryDao_Impl(this)
  }

  private val _bookmarkDao: Lazy<BookmarkDao> = lazy {
    BookmarkDao_Impl(this)
  }

  private val _downloadDao: Lazy<DownloadDao> = lazy {
    DownloadDao_Impl(this)
  }

  private val _playlistDao: Lazy<PlaylistDao> = lazy {
    PlaylistDao_Impl(this)
  }

  private val _userProfileDao: Lazy<UserProfileDao> = lazy {
    UserProfileDao_Impl(this)
  }

  private val _sponsorSegmentDao: Lazy<SponsorSegmentDao> = lazy {
    SponsorSegmentDao_Impl(this)
  }

  private val _deArrowCacheDao: Lazy<DeArrowCacheDao> = lazy {
    DeArrowCacheDao_Impl(this)
  }

  protected override fun createOpenDelegate(): RoomOpenDelegate {
    val _openDelegate: RoomOpenDelegate = object : RoomOpenDelegate(2,
        "969941f1d5b49927bc92738c6da31522", "3032bb6f19ec721857cd7c75ee14f94f") {
      public override fun createAllTables(connection: SQLiteConnection) {
        connection.execSQL("CREATE TABLE IF NOT EXISTS `videos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT NOT NULL, `title` TEXT NOT NULL, `thumbnail_url` TEXT NOT NULL, `channel_id` TEXT NOT NULL, `channel_name` TEXT NOT NULL, `channel_avatar_url` TEXT NOT NULL, `view_count` INTEGER NOT NULL, `duration_seconds` INTEGER NOT NULL, `published_at` INTEGER NOT NULL, `is_short` INTEGER NOT NULL, `is_live` INTEGER NOT NULL, `description` TEXT NOT NULL, `cached_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_videos_video_id` ON `videos` (`video_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `channels` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `channel_id` TEXT NOT NULL, `name` TEXT NOT NULL, `avatar_url` TEXT NOT NULL, `banner_url` TEXT NOT NULL, `subscriber_count` INTEGER NOT NULL, `video_count` INTEGER NOT NULL, `description` TEXT NOT NULL, `folder_tag` TEXT NOT NULL, `cached_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_channels_channel_id` ON `channels` (`channel_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `subscriptions` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `channel_id` TEXT NOT NULL, `channel_name` TEXT NOT NULL, `avatar_url` TEXT NOT NULL, `subscriber_count` INTEGER NOT NULL, `folder_name` TEXT NOT NULL, `subscribed_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_subscriptions_user_id_channel_id` ON `subscriptions` (`user_id`, `channel_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `video_id` TEXT NOT NULL, `title` TEXT NOT NULL, `thumbnail_url` TEXT NOT NULL, `channel_name` TEXT NOT NULL, `duration_seconds` INTEGER NOT NULL, `watched_at` INTEGER NOT NULL, `position_ms` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_history_user_id_video_id` ON `history` (`user_id`, `video_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `bookmarks` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `video_id` TEXT NOT NULL, `title` TEXT NOT NULL, `thumbnail_url` TEXT NOT NULL, `channel_name` TEXT NOT NULL, `duration_seconds` INTEGER NOT NULL, `added_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_bookmarks_user_id_video_id` ON `bookmarks` (`user_id`, `video_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `downloads` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT NOT NULL, `title` TEXT NOT NULL, `thumbnail_url` TEXT NOT NULL, `channel_name` TEXT NOT NULL, `file_path` TEXT NOT NULL, `audio_path` TEXT NOT NULL, `quality` TEXT NOT NULL, `status` TEXT NOT NULL, `progress_percent` INTEGER NOT NULL, `size_bytes` INTEGER NOT NULL, `duration_seconds` INTEGER NOT NULL, `is_audio_only` INTEGER NOT NULL, `work_id` TEXT NOT NULL, `enqueued_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_downloads_video_id` ON `downloads` (`video_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `playlists` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `description` TEXT NOT NULL, `thumbnail_url` TEXT NOT NULL, `created_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `playlist_videos` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playlist_id` INTEGER NOT NULL, `video_id` TEXT NOT NULL, `position` INTEGER NOT NULL, `added_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_playlist_videos_playlist_id_video_id` ON `playlist_videos` (`playlist_id`, `video_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `user_profiles` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `avatar_emoji` TEXT NOT NULL, `profile_type` TEXT NOT NULL, `is_pin_locked` INTEGER NOT NULL, `pin_hash` TEXT NOT NULL, `created_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `sponsor_segments` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT NOT NULL, `start_ms` INTEGER NOT NULL, `end_ms` INTEGER NOT NULL, `category` TEXT NOT NULL, `fetched_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE INDEX IF NOT EXISTS `index_sponsor_segments_video_id` ON `sponsor_segments` (`video_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS `dearrow_cache` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `video_id` TEXT NOT NULL, `alt_title` TEXT NOT NULL, `thumbnail_timestamp` REAL NOT NULL, `thumbnail_url` TEXT NOT NULL, `fetched_at` INTEGER NOT NULL)")
        connection.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_dearrow_cache_video_id` ON `dearrow_cache` (`video_id`)")
        connection.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)")
        connection.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '969941f1d5b49927bc92738c6da31522')")
      }

      public override fun dropAllTables(connection: SQLiteConnection) {
        connection.execSQL("DROP TABLE IF EXISTS `videos`")
        connection.execSQL("DROP TABLE IF EXISTS `channels`")
        connection.execSQL("DROP TABLE IF EXISTS `subscriptions`")
        connection.execSQL("DROP TABLE IF EXISTS `history`")
        connection.execSQL("DROP TABLE IF EXISTS `bookmarks`")
        connection.execSQL("DROP TABLE IF EXISTS `downloads`")
        connection.execSQL("DROP TABLE IF EXISTS `playlists`")
        connection.execSQL("DROP TABLE IF EXISTS `playlist_videos`")
        connection.execSQL("DROP TABLE IF EXISTS `user_profiles`")
        connection.execSQL("DROP TABLE IF EXISTS `sponsor_segments`")
        connection.execSQL("DROP TABLE IF EXISTS `dearrow_cache`")
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
        val _columnsVideos: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsVideos.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("video_id", TableInfo.Column("video_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("thumbnail_url", TableInfo.Column("thumbnail_url", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("channel_id", TableInfo.Column("channel_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("channel_name", TableInfo.Column("channel_name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("channel_avatar_url", TableInfo.Column("channel_avatar_url", "TEXT",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("view_count", TableInfo.Column("view_count", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("duration_seconds", TableInfo.Column("duration_seconds", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("published_at", TableInfo.Column("published_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("is_short", TableInfo.Column("is_short", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("is_live", TableInfo.Column("is_live", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsVideos.put("cached_at", TableInfo.Column("cached_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysVideos: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesVideos: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesVideos.add(TableInfo.Index("index_videos_video_id", true, listOf("video_id"),
            listOf("ASC")))
        val _infoVideos: TableInfo = TableInfo("videos", _columnsVideos, _foreignKeysVideos,
            _indicesVideos)
        val _existingVideos: TableInfo = read(connection, "videos")
        if (!_infoVideos.equals(_existingVideos)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |videos(com.pichitube.app.core.data.db.entity.VideoEntity).
              | Expected:
              |""".trimMargin() + _infoVideos + """
              |
              | Found:
              |""".trimMargin() + _existingVideos)
        }
        val _columnsChannels: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsChannels.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("channel_id", TableInfo.Column("channel_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("avatar_url", TableInfo.Column("avatar_url", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("banner_url", TableInfo.Column("banner_url", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("subscriber_count", TableInfo.Column("subscriber_count", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("video_count", TableInfo.Column("video_count", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("folder_tag", TableInfo.Column("folder_tag", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsChannels.put("cached_at", TableInfo.Column("cached_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysChannels: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesChannels: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesChannels.add(TableInfo.Index("index_channels_channel_id", true,
            listOf("channel_id"), listOf("ASC")))
        val _infoChannels: TableInfo = TableInfo("channels", _columnsChannels, _foreignKeysChannels,
            _indicesChannels)
        val _existingChannels: TableInfo = read(connection, "channels")
        if (!_infoChannels.equals(_existingChannels)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |channels(com.pichitube.app.core.data.db.entity.ChannelEntity).
              | Expected:
              |""".trimMargin() + _infoChannels + """
              |
              | Found:
              |""".trimMargin() + _existingChannels)
        }
        val _columnsSubscriptions: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSubscriptions.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("user_id", TableInfo.Column("user_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("channel_id", TableInfo.Column("channel_id", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("channel_name", TableInfo.Column("channel_name", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("avatar_url", TableInfo.Column("avatar_url", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("subscriber_count", TableInfo.Column("subscriber_count",
            "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("folder_name", TableInfo.Column("folder_name", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSubscriptions.put("subscribed_at", TableInfo.Column("subscribed_at", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSubscriptions: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSubscriptions: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSubscriptions.add(TableInfo.Index("index_subscriptions_user_id_channel_id", true,
            listOf("user_id", "channel_id"), listOf("ASC", "ASC")))
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
        val _columnsHistory: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsHistory.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("user_id", TableInfo.Column("user_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("video_id", TableInfo.Column("video_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("thumbnail_url", TableInfo.Column("thumbnail_url", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("channel_name", TableInfo.Column("channel_name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("duration_seconds", TableInfo.Column("duration_seconds", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("watched_at", TableInfo.Column("watched_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsHistory.put("position_ms", TableInfo.Column("position_ms", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysHistory: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesHistory: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesHistory.add(TableInfo.Index("index_history_user_id_video_id", false,
            listOf("user_id", "video_id"), listOf("ASC", "ASC")))
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
        val _columnsBookmarks: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsBookmarks.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarks.put("user_id", TableInfo.Column("user_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarks.put("video_id", TableInfo.Column("video_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarks.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarks.put("thumbnail_url", TableInfo.Column("thumbnail_url", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarks.put("channel_name", TableInfo.Column("channel_name", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarks.put("duration_seconds", TableInfo.Column("duration_seconds", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsBookmarks.put("added_at", TableInfo.Column("added_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysBookmarks: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesBookmarks: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesBookmarks.add(TableInfo.Index("index_bookmarks_user_id_video_id", true,
            listOf("user_id", "video_id"), listOf("ASC", "ASC")))
        val _infoBookmarks: TableInfo = TableInfo("bookmarks", _columnsBookmarks,
            _foreignKeysBookmarks, _indicesBookmarks)
        val _existingBookmarks: TableInfo = read(connection, "bookmarks")
        if (!_infoBookmarks.equals(_existingBookmarks)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |bookmarks(com.pichitube.app.core.data.db.entity.BookmarkEntity).
              | Expected:
              |""".trimMargin() + _infoBookmarks + """
              |
              | Found:
              |""".trimMargin() + _existingBookmarks)
        }
        val _columnsDownloads: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsDownloads.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("video_id", TableInfo.Column("video_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("title", TableInfo.Column("title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("thumbnail_url", TableInfo.Column("thumbnail_url", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("channel_name", TableInfo.Column("channel_name", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("file_path", TableInfo.Column("file_path", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("audio_path", TableInfo.Column("audio_path", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("quality", TableInfo.Column("quality", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("status", TableInfo.Column("status", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("progress_percent", TableInfo.Column("progress_percent", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("size_bytes", TableInfo.Column("size_bytes", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("duration_seconds", TableInfo.Column("duration_seconds", "INTEGER",
            true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("is_audio_only", TableInfo.Column("is_audio_only", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("work_id", TableInfo.Column("work_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDownloads.put("enqueued_at", TableInfo.Column("enqueued_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysDownloads: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesDownloads: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesDownloads.add(TableInfo.Index("index_downloads_video_id", true, listOf("video_id"),
            listOf("ASC")))
        val _infoDownloads: TableInfo = TableInfo("downloads", _columnsDownloads,
            _foreignKeysDownloads, _indicesDownloads)
        val _existingDownloads: TableInfo = read(connection, "downloads")
        if (!_infoDownloads.equals(_existingDownloads)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |downloads(com.pichitube.app.core.data.db.entity.DownloadEntity).
              | Expected:
              |""".trimMargin() + _infoDownloads + """
              |
              | Found:
              |""".trimMargin() + _existingDownloads)
        }
        val _columnsPlaylists: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPlaylists.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylists.put("user_id", TableInfo.Column("user_id", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylists.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylists.put("description", TableInfo.Column("description", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylists.put("thumbnail_url", TableInfo.Column("thumbnail_url", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylists.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPlaylists: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPlaylists: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoPlaylists: TableInfo = TableInfo("playlists", _columnsPlaylists,
            _foreignKeysPlaylists, _indicesPlaylists)
        val _existingPlaylists: TableInfo = read(connection, "playlists")
        if (!_infoPlaylists.equals(_existingPlaylists)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |playlists(com.pichitube.app.core.data.db.entity.PlaylistEntity).
              | Expected:
              |""".trimMargin() + _infoPlaylists + """
              |
              | Found:
              |""".trimMargin() + _existingPlaylists)
        }
        val _columnsPlaylistVideos: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsPlaylistVideos.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylistVideos.put("playlist_id", TableInfo.Column("playlist_id", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylistVideos.put("video_id", TableInfo.Column("video_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylistVideos.put("position", TableInfo.Column("position", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsPlaylistVideos.put("added_at", TableInfo.Column("added_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysPlaylistVideos: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesPlaylistVideos: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesPlaylistVideos.add(TableInfo.Index("index_playlist_videos_playlist_id_video_id",
            true, listOf("playlist_id", "video_id"), listOf("ASC", "ASC")))
        val _infoPlaylistVideos: TableInfo = TableInfo("playlist_videos", _columnsPlaylistVideos,
            _foreignKeysPlaylistVideos, _indicesPlaylistVideos)
        val _existingPlaylistVideos: TableInfo = read(connection, "playlist_videos")
        if (!_infoPlaylistVideos.equals(_existingPlaylistVideos)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |playlist_videos(com.pichitube.app.core.data.db.entity.PlaylistVideoEntity).
              | Expected:
              |""".trimMargin() + _infoPlaylistVideos + """
              |
              | Found:
              |""".trimMargin() + _existingPlaylistVideos)
        }
        val _columnsUserProfiles: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsUserProfiles.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfiles.put("name", TableInfo.Column("name", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfiles.put("avatar_emoji", TableInfo.Column("avatar_emoji", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfiles.put("profile_type", TableInfo.Column("profile_type", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfiles.put("is_pin_locked", TableInfo.Column("is_pin_locked", "INTEGER", true,
            0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfiles.put("pin_hash", TableInfo.Column("pin_hash", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsUserProfiles.put("created_at", TableInfo.Column("created_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysUserProfiles: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesUserProfiles: MutableSet<TableInfo.Index> = mutableSetOf()
        val _infoUserProfiles: TableInfo = TableInfo("user_profiles", _columnsUserProfiles,
            _foreignKeysUserProfiles, _indicesUserProfiles)
        val _existingUserProfiles: TableInfo = read(connection, "user_profiles")
        if (!_infoUserProfiles.equals(_existingUserProfiles)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |user_profiles(com.pichitube.app.core.data.db.entity.UserProfileEntity).
              | Expected:
              |""".trimMargin() + _infoUserProfiles + """
              |
              | Found:
              |""".trimMargin() + _existingUserProfiles)
        }
        val _columnsSponsorSegments: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsSponsorSegments.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSponsorSegments.put("video_id", TableInfo.Column("video_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSponsorSegments.put("start_ms", TableInfo.Column("start_ms", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsSponsorSegments.put("end_ms", TableInfo.Column("end_ms", "INTEGER", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSponsorSegments.put("category", TableInfo.Column("category", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsSponsorSegments.put("fetched_at", TableInfo.Column("fetched_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysSponsorSegments: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesSponsorSegments: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesSponsorSegments.add(TableInfo.Index("index_sponsor_segments_video_id", false,
            listOf("video_id"), listOf("ASC")))
        val _infoSponsorSegments: TableInfo = TableInfo("sponsor_segments", _columnsSponsorSegments,
            _foreignKeysSponsorSegments, _indicesSponsorSegments)
        val _existingSponsorSegments: TableInfo = read(connection, "sponsor_segments")
        if (!_infoSponsorSegments.equals(_existingSponsorSegments)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |sponsor_segments(com.pichitube.app.core.data.db.entity.SponsorSegmentEntity).
              | Expected:
              |""".trimMargin() + _infoSponsorSegments + """
              |
              | Found:
              |""".trimMargin() + _existingSponsorSegments)
        }
        val _columnsDearrowCache: MutableMap<String, TableInfo.Column> = mutableMapOf()
        _columnsDearrowCache.put("id", TableInfo.Column("id", "INTEGER", true, 1, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDearrowCache.put("video_id", TableInfo.Column("video_id", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDearrowCache.put("alt_title", TableInfo.Column("alt_title", "TEXT", true, 0, null,
            TableInfo.CREATED_FROM_ENTITY))
        _columnsDearrowCache.put("thumbnail_timestamp", TableInfo.Column("thumbnail_timestamp",
            "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDearrowCache.put("thumbnail_url", TableInfo.Column("thumbnail_url", "TEXT", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        _columnsDearrowCache.put("fetched_at", TableInfo.Column("fetched_at", "INTEGER", true, 0,
            null, TableInfo.CREATED_FROM_ENTITY))
        val _foreignKeysDearrowCache: MutableSet<TableInfo.ForeignKey> = mutableSetOf()
        val _indicesDearrowCache: MutableSet<TableInfo.Index> = mutableSetOf()
        _indicesDearrowCache.add(TableInfo.Index("index_dearrow_cache_video_id", true,
            listOf("video_id"), listOf("ASC")))
        val _infoDearrowCache: TableInfo = TableInfo("dearrow_cache", _columnsDearrowCache,
            _foreignKeysDearrowCache, _indicesDearrowCache)
        val _existingDearrowCache: TableInfo = read(connection, "dearrow_cache")
        if (!_infoDearrowCache.equals(_existingDearrowCache)) {
          return RoomOpenDelegate.ValidationResult(false, """
              |dearrow_cache(com.pichitube.app.core.data.db.entity.DeArrowCacheEntity).
              | Expected:
              |""".trimMargin() + _infoDearrowCache + """
              |
              | Found:
              |""".trimMargin() + _existingDearrowCache)
        }
        return RoomOpenDelegate.ValidationResult(true, null)
      }
    }
    return _openDelegate
  }

  protected override fun createInvalidationTracker(): InvalidationTracker {
    val _shadowTablesMap: MutableMap<String, String> = mutableMapOf()
    val _viewTables: MutableMap<String, Set<String>> = mutableMapOf()
    return InvalidationTracker(this, _shadowTablesMap, _viewTables, "videos", "channels",
        "subscriptions", "history", "bookmarks", "downloads", "playlists", "playlist_videos",
        "user_profiles", "sponsor_segments", "dearrow_cache")
  }

  public override fun clearAllTables() {
    super.performClear(false, "videos", "channels", "subscriptions", "history", "bookmarks",
        "downloads", "playlists", "playlist_videos", "user_profiles", "sponsor_segments",
        "dearrow_cache")
  }

  protected override fun getRequiredTypeConverterClasses(): Map<KClass<*>, List<KClass<*>>> {
    val _typeConvertersMap: MutableMap<KClass<*>, List<KClass<*>>> = mutableMapOf()
    _typeConvertersMap.put(VideoDao::class, VideoDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(ChannelDao::class, ChannelDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SubscriptionDao::class, SubscriptionDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(HistoryDao::class, HistoryDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(BookmarkDao::class, BookmarkDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(DownloadDao::class, DownloadDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(PlaylistDao::class, PlaylistDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(UserProfileDao::class, UserProfileDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(SponsorSegmentDao::class, SponsorSegmentDao_Impl.getRequiredConverters())
    _typeConvertersMap.put(DeArrowCacheDao::class, DeArrowCacheDao_Impl.getRequiredConverters())
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

  public override fun videoDao(): VideoDao = _videoDao.value

  public override fun channelDao(): ChannelDao = _channelDao.value

  public override fun subscriptionDao(): SubscriptionDao = _subscriptionDao.value

  public override fun historyDao(): HistoryDao = _historyDao.value

  public override fun bookmarkDao(): BookmarkDao = _bookmarkDao.value

  public override fun downloadDao(): DownloadDao = _downloadDao.value

  public override fun playlistDao(): PlaylistDao = _playlistDao.value

  public override fun userProfileDao(): UserProfileDao = _userProfileDao.value

  public override fun sponsorSegmentDao(): SponsorSegmentDao = _sponsorSegmentDao.value

  public override fun deArrowCacheDao(): DeArrowCacheDao = _deArrowCacheDao.value
}
