package com.example.dictionary.data.local.util

import java.lang.reflect.Type

/**
 * Created By Dhruv Limbachiya on 29-11-2021 06:25 PM.
 */

/**
 *
 */
interface JsonParser {
     fun <T> fromJson(json: String,type: Type) : T?
     fun <T> toJson(obj : T, type: Type) : String?
}