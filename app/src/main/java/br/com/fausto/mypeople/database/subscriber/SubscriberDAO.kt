package br.com.fausto.mypeople.database.subscriber

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {

    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber)

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("SELECT * FROM subscriber_data_table where subscriber_id = :id")
    suspend fun searchById(id: Int): Subscriber

    @Query("DELETE FROM subscriber_data_table")
    suspend fun deleteAllSubscribers()

    @Query("SELECT * FROM subscriber_data_table")
    fun getAllSubscribers(): LiveData<List<Subscriber>>
}