package com.zp.paradrop.model

import com.zp.paradrop.util.Utils
import java.math.BigInteger

import java.util.List


data class Config @JvmOverloads constructor(var coin: String = "",
                                            var contractAddress: String = "",
                                            var mnemonic: String = "",
                                            var commonNums: Double = 0.0,
                                            var commonUnit: String = "gwei",
                                            var commonGasPrice: Long = 0L,
                                            var commonGasLimit: Long = 21000,
                                            var list: List<TxData>? = null,
                                            var logLevel: String = "debug",
                                            var logPath: String = "red_log.log") {

    fun isAvail(): Boolean {
        return Utils.notEmpty(coin) && Utils.notEmpty(mnemonic) && Utils.notEmptyList(list)
    }

    fun toGasPrice(): BigInteger {
        return BigInteger.valueOf(commonGasPrice)
    }

    fun toGasLimit(): BigInteger {
        return BigInteger.valueOf(commonGasLimit)
    }
}