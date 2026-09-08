package com.pichitube.app.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pichitube.app.core.data.db.dao.HistoryDao
import com.pichitube.app.core.data.db.dao.WatchLaterDao
import com.pichitube.app.core.data.db.dao.SubscriptionDao
import com.pichitube.app.core.data.db.entity.HistoryEntity
import com.pichitube.app.core.data.db.entity.WatchLaterEntity
import com.pichitube.app.core.data.db.entity.SubscriptionEntity

@Database(
    entities = [HistoryEntity::class, WatchLaterEntity::class, SubscriptionEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun watchLaterDao(): WatchLaterDao
    abstract fun subscriptionDao(): SubscriptionDao
}
