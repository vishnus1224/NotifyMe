package com.vishnus1224.notifyme.feature.customer.data

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Customer(
    @PrimaryKey
    var id: String,
    var phoneNumber: String,
    var name: String,
    var address: String,
    var createdAt: Long,
) : RealmObject {

    constructor(): this("","", "", "", System.currentTimeMillis())
}