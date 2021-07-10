package com.alpertign.pokemon.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Pokemon(
    @ColumnInfo(name = "description")
    @SerializedName("description")
    val description: String?,
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int?,
    @ColumnInfo(name = "imageUrl")
    @SerializedName("imageUrl")
    val imageUrl: String?,
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val name: String?
){

    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

}