package me.jesforge.econix.config

interface Configurable {
    fun save()
    fun load() {}
    fun reset() {}
}