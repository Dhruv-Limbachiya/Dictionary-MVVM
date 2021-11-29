package com.example.dictionary.data.local.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.dictionary.data.local.util.JsonParser
import com.example.dictionary.data.model.Meaning
import com.google.gson.reflect.TypeToken

/**
 * Created By Dhruv Limbachiya on 29-11-2021 06:10 PM.
 */

/**
 * Custom converter class for Dictionary database.
 * @ProvidedTypeConverter : required when we want to pass dependencies(Here,JsonParser) to our type converter classes.
 * It means control of instantiation of Converter class is in our hand.
 */
@ProvidedTypeConverter
class Converter(
    private val jsonParser: JsonParser
) {
    /**
     * Deserializes the specified json into list of Meaning objects
     */
    @TypeConverter
    fun fromMeaningJsonToList(json: String) : List<Meaning> {
        return jsonParser.fromJson<ArrayList<Meaning>>(
            json,
            object : TypeToken<ArrayList<Meaning>>(){}.type // Get the type of an object.
        ) ?: emptyList()
    }

    /**
     * Serializes the list of meaning objects into json string.
     */
    @TypeConverter
    fun fromMeaningListToJson(meanings: List<Meaning>) : String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<ArrayList<Meaning>>(){}.type // Get the type of an object.
        ) ?: "[]" // empty array.
    }
}

