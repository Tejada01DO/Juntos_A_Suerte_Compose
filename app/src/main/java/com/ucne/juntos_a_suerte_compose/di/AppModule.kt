package com.ucne.juntos_a_suerte_compose.di

import android.content.Context
import androidx.room.Room
import com.ucne.juntos_a_suerte_compose.data.local.Juntos_A_SuerteDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideAppDbContext(@ApplicationContext context: Context) : Juntos_A_SuerteDb {
        return Room.databaseBuilder(
            context,
            Juntos_A_SuerteDb::class.java,
            "Juntos_A_SuerteDb"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providePersonDao(Juntos_A_Suerte_Db: Juntos_A_SuerteDb) = Juntos_A_Suerte_Db.personDao()

    @Provides
    fun provideTeamDao(Juntos_A_Suerte_Db: Juntos_A_SuerteDb) = Juntos_A_Suerte_Db.teamDao()
}