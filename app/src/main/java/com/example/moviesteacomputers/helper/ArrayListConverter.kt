package com.example.moviesteacomputers.helper

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
object ArrayListConverter {

    @TypeConverter
    fun fromIntArrayList(value: List<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIntArrayList(value: String): ArrayList<Int> {
        return try {
            Gson().fromJson(value, object : TypeToken<List<Int>>(){}.type)
        } catch (e: Exception) {
            arrayListOf()
        }
    }

}