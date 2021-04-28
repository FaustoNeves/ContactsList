package br.com.fausto.contactslist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contact_data_table")
data class Contact(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    var id: Int,
    @ColumnInfo(name = "contact_name")
    var name: String,
    @ColumnInfo(name = "contact_email")
    var email: String,
    @ColumnInfo(name = "contact_number")
    var phoneNumber: String
) : Serializable
