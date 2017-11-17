package ru.nitrodenov.messenger.module.channels.entity

import android.os.Parcel
import android.os.Parcelable

class ShortChannelData(val id: String,
                       val title: String,
                       val description: String,
                       val time: String,
                       val logos: List<String>) : Parcelable {

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        with(parcel) {
            writeString(id)
            writeString(title)
            writeString(description)
            writeString(time)
            writeStringList(logos)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ShortChannelData> {
        override fun createFromParcel(parcel: Parcel): ShortChannelData {
            with(parcel) {
                val logos = ArrayList<String>()
                readStringList(logos)
                return ShortChannelData(id = readString(),
                        title = readString(),
                        description = readString(),
                        time = readString(),
                        logos = logos)
            }
        }

        override fun newArray(size: Int): Array<ShortChannelData?> = arrayOfNulls(size)
    }
}