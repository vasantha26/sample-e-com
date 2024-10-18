package com.example.sample.database

import android.app.Application
import androidx.room.Room
import com.example.sample.dao.OrderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(app, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration() // Optional: Handle migrations
            .build()
    }

    @Provides
    @Singleton
    fun provideOrderDao(database: AppDatabase): OrderDao {
        return database.orderDao()
    }
}
