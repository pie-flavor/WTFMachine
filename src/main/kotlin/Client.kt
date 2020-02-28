package wtfmachine

import io.socket.client.IO
import io.socket.client.Socket
import javafx.scene.input.Clipboard
import okhttp3.*
import tornadofx.*
import java.net.CookieManager
import java.net.CookiePolicy

class Client private constructor(val username: String, val http: OkHttpClient, val csrfToken: String) {
    companion object {
        const val urlString = "https://what.thedailywtf.com"
        val url = HttpUrl.parse(urlString)!!
        const val userAgent = "WTFViewer/1.0 (jellypotato)"
        fun createAndLogin(username: String, password: String): Client {
            val http = OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(CookieManager(null, CookiePolicy.ACCEPT_ALL)))
                .build()
            val cfg = http.newCall(
                Request.Builder().addHeader("User-Agent", userAgent).url(url + "/api/config").build()
            ).execute().assumeSuccess("Could not obtain CSRF token").body()!!.use { it.string() }
            val csrfToken = json.parse(NodebbConfig.serializer(), cfg).csrfToken
            val res = http.newCall(
                Request.Builder()
                    .header("X-CSRF-Token", csrfToken)
                    .header("User-Agent", userAgent)
                    .url(url + "/login")
                    .post(FormBody.Builder().add("username", username).add("password", password).build())
                    .build()
            ).execute().assumeSuccess("Unsuccessful login")
            return Client(username, http, csrfToken)
        }
        fun createAndConnect(username: String, token: String): Client {
            val http = OkHttpClient.Builder()
                .cookieJar(JavaNetCookieJar(CookieManager(null, CookiePolicy.ACCEPT_ALL)))
                .build()
            val cfg = http.newCall(
                Request.Builder().url("$url/api/config").build()
            ).execute().assumeSuccess("Could not obtain CSRF token").body()!!.use { it.string() }
            val csrfToken = json.parse(NodebbConfig.serializer(), cfg).csrfToken
            http.cookieJar().saveFromResponse(url, listOf(Cookie.parse(url, "express.sid=$token")))
            return Client(username, http, csrfToken)
        }
    }
    val socket: Socket
    var connected = false
    init {
        val opts = IO.Options()
        opts.callFactory = http
        socket = IO.socket(urlString, opts)
        socket.on(Socket.EVENT_CONNECT) {
            if (!connected) {
                addNotification("Connected")
                connected = true
            } else {
                addNotification("Reconnected")
            }
        }
        socket.on(Socket.EVENT_ERROR) {
            warn(it[0].toString())
        }
        socket.on(Socket.EVENT_DISCONNECT) {
            warn("Disconnected")
        }
    }

    fun request(f: Request.Builder.() -> Unit): Request {
        return Request.Builder()
            .header("X-CSRF-Token", csrfToken)
            .header("User-Agent", userAgent)
            .apply(f)
            .build()
    }

    val configRegex = """"configJSON"\s*:\s*"\{.*?}"\s*,""".toRegex()

    fun getUnreadTopics(): UnreadList {
        val unread = http.newCall(request { url("$url/api/unread") }).execute().assumeSuccess().body()!!.use {
            it.string()
        }.replace(configRegex, "") // FUCK YOU
        return json.parse(UnreadList.serializer(), unread)
    }

    fun addNotification(message: String) {
        //todo
    }

    fun warn(message: String) {
        //todo
    }
}