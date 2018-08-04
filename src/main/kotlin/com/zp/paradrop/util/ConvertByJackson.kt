package com.zp.paradrop.util

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.core.JsonParseException
import java.io.IOException
import java.io.DataInput
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.DeserializationFeature
import java.io.InputStream
import java.io.Reader
import java.lang.reflect.Type


object ConvertByJackson{
    private val mapper = ObjectMapper()

    init {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }

    private fun create(): ObjectMapper {
        return mapper
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(json: String, valueType: Class<T>): T {
        return create().readValue(json, valueType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(json: String, type: Type): T {
        val javaType = create().typeFactory.constructType(type)
        return create().readValue<T>(json, javaType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(src: Reader, valueType: Class<T>): T {
        return create().readValue(src, valueType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(src: Reader, type: Type): T {
        val javaType = create().typeFactory.constructType(type)
        return create().readValue(src, javaType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(src: InputStream, valueType: Class<T>): T {
        return create().readValue(src, valueType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(src: InputStream, type: Type): T {
        val javaType = create().typeFactory.constructType(type)
        return create().readValue(src, javaType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(src: ByteArray, valueType: Class<T>): T {
        return create().readValue(src, valueType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(src: ByteArray, type: Type): T {
        val javaType = create().typeFactory.constructType(type)
        return create().readValue<T>(src, javaType)
    }

    @Throws(IOException::class)
    fun <T> fromJson(src: DataInput, valueType: Class<T>): T {
        return create().readValue(src, valueType)
    }

    @Throws(IOException::class, JsonParseException::class, JsonMappingException::class)
    fun <T> fromJson(src: DataInput, type: Type): T {
        val javaType = create().typeFactory.constructType(type)
        return create().readValue<T>(src, javaType)
    }

    @Throws(JsonProcessingException::class)
    fun toJson(value: Any): String {
        return create().writeValueAsString(value)
    }
}