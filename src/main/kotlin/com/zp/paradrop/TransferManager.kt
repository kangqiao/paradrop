package com.zp.paradrop

import org.web3j.protocol.core.methods.response.TransactionReceipt
import com.zp.paradrop.util.ZLog
import java.io.IOException
import com.zp.paradrop.util.ConvertByJackson
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.core.JsonParseException
import com.zp.paradrop.eth.BaseTransfer
import com.zp.paradrop.eth.ETHTransfer
import com.zp.paradrop.eth.ETHWallet
import com.zp.paradrop.eth.TokenTransfer
import com.zp.paradrop.model.Config
import com.zp.paradrop.model.TxData
import com.zp.paradrop.util.Utils
import org.web3j.protocol.core.RemoteCall
import java.io.Reader
import java.util.concurrent.CountDownLatch


class TransferManager(val config: Config) {
    private val wallet: ETHWallet
    @Deprecated("")
    private val countDownLatch: CountDownLatch? = null

    @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
    constructor(json: String) : this(ConvertByJackson.fromJson(json, Config::class.java!!)) {
    }

    @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
    constructor(reader: Reader) : this(ConvertByJackson.fromJson(reader, Config::class.java)) {
    }

    init {
        this.wallet = ETHWallet(config.mnemonic)
        ZLog.setupLog(config.logLevel, config.logPath)
    }

    protected fun createTransfer(): BaseTransfer? {
        when (config.coin) {
            "ETH" -> return ETHTransfer(wallet)
            "MEX" -> return TokenTransfer(wallet, GlobalConfig.MEX_CONTRACT_ADDRESS)
            else -> if (Utils.notEmpty(config.contractAddress)) {
                return TokenTransfer(wallet, config.contractAddress)
            }
        }
        return null
    }

    fun sendRedEnvelope() {
        ZLog.e(TAG, "======>>>>>!!!开始发红包发啦!!!<<<<<======")
        if (!config.isAvail()) {
            ZLog.w(TAG, "配置有误, 请修改!!!")
        }
        try {
            val transfer = createTransfer()
            if (null == transfer) {
                ZLog.e(TAG, "当前不支持该币种:" + config.coin)
                return
            }
            ZLog.e(TAG, "======>>>>>" + config.coin + " (" + config.contractAddress + ")<<<<<======")
            transfer!!.connectETHClient()
            transfer!!.getBalance()
            //countDownLatch = new CountDownLatch(config.list.size());
            for (red in config.list!!) {
                red.prepare(config)
                if (red.check()) {
                    ZLog.it(TAG, "开始给 " + red.name + "(" + red.toAddress + ") 发送红包.")
                    //RemoteCall<TransactionReceipt> remoteSend = transfer.sendFunds(red);
                    //sendFundsAsyn(transfer, red, remoteSend);

                    val transaction = transfer!!.sendTransaction(red)

                    ZLog.it(TAG, "result: " + transaction.getTransactionHash()
                            + ", rawMessage:" + transaction.getRawResponse()
                            //+ ", error.code:" + transaction.getError().getCode()
                            //+ ", error.msg:" + transaction.getError().getMessage()
                    )
                } else {
                    //countDownLatch.countDown();
                    ZLog.w(TAG, red.name + "(" + red.toAddress + ") 检查失败, 未能发送")
                }
            }
            // 阻塞当前线程，直到倒数计数器倒数到0
            //countDownLatch.await();
            //			if (Utils.notEmpty(transfer.failedList)) {
            //				ZLog.e(TAG, "发送失败的红包:");
            //				for (RedEnvelope envelope : transfer.failedList) {
            //					ZLog.w(TAG, envelope.toString());
            //				}
            //			}
            //		} catch (InterruptedException e) {
            //			e.printStackTrace();
        } catch (e: IOException) {
            e.printStackTrace()
        }

        ZLog.e(TAG, "======>>>>>!!!红包发送结束了!!!<<<<<======")
    }

    private fun sendFundsAsyn(transfer: BaseTransfer, red: TxData, remoteSend: RemoteCall<TransactionReceipt>) {
        remoteSend.sendAsync()
        //countDownLatch.countDown();
        //		remoteSend.observable()
        //		//.subscribeOn(Schedulers.newThread())
        //		.subscribe(new Action1<TransactionReceipt>() {
        //			@Override
        //			public void call(TransactionReceipt send) {
        //				StringBuilder sb = new StringBuilder(red.name);
        //				if ("0x1".equals(send.getStatus())) {
        //					sb.append("的红包发送成功: ");
        //				} else {
        //					sb.append("的红包发送失败: ");
        //					transfer.addSendFailed(red);
        //				}
        //				sb.append("status: " + send.getStatus())
        //						.append(", trans hash=" + send.getTransactionHash())
        //						.append(", from :" + send.getFrom())
        //						.append(", to:" + send.getTo())
        //						.append(", gas used=" + send.getGasUsed());
        //				ZLog.it(TAG, sb.toString());
        //				countDownLatch.countDown();
        //			}
        //		}, new Action1<Throwable>() {
        //			@Override
        //			public void call(Throwable arg0) {
        //				ZLog.et(TAG, red.name + "的红包发送失败" + arg0.getMessage());
        //				transfer.addSendFailed(red);
        //				countDownLatch.countDown();
        //			}
        //		});
    }

    companion object {

        private val TAG = "TransferManager"
    }
}