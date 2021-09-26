package ui_missiondetail.di

import com.astute.mission_interactors.GetMissionFromCache
import com.astute.mission_interactors.MissionInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}