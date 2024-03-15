package com.xenia.testvk.di

import android.content.Context
import androidx.room.Room
import com.xenia.testvk.data.data_source.CryptoClass
import com.xenia.testvk.data.data_source.Database
import com.xenia.testvk.data.data_source.PasswordDao
import com.xenia.testvk.data.mapper.PasswordMapper
import com.xenia.testvk.data.repository.PassphraseRepository
import com.xenia.testvk.data.repository.PasswordRepositoryImpl
import com.xenia.testvk.domain.repository.PasswordRepository
import com.xenia.testvk.domain.usecases.passwords_usecases.AddNewPasswordUseCase
import com.xenia.testvk.domain.usecases.passwords_usecases.DeletePasswordUseCase
import com.xenia.testvk.domain.usecases.passwords_usecases.GetPasswordByIdUseCase
import com.xenia.testvk.domain.usecases.passwords_usecases.GetPasswordsUseCase
import com.xenia.testvk.domain.usecases.UseCases
import com.xenia.testvk.domain.usecases.enter_usecases.CheckFileMasterPasswordUseCase
import com.xenia.testvk.domain.usecases.enter_usecases.SetMasterPasswordUseCase
import com.xenia.testvk.domain.usecases.enter_usecases.ValidateMasterPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        passphraseRepository: PassphraseRepository
    ): Database =
        Room.databaseBuilder(context, Database::class.java, "PasswordDB")
            .openHelperFactory(SupportFactory(passphraseRepository.getPassphrase()))
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providesPasswordRepo(
        passwordDao: PasswordDao,
        mapper: PasswordMapper,
        cryptoClass: CryptoClass
    ): PasswordRepository {
        return PasswordRepositoryImpl(
            passwordDao,
            mapper,
            cryptoClass
        )
    }

    @Provides
    fun providesPassphraseRepo(@ApplicationContext context: Context): PassphraseRepository {
        return PassphraseRepository(context)
    }

    @Provides
    fun providesDataMapper(): PasswordMapper {
        return PasswordMapper()
    }

    @Provides
    fun providesCryptoClass(): CryptoClass {
        return CryptoClass()
    }

    @Provides
    @Singleton
    fun providesUseCases(passwordRepository: PasswordRepository): UseCases {
        return UseCases(
            allPasswords = GetPasswordsUseCase(passwordRepository),
            getPasswordById = GetPasswordByIdUseCase(passwordRepository),
            addPassword = AddNewPasswordUseCase(passwordRepository),
            deletePassword = DeletePasswordUseCase(passwordRepository),
            checkFileMasterPassword = CheckFileMasterPasswordUseCase(passwordRepository),
            setMasterPassword = SetMasterPasswordUseCase(passwordRepository),
            validateMasterPassword = ValidateMasterPasswordUseCase(passwordRepository)
        )
    }

    @Provides
    fun provideItemDao(appDatabase: Database): PasswordDao = appDatabase.passwordDao

}