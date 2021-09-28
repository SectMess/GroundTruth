package ui_missiondetail.di

import com.astute.core.util.Logger
import com.astute.mission_interactors.GetMissionFromCache
import com.astute.mission_interactors.MissionInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MissionDetailModule {

    @Provides
    @Singleton
    fun provideGetMissionFromCache(
        interactors: MissionInteractors
    ): GetMissionFromCache{
        return interactors.getMissionFromCache
    }

    @Provides
    @Singleton
    @Named("missionDetailLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag="MissionDetail",
            isDebug = true
        )
    }
}