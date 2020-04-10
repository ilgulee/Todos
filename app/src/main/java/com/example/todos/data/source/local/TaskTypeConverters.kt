package com.example.todos.data.source.local

import androidx.room.TypeConverter
import java.util.*

class TaskTypeConverters {

    @TypeConverter
    fun longFromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun longToDate(milli: Long?): Date? {
        return milli?.let {
            Date(it)
        }
    }

    @TypeConverter
    fun stringToUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun stringFromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}