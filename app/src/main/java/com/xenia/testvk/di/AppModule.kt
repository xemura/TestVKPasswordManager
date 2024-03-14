package com.xenia.testvk.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.xenia.testvk.data.data_source.Database
import com.xenia.testvk.data.data_source.PasswordDao
import com.xenia.testvk.data.repository.PasswordRepositoryImpl
import com.xenia.testvk.domain.repository.PasswordRepository
import com.xenia.testvk.domain.usecases.AddNewPasswordUseCase
import com.xenia.testvk.domain.usecases.DeletePasswordUseCase
import com.xenia.testvk.domain.usecases.GetPasswordByIdUseCase
import com.xenia.testvk.domain.usecases.GetPasswordsUseCase
import com.xenia.testvk.domain.usecases.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun providesDatabase(app: Application): Database {
//        return Room.databaseBuilder(
//            app,
//            Database::class.java,
//            "PasswordDB"
//        ).build()
//    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): Database =
        Room.databaseBuilder(context, Database::class.java, "PasswordDB")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesPasswordRepo(passwordDao: PasswordDao): PasswordRepository {
        return PasswordRepositoryImpl(passwordDao)
    }

    @Provides
    @Singleton
    fun providesUseCases(passwordRepository: PasswordRepository): UseCases {
        return UseCases(
            allPasswords = GetPasswordsUseCase(passwordRepository),
            getPasswordById = GetPasswordByIdUseCase(passwordRepository),
            addPassword = AddNewPasswordUseCase(passwordRepository),
            deletePassword = DeletePasswordUseCase(passwordRepository)
        )
    }

    @Provides
    fun provideItemDao(appDatabase: Database): PasswordDao = appDatabase.passwordDao

}