package com.zp.paradrop

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.core.JsonParseException
import com.zp.paradrop.util.ZLog
import sun.jvm.hotspot.HelloWorld.e
import java.io.*
import java.nio.charset.Charset


fun main(vararg args: String){
    println("hello zhaopan")
}

const val TAG = "Main"

private fun sendRedEnvelopeFromCmd(args: Array<String>?) {
    var configPath = ""
    if (null == args || args.size <= 0) {
        ZLog.e(TAG, "请指定配置文件")
        return
    }
    configPath = args[0]
    var `in`: InputStream? = null
    try {
        `in` = FileInputStream(configPath)
        val reader = InputStreamReader(`in`, Charset.forName("UTF-8"))
        TransferManager(reader).sendRedEnvelope()
    } catch (e: FileNotFoundException) {
        ZLog.e(TAG, "找不到配置文件:$configPath")
        e.printStackTrace()
    } catch (e: IOException) {
        ZLog.e(TAG, "读取配置文件失败!")
        e.printStackTrace()
    } finally {
        try {
            `in`!!.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}

@Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
private fun sendRedEnveloperFromJson() {
    val json = ""
    TransferManager(json).sendRedEnvelope()
}