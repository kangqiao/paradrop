package com.zp.paradrop.eth

import com.zp.paradrop.model.TxData
import com.zp.paradrop.util.Utils
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.TypeReference
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.Type
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.utils.Convert
import java.io.IOException
import java.math.BigInteger
import java.util.*

class TokenTransfer(wallet: ETHWallet, // Token币合约地址.
                    private val CONTRACT: String) : BaseTransfer(wallet) {

    init {
        if (Utils.isTrimEmpty(CONTRACT)) {
            throw RuntimeException("合约地址不能为空")
        }
    }

    override fun sendFunds(red: TxData): RemoteCall<TransactionReceipt> {
        val token = ERC20Token(CONTRACT, web3j, transactionManager, red.toGasPrice(), red.toGasLimit())
        val nums = Convert.toWei(red.toNums(), red.toUnit())
        return token.transfer(red.toAddress, nums.toBigInteger())
    }

    @Throws(IOException::class)
    override fun sendTransaction(red: TxData): EthSendTransaction {
        val nums = Convert.toWei(red.toNums(), red.toUnit())
        val function = Function(
                "transfer",
                Arrays.asList(Address(red.toAddress),
                        Uint256(nums.toBigInteger())),
                emptyList())

        val data = FunctionEncoder.encode(function)
        //ZLog.d(TAG, "data:"+data+", nums:"+nums.toBigInteger());
        return transactionManager!!.sendTransaction(red.toGasPrice(), red.toGasLimit(), CONTRACT, data, BigInteger.ZERO)
    }

    companion object {
        private val TAG = "TokenTransfer"
    }

}