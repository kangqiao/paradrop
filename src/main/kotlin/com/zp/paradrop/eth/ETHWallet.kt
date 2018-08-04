package com.zp.paradrop.eth

import org.bitcoinj.crypto.MnemonicException.MnemonicChecksumException
import org.bitcoinj.crypto.MnemonicException.MnemonicWordException
import org.bitcoinj.crypto.MnemonicException.MnemonicLengthException
import java.io.IOException
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.MnemonicCode
import com.zp.paradrop.MyMnemonicCode
import com.zp.paradrop.util.Utils
import java.util.Arrays

import org.bitcoinj.core.ECKey
import org.web3j.crypto.Credentials



class ETHWallet(mnemonicCodeStr: String) {

    var credentials: Credentials? = null
        private set
    var adress: String? = null
        private set
    var blance: String? = null

    val isReady: Boolean
        get() = credentials != null && !Utils.isTrimEmpty(adress)

    init {
        if (Utils.isTrimEmpty(mnemonicCodeStr)) {
            throw RuntimeException("助记词不能为空")
        }
        loadWallet(mnemonicCodeStr)
    }

    private fun loadWallet(mnemonicCodeStr: String) {
        try {
            val path = Utils.getMnemonicCodeLibsPathByLocale()

            // 通过指定的中文助记词库解析助记词, 得到在中文库中的seed数据.
            val resourceStream = ETHWallet::class.java!!.getClassLoader().getResourceAsStream(path)
            val mc = MyMnemonicCode(resourceStream, null)

            //path = "bin/wordlibs/"+new Locale("zh", "CN").toString() + ".txt";
            //MyMnemonicCode mc = new MyMnemonicCode(new FileInputStream(path), null); //本地调试用

            //val wordList = Arrays.asList(mnemonicCodeStr.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())
            val wordList = mnemonicCodeStr.split(" ")
            var hd_seed = mc.toEntropy(wordList)
            // 通过seed数据解出英文环境下的助记词, 进而转换为英文环境下的seed数据.
            val enWordList = MnemonicCode().toMnemonic(hd_seed)
            hd_seed = org.bitcoinj.crypto.MnemonicCode.toSeed(enWordList, "")
            // 生成 m/44'/60'/a'/0/n/ ETH钱包的私钥.
            val masterKey = HDKeyDerivation.createMasterPrivateKey(hd_seed)
            val purposeKey = HDKeyDerivation.deriveChildKey(masterKey, 44 or ChildNumber.HARDENED_BIT)
            val coinKey = HDKeyDerivation.deriveChildKey(purposeKey, 60 or ChildNumber.HARDENED_BIT)
            val accountKey = HDKeyDerivation.deriveChildKey(coinKey, 0 or ChildNumber.HARDENED_BIT)
            val receiveKey = HDKeyDerivation.deriveChildKey(accountKey, 0)
            val addressKey = HDKeyDerivation.deriveChildKey(receiveKey, 0)

            // 生成ETH钱包确定性私钥得到私钥证书和收发币地址.
            val addressECKey = ECKey.fromPrivate(addressKey.privKeyBytes)
            credentials = Credentials.create(addressECKey.getPrivateKeyAsHex())
            val encodeBytes = addressECKey.getPubKeyPoint().getEncoded(false)
            val pubBytes = Arrays.copyOfRange(encodeBytes, 1, encodeBytes.size)
            adress = Utils.toHexString(Utils.sha3omit12(pubBytes))
            //ZLog.i(TAG, "ETH发币地址: " + address);
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: MnemonicLengthException) {
            e.printStackTrace()
        } catch (e: MnemonicWordException) {
            e.printStackTrace()
        } catch (e: MnemonicChecksumException) {
            e.printStackTrace()
        }

    }

    companion object {
        private val TAG = "ETHWallet"
    }


}