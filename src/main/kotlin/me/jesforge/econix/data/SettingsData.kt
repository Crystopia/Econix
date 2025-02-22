package me.jesforge.econix.data

import kotlinx.serialization.Serializable

@Serializable
data class SettingsData(
    var database: DatabaseData,
    var loadDefaults: Boolean = true,
)

@Serializable
data class DatabaseData(
    var username: String = "",
    var password: String? = "",
    var url: String? = "jdbc:mysql://localhost:3306/database",
)

