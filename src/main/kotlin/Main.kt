import io.github.cdimascio.dotenv.dotenv

fun main() {
    val dotenv = dotenv()
    val opts = ScrapeOpts(
        headless = dotenv["headless", "true"].toBoolean(),
        baseUrl = dotenv["baseurl"],
        username = dotenv["username"],
        password = dotenv["password"],
        forumBase = dotenv["forumbase"]
    )
    mkTables()
    scrape(opts)
}