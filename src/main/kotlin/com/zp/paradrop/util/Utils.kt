package com.zp.paradrop.util


import com.zp.paradrop.model.TxData
import java.util.Arrays
import java.util.Locale

import org.bouncycastle.jcajce.provider.digest.Keccak
import java.util.List
import kotlin.experimental.and


object Utils{

    fun isTrimEmpty(str: CharSequence?): Boolean {
        return null == str || str.toString().trim { it <= ' ' }.length == 0
    }

    fun notEmpty(str: CharSequence): Boolean {
        return !isTrimEmpty(str)
    }

    fun getMnemonicCodeLibsPathByLocale(): String {
//    	if(!new File(path).exists()) {
        //    		throw new RuntimeException("内部错误, 找不到指定的助记词库("+path+").");
        //    	}
        return "wordlibs/" + Locale("zh", "CN").toString() + ".txt"
    }

    fun toDouble(value: Any): Double? {
        if (value is Double) {
            return value
        } else if (value is Number) {
            return value.toDouble()
        } else if (value is CharSequence) {
            return java.lang.Double.valueOf(value.toString())
        }
        return null
    }

    fun toInteger(value: Any, def: Int?): Int? {
        if (value is Int) {
            return value
        } else if (value is Number) {
            return value.toInt()
        } else if (value is CharSequence) {
            return Integer.valueOf(value.toString())
        }
        return def
    }

    fun toInteger(value: Any): Int? {
        return toInteger(value, null)
    }

    fun toLong(value: Any): Long? {
        if (value is Long) {
            return value
        } else if (value is Number) {
            return value.toLong()
        } else if (value is CharSequence) {
            return java.lang.Long.valueOf(value.toString())
        }
        return null
    }

    fun notEmptyList(list: List<TxData>?): Boolean {
        return null != list && !list.isEmpty()
    }

    fun sha3(input: ByteArray, offset: Int, length: Int): ByteArray {
        val kecc = Keccak.Digest256()
        kecc.update(input, offset, length)
        return kecc.digest()
    }

    fun sha3(input: ByteArray): ByteArray {
        return sha3(input, 0, input.size)
    }

    fun sha3omit12(input: ByteArray): ByteArray {
        val hash = sha3(input)
        return Arrays.copyOfRange(hash, 12, hash.size)
    }

    fun toHexString(input: ByteArray, offset: Int, length: Int, withPrefix: Boolean): String {
        val stringBuilder = StringBuilder()
        if (withPrefix) {
            stringBuilder.append("0x")
        }
        for (i in offset until offset + length) {
            stringBuilder.append(String.format("%02x", input[i] and 0xFF.toByte()))
        }

        return stringBuilder.toString()
    }

    fun toHexString(input: ByteArray): String {
        return toHexString(input, 0, input.size, true)
    }
}