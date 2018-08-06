package com.zp.paradrop.excel

import java.io.IOException
import java.io.InputStream
import java.io.PushbackInputStream

import org.apache.poi.EmptyFileException
import org.apache.poi.openxml4j.exceptions.InvalidFormatException
import org.apache.poi.poifs.filesystem.DocumentFactoryHelper
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem
import org.apache.poi.util.IOUtils

import com.alibaba.excel.ExcelReader
import com.alibaba.excel.read.event.AnalysisEventListener
import com.alibaba.excel.support.ExcelTypeEnum

object ExcelReaderFactory {
    /**
     * @param in
     * 文件输入流
     * @param customContent
     * 自定义模型可以在
     * [AnalysisEventListener.invoke]
     * AnalysisContext中获取用于监听者回调使用
     * @param eventListener
     * 用户监听
     * @throws IOException
     * @throws EmptyFileException
     * @throws InvalidFormatException
     */
    @Throws(EmptyFileException::class, IOException::class, InvalidFormatException::class)
    fun getExcelReader(ins: InputStream, customContent: Any?,
                       eventListener: AnalysisEventListener<*>): ExcelReader {
        var instream = ins
        // 如果输入流不支持mark/reset，需要对其进行包裹
        if (!instream.markSupported()) {
            instream = PushbackInputStream(instream, 8)
        }

        // 确保至少有一些数据
        val header8 = IOUtils.peekFirst8Bytes(instream)
        var excelTypeEnum: ExcelTypeEnum? = null
        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
            excelTypeEnum = ExcelTypeEnum.XLS
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(instream)) {
            excelTypeEnum = ExcelTypeEnum.XLSX
        }
        if (excelTypeEnum != null) {
            return ExcelReader(instream, excelTypeEnum, customContent, eventListener)
        }
        throw InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream")

    }

    /**
     * @param `instream`
     * 文件输入流
     * @param customContent
     * 自定义模型可以在
     * [AnalysisEventListener.invoke]
     * AnalysisContext中获取用于监听者回调使用
     * @param eventListener
     * 用户监听
     * @param trim
     * 是否对解析的String做trim()默认true,用于防止 excel中空格引起的装换报错。
     * @throws IOException
     * @throws EmptyFileException
     * @throws InvalidFormatException
     */
    @Throws(EmptyFileException::class, IOException::class, InvalidFormatException::class)
    fun getExcelReader(ins : InputStream, customContent: Any?,
                       eventListener: AnalysisEventListener<*>, trim: Boolean): ExcelReader {
        var instream = ins
        // 如果输入流不支持mark/reset，需要对其进行包裹
        if (!instream.markSupported()) {
            instream = PushbackInputStream(instream, 8)
        }

        // 确保至少有一些数据
        val header8 = IOUtils.peekFirst8Bytes(instream)
        var excelTypeEnum: ExcelTypeEnum? = null
        if (NPOIFSFileSystem.hasPOIFSHeader(header8)) {
            excelTypeEnum = ExcelTypeEnum.XLS
        }
        if (DocumentFactoryHelper.hasOOXMLHeader(instream)) {
            excelTypeEnum = ExcelTypeEnum.XLSX
        }
        if (excelTypeEnum != null) {
            return ExcelReader(instream, excelTypeEnum, customContent, eventListener, trim)
        }
        throw InvalidFormatException("Your InputStream was neither an OLE2 stream, nor an OOXML stream")
    }
}