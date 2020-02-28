@file:UseSerializers(InstantSerializer::class, IntBoolSerializer::class, ColorSerializer::class)

package wtfmachine

import javafx.scene.paint.Color
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class NodebbConfig(@SerialName("csrf_token") val csrfToken: String)

@Serializable
data class UnreadList(val showSelect: Boolean, val nextStart: Int, val topics: List<Topic> = listOf(),
                      val topicCount: Int = 0, val pageCount: Int)

@Serializable
data class Topic(val cid: Int = 3, val tid: Int = 1, val uid: Int = 1, val mainPid: Int?, val upvotes: Int = 0,
                 val downvotes: Int = 0, val title: String = "", @SerialName("postcount") val postCount: Int = 0,
                 val teaserPid: Int? = null, @SerialName("timestampISO") val timestamp: Instant,
                 val deleted: Boolean = false, val locked: Boolean, val pinned: Boolean = false,
                 val unread: Boolean = false, val bookmark: Int? = null, private val category: Category? = null,
                 private val user: User? = null) {
    fun category(client: Client): Category {
        return category ?: TODO()
    }
    fun user(client: Client): User {
        return user ?: TODO()
    }
}

@Serializable
data class Category(val cid: Int, val name: String, val bgColor: Color = Color.GREEN)

@Serializable
data class User(val uid: Int, val banned: Boolean = false, val status: String? = null, val userslug: String,
                val username: String, @SerialName("postcount") val postCount: Int = 0,
                val signature: String? = null, val reputation: Int = 0)
