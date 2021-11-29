package com.example.dictionary.data.local.util

import com.google.gson.Gson
import java.lang.reflect.Type

/**
 * Created By Dhruv Limbachiya on 29-11-2021 06:29 PM.
 */

/**
 * A class responsible for parsing json to object or object to json.
 */
class GsonParser(
    private val gson: Gson
) : JsonParser {

    /**
     * This method deserializes the specified Json into an object of the specified type
     */
    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json,type)
    }

    /**
     * This method serializes the specified object into its equivalent Json representation
     */
    override fun <T> toJson(obj: T, type: Type): String? {
        return gson.toJson(obj,type)
    }
}