package xyz.teamgravity.shoppinglisttest.injection

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.teamgravity.shoppinglisttest.viewmodel.MyDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestApplicationModule {

    @Provides
    @Named("memory_database")
    fun provideInMemoryDatabase(app: Application) =
        Room.inMemoryDatabaseBuilder(app, MyDatabase::class.java).allowMainThreadQueries().build()
}