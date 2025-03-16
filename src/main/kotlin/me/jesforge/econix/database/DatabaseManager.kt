package me.jesforge.econix.database

import me.jesforge.econix.Econix
import me.jesforge.econix.config.ConfigManager
import org.ktorm.database.Database

object DatabaseManager {

    var database: Database? = null

    fun init() {
        try {
            this.database = Database.connect(
                url = ConfigManager.settings.database.url!!,
                user = ConfigManager.settings.database.username,
                password = ConfigManager.settings.database.password,
                driver = "com.mysql.cj.jdbc.Driver",
            )
            Econix.instance.logger.info("Loaded database connection")
        } catch (ex: Exception) {
            Econix.instance.logger.warning(ex.toString())
            Econix.instance.logger.warning("Couldn't initialize database")
            Econix.instance.server.pluginManager.disablePlugin(Econix.instance)
        }
    }

    private fun createUsers() {
        println("Loading users table")
        database!!.useConnection { conn ->
            conn.createStatement().use { stmt ->
                stmt.executeUpdate(
                    """
                CREATE TABLE IF NOT EXISTS users (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    name VARCHAR(255) NOT NULL,
                    uuid VARCHAR(36) UNIQUE NOT NULL,
                    currencys VARCHAR(5000) NOT NULL
                )
                """.trimIndent()
                )
            }
        }
    }
}