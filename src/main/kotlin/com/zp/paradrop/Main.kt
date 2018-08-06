package com.zp.paradrop

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.core.JsonParseException
import com.zp.paradrop.excel.ExcelLoader
import com.zp.paradrop.util.ZLog
import java.io.*
import java.nio.charset.Charset
import org.slf4j.LoggerFactory

const val TAG = "Main"
//定义日志对象
private val log = LoggerFactory.getLogger(TAG)



fun main(args: Array<String>){
    //级别为debug的日志
    log.debug("Hello! debug!")
    //级别为info的日志
    log.info("Hello! info!")
    //级别为warn的日志
    log.warn("Hello! warn!")
    //级别为error的日志
    log.error("Hello! error!")
    //sendRedEnvelopeFromCmd(args)
    var configPath = args[0]
    var url = ExcelLoader::class.java.getResource("")
    url = ClassLoader.getSystemResource("")
    val path = File("/Users/zhaopan/workspace/kotlin/paradrop/paradrop.xlsx")
    log.error(""+path.exists())
    ExcelLoader.read(path.canonicalPath)
}

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