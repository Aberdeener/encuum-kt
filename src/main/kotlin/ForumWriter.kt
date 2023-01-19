import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.name
import org.jetbrains.exposed.sql.transactions.transaction

object DB {
    val db by lazy {
        Database.connect(url = "jdbc:sqlite:forum.db", databaseConfig = DatabaseConfig{ useNestedTransactions = true })
    }
}

fun mkTables() {
    println("Connected to ${DB.db.name}")
    transaction {
        SchemaUtils.create(Forums, ForumThreads, Posts, Users)
    }
}
