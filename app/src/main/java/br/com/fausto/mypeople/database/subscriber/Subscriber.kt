package br.com.fausto.mypeople.database.subscriber

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "subscriber_data_table")
data class Subscriber(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "subscriber_id")
    var id: Int,
    @ColumnInfo(name = "subscriber_name")
    var name: String,
    @ColumnInfo(name = "subscriber_email")
    var email: String,
    @ColumnInfo(name = "subscriber_number")
    var phoneNumber: String
) : Serializable
