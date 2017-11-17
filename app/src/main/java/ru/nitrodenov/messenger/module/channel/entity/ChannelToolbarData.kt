package ru.nitrodenov.messenger.module.channel.entity

import android.os.Parcel
import android.os.Parcelable

class ChannelToolbarData(val imageUrls: List<String>, val title: String, val description: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.createStringArrayList(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStringList(imageUrls)
        parcel.writeString(title)
        parcel.writeString(description)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ChannelToolbarData> {

        override fun createFromParcel(parcel: Parcel): ChannelToolbarData =
                ChannelToolbarData(parcel)

        override fun newArray(size: Int): Array<ChannelToolbarData?> = arrayOfNulls(size)

    }
}