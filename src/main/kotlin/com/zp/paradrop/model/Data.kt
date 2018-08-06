package com.zp.paradrop.model

import com.alibaba.excel.annotation.ExcelProperty
import com.alibaba.excel.metadata.BaseRowModel
import java.io.Serializable
import java.math.BigInteger

//account	symbol	contractAddress	mnemonic	commonNum	unit	maxGasPrice	minGasLimit	record
data class Account(
        @ExcelProperty(value = arrayOf("钱包名"), index = 0) val name: String = "",
        @ExcelProperty(value = arrayOf("币种"), index = 1) val symbol: String = "",
        @ExcelProperty(value = arrayOf("合约地址"), index = 2) val contractAddress: String = "",
        @ExcelProperty(value = arrayOf("助记词"), index = 3) val mnemonic: String = "",
        @ExcelProperty(value = arrayOf("燃料费"), index = 4) val gasPrice: Long = 2000000000,
        @ExcelProperty(value = arrayOf("燃料限制"), index = 5) val gasLimit: Long = 21000,
        @ExcelProperty(value = arrayOf("发币数量"), index = 6) val commonNum: Long = 10,
        @ExcelProperty(value = arrayOf("发币单位"), index = 7) val unit: String = "ether",
        @ExcelProperty(value = arrayOf("记录Sheet"), index = 8) val record: String = name
) : BaseRowModel(), Serializable {


}

//名称	收币地址	合约地址	发币数量	发币单位	燃料费	燃料限制	随机数	交易ID
data class Record(
        @ExcelProperty(value = "名称", index = 0) var name: String,
        @ExcelProperty(value = "收币地址", index = 1) var recipient: String,
        @ExcelProperty(value = "合约地址", index = 2) var contract: String,
        @ExcelProperty(value = "发币数量", index = 3) var value: Long,
        @ExcelProperty(value = "发币单位", index = 4) var unit: String,
        @ExcelProperty(value = "燃料费", index = 5) var gasPrice: Long,
        @ExcelProperty(value = "燃料限制", index = 6) var gasLimit: Long,
        @ExcelProperty(value = "随机数", index = 7) var nonce: Long,
        @ExcelProperty(value = "交易ID", index = 8) var txId: String
) : BaseRowModel(), Serializable {

}