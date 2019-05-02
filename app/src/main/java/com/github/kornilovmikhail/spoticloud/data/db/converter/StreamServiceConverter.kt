package com.github.kornilovmikhail.spoticloud.data.db.converter

import androidx.room.TypeConverter
import com.github.kornilovmikhail.spoticloud.core.model.StreamServiceEnum

class StreamServiceConverter {

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromStreamServiceEnum(service: StreamServiceEnum): String = service.name

        @TypeConverter
        @JvmStatic
        fun toStreamServiceEnum(name: String): StreamServiceEnum = StreamServiceEnum.valueOf(name)
    }
}
