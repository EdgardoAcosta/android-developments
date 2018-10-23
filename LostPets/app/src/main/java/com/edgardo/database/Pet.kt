package com.edgardo.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import java.util.Date

@Entity(tableName = "Pet")
@TypeConverters(Converters::class)

data class Pet(
    @ColumnInfo(name = "Name") var Name: String?,
    @ColumnInfo(name = "Race") var Race: Int,
    @ColumnInfo(name = "LocationFound") var LocationFound: String?,
    @ColumnInfo(name = "DateFound") var DateFound: String?,
    @ColumnInfo(name = "Phone") var Phone: String?,
    @ColumnInfo(name = "Email") var Email: String?,
    @ColumnInfo(name = "ImagenPet") var ImagePet: ByteArray?,
    @ColumnInfo(name = "Favorite") var Favorite: Int?
) : Parcelable {
    @ColumnInfo(name = "_id")
    @PrimaryKey(autoGenerate = true)
    var _id: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()!!,
        parcel.createByteArray(),
        parcel.readInt()
    ) {
        _id = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeInt(Race)
        parcel.writeString(DateFound)
        parcel.writeString(LocationFound)
        parcel.writeString(Phone)
        parcel.writeString(Email)
        parcel.writeByteArray(ImagePet)
        parcel.writeInt(Favorite!!)
        parcel.writeInt(_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Pet> {
        override fun createFromParcel(parcel: Parcel): Pet {
            return Pet(parcel)
        }

        override fun newArray(size: Int): Array<Pet?> {
            return arrayOfNulls(size)
        }
    }
}