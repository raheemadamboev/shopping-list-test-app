package xyz.teamgravity.shoppinglisttest.injection

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.teamgravity.shoppinglisttest.api.PixabayApi
import xyz.teamgravity.shoppinglisttest.helper.constants.PixabayApiConstants
import xyz.teamgravity.shoppinglisttest.helper.constants.ShoppingDatabase
import xyz.teamgravity.shoppinglisttest.viewmodel.MyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideMyDatabase(app: App) = Room.databaseBuilder(app, MyDatabase::class.java, ShoppingDatabase.DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(db: MyDatabase) = db.dao()

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(PixabayApiConstants.BASE_URL)
        .build()
        .create(PixabayApi::class.java)
}