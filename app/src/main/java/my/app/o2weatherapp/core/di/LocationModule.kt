package my.app.o2weatherapp.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import my.app.o2weatherapp.core.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationTracker(@ApplicationContext context: Context): LocationTracker {
        return LocationTracker(context)
    }
}