package ru.nitrodenov.messenger.module.channel.entity

import android.os.Parcel
import android.os.Parcelable
import ru.nitrodenov.messenger.async.TaskResult

class ChannelData(val messages: ArrayList<Message>) : TaskResult(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(Message))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(messages)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ChannelData> {

        override fun createFromParcel(parcel: Parcel): ChannelData = ChannelData(parcel)

        override fun newArray(size: Int): Array<ChannelData?> = arrayOfNulls(size)

    }
}

fun createMock(): ChannelData {
    val messages: ArrayList<Message> = ArrayList()

    val message1 = Message(true,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/139444/d934833c.a.4767395-1/100x100",
            "https://avatars.yandex.net/get-music-content/139444/d934833c.a.4767395-1/400x400")
    val message2 = Message(false,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            null)
    val message3 = Message(true,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/49876/b25ac9ea.a.3113823-1/100x100",
            null)
    val message4 = Message(false,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            null)
    val message5 = Message(true,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/28589/aba9e76b.a.1597165-1/100x100",
            null)
    val message6 = Message(false,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            null)
    val message7 = Message(true,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/28589/aba9e76b.a.1597165-1/100x100",
            null)
    val message8 = Message(false,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            null)
    val message9 = Message(true,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/49876/b25ac9ea.a.3113823-1/100x100",
            null)
    val message10 = Message(false,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            null)
    val message11 = Message(true,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/139444/d934833c.a.4767395-1/100x100",
            null)
    val message12 = Message(false,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            null)
    val message13 = Message(true,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/139444/d934833c.a.4767395-1/100x100",
            null)
    val message14 = Message(false,
            "message message message message message message message message message message message message message message message message message message message message message message ",
            "21:00",
            "https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            null)

    messages.add(message1)
    messages.add(message2)
    messages.add(message3)
    messages.add(message4)
    messages.add(message5)
    messages.add(message6)
    messages.add(message7)
    messages.add(message8)
    messages.add(message9)
    messages.add(message10)
    messages.add(message11)
    messages.add(message12)
    messages.add(message13)
    messages.add(message14)

    return ChannelData(messages)
}