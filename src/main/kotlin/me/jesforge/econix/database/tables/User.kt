package me.jesforge.econix.database.tables

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar

interface User : Entity<User> {
    val id: Int
    val name: String
    val uuid: String
    val currencys: String
}

object Users : Table<User>("users") {
    val id = int("id").primaryKey().bindTo { it.id }
    val name = varchar("name").bindTo { it.name }
    val uuid = varchar("uuid").bindTo { it.uuid }
    val currencys = varchar("currencys").bindTo { it.currencys }
}