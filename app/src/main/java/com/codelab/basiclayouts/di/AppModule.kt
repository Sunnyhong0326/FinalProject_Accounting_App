package com.codelab.basiclayouts.di

import android.app.Application
import android.content.Context
import android.graphics.Typeface.createFromAsset
import androidx.room.Room
import com.codelab.basiclayouts.core.data.data_source.AccountingDatabase
import com.codelab.basiclayouts.core.data.repository.AccountingRepositoryImpl
import com.codelab.basiclayouts.core.data.repository.GraphqlRepoImp
import com.codelab.basiclayouts.core.domain.repository.AccountingRepository
import com.codelab.basiclayouts.core.domain.repository.GraphqlRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.codelab.basiclayouts.core.domain.use_case.AccountingUseCases
import com.codelab.basiclayouts.core.domain.use_case.GraphQLUseCases
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAccountingDatabase(
        @ApplicationContext context: Context
    ): AccountingDatabase {
        return Room.databaseBuilder(
            context,
            AccountingDatabase::class.java,
            AccountingDatabase.DATABASE_NAME
        )
            //.createFromAsset("database/accounting_database.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: AccountingDatabase): AccountingRepository{
        return AccountingRepositoryImpl(db.accountingDao)
    }

    @Provides
    @Singleton
    fun provideAccountingUseCases(repository: AccountingRepository): AccountingUseCases {
        return AccountingUseCases(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideGraphQLRepository(): GraphqlRepo{
        return GraphqlRepoImp()
    }

    @Provides
    @Singleton
    fun provideGraphQLUseCases(repository: GraphqlRepo): GraphQLUseCases {
        return GraphQLUseCases((
                repository))
    }
}