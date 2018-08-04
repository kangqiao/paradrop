package com.zp.paradrop.eth

import com.zp.paradrop.model.TxData
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.response.TransactionReceipt
import java.awt.Color.red
import org.web3j.tx.Transfer
import org.web3j.tx.Transfer.sendFunds



class ETHTransfer(wallet: ETHWallet): BaseTransfer(wallet){
    private val TAG = "ETHTransfer"
    override fun sendFunds(red: TxData): RemoteCall<TransactionReceipt> {
        //if(null == transactionManager)transactionManager = new FastRawTransactionManager(web3j, ethWallet.getCredentials());
        return Transfer(web3j, transactionManager).sendFunds(red.toAddress, red.toNums(), red.toUnit(), red.toGasPrice(), red.toGasLimit())
    }
}