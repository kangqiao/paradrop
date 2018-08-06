package com.zp.paradrop.excel

import com.alibaba.excel.metadata.Sheet
import com.alibaba.excel.read.context.AnalysisContext
import com.alibaba.excel.read.event.AnalysisEventListener
import com.zp.paradrop.model.Account
import java.io.FileInputStream



object ExcelLoader{
    @Throws(Exception::class)
    fun read(xlsFilePath: String) {
        FileInputStream(xlsFilePath).use { inputStream ->
            val listener = object : AnalysisEventListener<Account>() {

                override fun invoke(obj: Account, context: AnalysisContext) {
                    System.err.println("Row:" + context.currentRowNum + " Data:" + obj)
                }

                override fun doAfterAllAnalysed(context: AnalysisContext) {
                    System.err.println("doAfterAllAnalysed...")
                }
            }
            val excelReader = ExcelReaderFactory.getExcelReader(inputStream, null, listener)
            // 第二个参数为表头行数，按照实际设置
            excelReader.read(Sheet(1, 1, Account::class.java, "account", null))
        }
    }
}


fun main(vararg args: String){
    ExcelLoader.read("paraback.xlsx")
}
