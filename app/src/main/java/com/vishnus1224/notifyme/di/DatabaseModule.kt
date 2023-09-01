package com.vishnus1224.notifyme.di

import com.vishnus1224.notifyme.feature.customer.data.Customer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

@Module
@InstallIn(ViewModelComponent::class)
class DatabaseModule {

    @Provides
    fun provideRealmConfiguration(): RealmConfiguration = RealmConfiguration.Builder(
        schema = setOf(Customer::class)
    ).build()

    @Provides
    fun provideRealm(config: RealmConfiguration): Realm = Realm.open(config)
}