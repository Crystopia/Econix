package me.jesforge.econix.database.tables

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar


object Users : Table<Nothing>("users") {
    val id = int("id")
    val name = varchar("name")
    val uuid = varchar("uuid").primaryKey()
    val currencys = varchar("currencys")
}