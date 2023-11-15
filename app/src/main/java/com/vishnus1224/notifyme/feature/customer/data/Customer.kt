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
    var reminderDate: Long?
) : RealmObject {

    constructor(): this(
        id = "",
        phoneNumber = "",
        name = "",
        address= "",
        createdAt = System.currentTimeMillis(),
        reminderDate = null,
    )
}