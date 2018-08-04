package com.zp.paradrop.eth

import java.io.IOException
import org.web3j.protocol.core.methods.response.EthSendTransaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import com.zp.paradrop.util.ZLog
import sun.text.normalizer.UnicodeMatcher.ETHER
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.response.EthGetBalance
import org.web3j.tx.FastRawTransactionManager
import com.zp.paradrop.GlobalConfig
import com.zp.paradrop.model.TxData
import org.web3j.protocol.http.HttpService
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.RemoteCall
import java.util.HashSet
import org.web3j.tx.TransactionManager
import org.web3j.utils.Convert


abstract class BaseTransfer(val ethWallet: ETHWallet) {
    companion object {
        const val TAG = "Transfer"
    }
    lateinit var web3j: Web3j

    protected var transactionManager: TransactionManager? = null
    var failedList: MutableSet<TxData>
    init {
        this.failedList = HashSet()
    }

    // 连接以太坊客户端
    @Throws(IOException::class)
    fun connectETHClient() {
        web3j = Web3j.build(HttpService(GlobalConfig.ETH_NET_NODE_ADDRESS))
        val web3ClientVersion = web3j.web3ClientVersion().send().web3ClientVersion
        ZLog.i(TAG, "已连接到ETH网络: web3j version=$web3ClientVersion")
        transactionManager = FastRawTransactionManager(web3j, ethWallet.credentials!!)
    }

    @Throws(IOException::class)
    fun getBalance(): String {
        val address = ethWallet.adress
        // 第二个参数：区块的参数，建议选最新区块
        val balance = web3j.ethGetBalance(address, DefaultBlockParameter.valueOf("latest")).send()
        // 格式转化 wei-ether
        val blanceETH = Convert.fromWei(balance.balance.toString(), Convert.Unit.ETHER).toPlainString() + " ether"
        ZLog.i(TAG, "ETH钱包($address)余额: $blanceETH")
        ethWallet.blance = blanceETH
        return blanceETH
    }

    fun addSendFailed(red: TxData) {
        this.failedList.add(red)
    }

    abstract fun sendFunds(red: TxData): RemoteCall<TransactionReceipt>

    @Throws(IOException::class)
    open fun sendTransaction(red: TxData): EthSendTransaction {
        return transactionManager!!.sendTransaction(red.toGasPrice(), red.toGasLimit(), red.toAddress, "", red.toNums().toBigInteger())
    }
}