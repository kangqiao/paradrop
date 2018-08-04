package com.zp.paradrop

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.core.JsonParseException
import com.zp.paradrop.util.ZLog
import java.io.*
import java.nio.charset.Charset


fun main(args: Array<String>){
    println("hello zhaopan")
    sendRedEnvelopeFromCmd(args)
}

const val TAG = "Main"

private fun sendRedEnvelopeFromCmd(args: Array<String>?) {
    if (null == args || args.size <= 0) {
        ZLog.e(TAG, "请指定配置文件")
        return
    }
    var configPath = args[0]
    var instream: InputStream? = null
    try {
        instream = FileInputStream(configPath)
        val reader = InputStreamReader(instream, Charset.forName("UTF-8"))
        TransferManager(reader).sendRedEnvelope()
    } catch (e: FileNotFoundException) {
        ZLog.e(TAG, "找不到配置文件:$configPath")
        e.printStackTrace()
    } catch (e: IOException) {
        ZLog.e(TAG, "读取配置文件失败!")
        e.printStackTrace()
    } finally {
        try {
            instream!!.close()
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