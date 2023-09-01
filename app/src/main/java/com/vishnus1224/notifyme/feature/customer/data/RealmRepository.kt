package com.vishnus1224.notifyme.feature.customer.data

import io.realm.kotlin.Realm

interface RealmRepository {
    val realm: Realm

    fun destroy() {
        realm.close()
    }
}