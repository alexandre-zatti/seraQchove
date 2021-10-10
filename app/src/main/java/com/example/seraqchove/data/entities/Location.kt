package com.example.seraqchove.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "location",
        foreignKeys = [ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.NO_ACTION
        )]
)
data class Location (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val user_id: Int,
    val city: String,
) : Parcelable