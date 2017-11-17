package ru.nitrodenov.messenger.module.channels.entity

import android.os.Parcel
import android.os.Parcelable
import ru.nitrodenov.messenger.async.TaskResult

class ChannelsData(val channels: List<ShortChannelData>) : TaskResult(), Parcelable {

    constructor(parcel: Parcel) : this(parcel.createTypedArrayList(ShortChannelData))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(channels)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ChannelsData> {

        override fun createFromParcel(parcel: Parcel): ChannelsData = ChannelsData(parcel)

        override fun newArray(size: Int): Array<ChannelsData?> = arrayOfNulls(size)

    }

}

fun createMock(): ChannelsData {
    val channels = ArrayList<ShortChannelData>()

    val logosArray = arrayOf("https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m100x100",
            "https://avatars.yandex.net/get-music-content/28589/10820c62.p.3504/m100x100",
            "https://avatars.yandex.net/get-music-content/118603/a39a9aaf.p.249991/m100x100",
            "https://avatars.yandex.net/get-music-content/49876/b25ac9ea.a.3113823-1/100x100",
            "https://avatars.yandex.net/get-music-content/28589/aba9e76b.a.1597165-1/100x100",
            "https://avatars.yandex.net/get-music-content/63210/87eafd0a.a.948624-1/100x100",
            "https://avatars.yandex.net/get-music-content/175191/3dce96bc.a.4001581-1/100x100",
            "https://avatars.yandex.net/get-music-content/42108/53d0769f.a.217019-1/100x100",
            "https://avatars.yandex.net/get-music-content/38044/68ef8131.a.2490405-1/100x100",
            "https://avatars.yandex.net/get-music-content/28589/7f848468.a.1980067-1/100x100",
            "https://avatars.yandex.net/get-music-content/49707/c35255ac.a.227551-1/100x100",
            "https://avatars.yandex.net/get-music-content/139444/d934833c.a.4767395-1/100x100",
            "https://avatars.yandex.net/get-music-content/114728/0e708af4.a.4577497-1/100x100",
            "https://avatars.yandex.net/get-music-content/118603/0ceda468.a.4780018-1/100x100",
            "https://avatars.yandex.net/get-music-content/113160/5ae287f1.a.4785071-1/100x100",
            "https://avatars.yandex.net/get-music-content/139444/d934833c.a.4767395-1/100x100",
            "https://avatars.yandex.net/get-music-content/98892/6e0dc76b.a.4745778-1/100x100",
            "https://avatars.yandex.net/get-music-content/175191/d8e501ae.a.4783574-1/100x100",
            "https://avatars.yandex.net/get-music-content/193823/e3a5d034.a.4727272-3/100x100",
            "https://avatars.yandex.net/get-music-content/114728/aee707a2.a.4369996-1/100x100",
            "https://avatars.yandex.net/get-music-content/175191/9a4b29cb.a.4794799-1/100x100")

    for (i in 0..100) {
        val logos = if (i < logosArray.size) {
            listOf(logosArray[i], logosArray[i], logosArray[i], logosArray[i])
        } else {
            listOf("https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m70x70")
        }
//        val logos = listOf("https://avatars.yandex.net/get-music-content/42108/0ce84788.p.1053/m30x30")
        val shortChannelData = ShortChannelData(id = i.toString(),
                title = "Бостонское чаепитие $i",
                time = "12:44",
                description = "dfgsfg $i",
                logos = logos)
        channels.add(shortChannelData)
    }

    return ChannelsData(channels)
}