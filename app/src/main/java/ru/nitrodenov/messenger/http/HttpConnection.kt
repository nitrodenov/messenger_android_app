package ru.nitrodenov.messenger.http

import java.net.HttpURLConnection
import java.net.URL

interface HttpConnection {

    fun getHttpConnection(url: String): HttpURLConnection
}

class HttpConnectionImpl : HttpConnection {

    override fun getHttpConnection(url: String): HttpURLConnection {
        val mUrl = URL(url)
        val httpConnection = mUrl.openConnection() as HttpURLConnection
        httpConnection.requestMethod = "GET"
        httpConnection.setRequestProperty("Content-length", "0")
        httpConnection.useCaches = false
        httpConnection.allowUserInteraction = false
        httpConnection.connectTimeout = 100000
        httpConnection.readTimeout = 100000

        httpConnection.connect()

        return httpConnection
    }

}