package com.vishnus1224.notifyme.feature.customer.data

import io.realm.kotlin.Realm
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerRepository
@Inject constructor(
    override val realm: Realm,
) : RealmRepository {

    suspend fun getAllCustomers(): List<Customer> = withContext(Dispatchers.IO) {
        realm.query(Customer::class).find()
    }

    suspend fun saveCustomer(customer: Customer) {
        realm.write {
            copyToRealm(instance = customer, updatePolicy = UpdatePolicy.ALL)
        }
    }

    suspend fun getCustomer(id: String?): Customer? = withContext(Dispatchers.IO) {
        realm.query<Customer>("id == $0", id).first().find()
    }
}