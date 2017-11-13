package ru.nitrodenov.messenger.module.channel.entity

import android.os.Parcel
import android.os.Parcelable

class Message(val isIncomeMessage: Boolean,
              val text: String?,
              val time: String,
              val logo: String?,
              val imageInMessage: String?) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readByte() != 0.toByte(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isIncomeMessage) 1 else 0)
        parcel.writeString(text)
        parcel.writeString(time)
        parcel.writeString(logo)
        parcel.writeString(imageInMessage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Message> {
        override fun createFromParcel(parcel: Parcel): Message {
            return Message(parcel)
        }

        override fun newArray(size: Int): Array<Message?> {
            return arrayOfNulls(size)
        }
    }
}