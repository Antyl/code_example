package com.atl.ayan.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.atl.ayan.model.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(context: Context) {

    private var mContext: Context = context.applicationContext

    @Singleton
    @Provides
    fun context(): Context {
        return mContext
    }

    @Singleton
    @Provides
    fun roomDatabase(): AppDatabase {
        return Room.databaseBuilder(context(), AppDatabase::class.java,"database_v4")
            .allowMainThreadQueries()
//            .fallbackToDestructiveMigration()
//            .addMigrations(MIGRATION_3_4)
            .build()
    }

//    val MIGRATION_3_4: Migration =
//        object : Migration(3, 4) {
//            override fun migrate(database: SupportSQLiteDatabase) {}
//        }
}