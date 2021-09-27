package ui_missionlist.di

import com.astute.core.util.Logger
import com.astute.mission_interactors.FilterMission
import com.astute.mission_interactors.GetMissions
import com.astute.mission_interactors.MissionInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MissionListModule {

    @Provides
    @Singleton
    fun provideGetMissions(
        interactors: MissionInteractors
    ): GetMissions{
        return interactors.getMissions
    }

    @Provides
    @Singleton
    fun provideFilterMission(
        interactors: MissionInteractors
    ): FilterMission{
        return interactors.filterMission
    }

    @Provides
    @Singleton
    @Named("missionListLogger")
    fun provideLogger(): Logger {
        return Logger(
            tag="MissionList",
            isDebug = true
        )
    }
}