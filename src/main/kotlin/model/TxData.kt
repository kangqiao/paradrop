package com.zp.paradrop.model

import com.zp.paradrop.util.Utils
import org.web3j.utils.Convert
import java.math.BigInteger
import java.math.BigDecimal



data class TxData @JvmOverloads constructor(var name: String = "",
                                            var toAddress: String = "",
                                            var nums: Double = 0.0,
                                            var unit: String = "",
                                            var gasPrice: Long = 0L,
                                            var gasLimit: Long = 0L,
                                            var status: Int = 0){



    fun toNums(): BigDecimal {
        return BigDecimal.valueOf(nums)
    }

    fun toUnit(): Convert.Unit {
        return Convert.Unit.fromString(unit)
    }

    fun toGasPrice(): BigInteger {
        return BigInteger.valueOf(gasPrice)
    }

    fun toGasLimit(): BigInteger {
        return BigInteger.valueOf(gasLimit)
    }

    fun prepare(config: Config) {
        if (nums <= 0) nums = config.commonNums
        if (Utils.isTrimEmpty(unit)) unit = config.commonUnit
        if (gasPrice <= 0) gasPrice = config.commonGasPrice
        if (gasLimit <= 0) gasLimit = config.commonGasLimit
    }

    fun check(): Boolean {
        return if (Utils.isTrimEmpty(toAddress) || Utils.isTrimEmpty(unit) || nums <= 0
                || gasPrice <= 0 && gasLimit <= 0) {
            false
        } else true
    }

    override fun toString(): String {
        return "TxData(name='$name', toAddress='$toAddress', nums=$nums, unit='$unit', gasPrice=$gasPrice, gasLimit=$gasLimit, status=$status)"
    }
}