package com.astute.groundtruth.di

import android.app.Application
import com.astute.mission_interactors.MissionInteractors
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MissionInteractorsModule {

    @Provides
    @Singleton
    @Named("missionAndroidSqlDriver") //in instance there is another sql database
    fun provideAndroidDrive(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = MissionInteractors.schema,
            context = app,
            name = MissionInteractors.dbName,
        )
    }

    @Provides
    @Singleton
    fun provideMissionInteractors(
        @Named("missionAndroidSqlDriver") sqlDriver: SqlDriver
    ): MissionInteractors {
        return MissionInteractors.build(sqlDriver = sqlDriver)
    }

}