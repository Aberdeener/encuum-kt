import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

data class ScrapeOpts(val headless: Boolean, val baseUrl: String, val username: String, val password: String, val forumBase: String)

object Forums: IntIdTable() {
    val url = text("url").default("")
    val title = text("title").default("")
    val idx = uniqueIndex(url, title)
}
class Forum(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Forum>(Forums)
    var url by Forums.url
    var title by Forums.title
    val threads by ForumThread referrersOn ForumThreads.forum
}

object ForumThreads: IntIdTable() {
    val url = text("url").default("")
    val title = text("title").default("")
    val posterId = reference("poster_id", Users)
    val forum = reference("forum", Forums)
    val idx = uniqueIndex(url, title, posterId, forum)
}

class ForumThread(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<ForumThread>(ForumThreads)
    var url by ForumThreads.url
    var title by ForumThreads.title
    var posterId by ForumThreads.posterId
    var forum by ForumThreads.forum
    val posts by Post referrersOn Posts.forumThread
}

object Posts: IntIdTable() {
    val url = text("url").default("")
    val seq = integer("seq").default(1)
    val posterId = reference("poster_id", Users)
    val bbcode = text("bbcode").default("")
    val posted = text("posted").default("")
    val enjinPostId = integer("enjinPostId").default(0)
    val forumThread = reference("forumThread", ForumThreads)
    val idx = uniqueIndex(enjinPostId, url, seq, posterId, bbcode, forumThread)
}

class Post(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<Post>(Posts)
    var url by Posts.url
    var seq by Posts.seq
    var posterId by Posts.posterId
    var bbcode by Posts.bbcode
    var posted by Posts.posted
    var enjinPostId by Posts.enjinPostId
    var forumThread by Posts.forumThread
}

object Users: IntIdTable() {
    val username = text("username").default("")
    val uuid = text("uuid").default("")
    val enjinUserId = integer("enjinUserId").default(0)
    val idx = uniqueIndex(username, enjinUserId)
}

class User(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<User>(Users)
    var username by Users.username
    var uuid by Users.uuid
    var enjinUserId by Users.enjinUserId
}