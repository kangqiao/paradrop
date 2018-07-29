package com.zp.paradrop.util

import java.io.IOException
import java.io.FileWriter
import java.io.PrintWriter
import java.io.File



object ZLog{
    private val LOG_LOCAL_E = true
    private var LOG_LOCAL_W = true
    private var LOG_LOCAL_I = true
    private var LOG_LOCAL_D = true

    private val PREFIX = "zp:::"
    private fun THREAD(): String {
        return Thread.currentThread().toString() + ":" + Thread.currentThread().id + ":"
    }

    fun e(TAG: String, msg: String) {
        if (LOG_LOCAL_E) Log.e(TAG, PREFIX + msg)
    }

    fun e(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_E) Log.e(TAG, PREFIX + msg, tr)
    }

    fun et(TAG: String, msg: String) {
        if (LOG_LOCAL_E) Log.et(TAG, PREFIX + THREAD() + msg)
    }

    fun et(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_E) Log.et(TAG, PREFIX + THREAD() + msg, tr)
    }

    fun w(TAG: String, msg: String) {
        if (LOG_LOCAL_W) Log.w(TAG, PREFIX + msg)
    }

    fun w(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_W) Log.w(TAG, PREFIX + msg, tr)
    }

    fun wt(TAG: String, msg: String) {
        if (LOG_LOCAL_W) Log.wt(TAG, PREFIX + THREAD() + msg)
    }

    fun wt(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_W) Log.wt(TAG, PREFIX + THREAD() + msg, tr)
    }

    fun i(TAG: String, msg: String) {
        if (LOG_LOCAL_I) Log.i(TAG, PREFIX + msg)
    }

    fun i(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_I) Log.i(TAG, PREFIX + msg, tr)
    }

    fun it(TAG: String, msg: String) {
        if (LOG_LOCAL_I) Log.it(TAG, PREFIX + THREAD() + msg)
    }

    fun it(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_I) Log.it(TAG, PREFIX + THREAD() + msg, tr)
    }

    fun d(TAG: String, msg: String) {
        if (LOG_LOCAL_D) Log.d(TAG, PREFIX + msg)
    }

    fun d(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_D) Log.d(TAG, PREFIX + msg, tr)
    }

    fun dt(TAG: String, msg: String) {
        if (LOG_LOCAL_D) Log.dt(TAG, PREFIX + THREAD() + msg)
    }

    fun dt(TAG: String, msg: String, tr: Throwable) {
        if (LOG_LOCAL_D) Log.dt(TAG, PREFIX + THREAD() + msg, tr)
    }

    fun setupLog(logLevel: String, logPath: String) {
        when (logLevel) {
            "error" -> {
                LOG_LOCAL_W = false
                LOG_LOCAL_I = false
                LOG_LOCAL_D = false
            }
            "warn" -> {
                LOG_LOCAL_I = false
                LOG_LOCAL_D = false
            }
            "info" -> LOG_LOCAL_D = false
        }
        Log.logPath = logPath
    }

    private object Log {
        var logPath = "./red_log.log"
        private val logCache = StringBuffer()
        private val now = java.util.Date()
        private val format = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        private val dateTimeStr: String
            get() = format.format(System.currentTimeMillis())

        @JvmOverloads
        fun log(TAG: String, msg: String, tr: Throwable? = null) {
            val content = dateTimeStr + "\t" + TAG + "\t" + msg
            println(content)
            exportLog(content)
            if (null != tr) {
                println(tr.message)
                exportLog(tr.message?: "")
            }
        }

        fun exportLog(msg: String) {
            var pw: PrintWriter? = null
            try {
                if (Utils.isTrimEmpty(logPath)) return
                val logfile = File(logPath) ?: return
                if (!logfile.exists()) {
                    /*File parentDir = new File(logfile.getParent());
	                 if (!parentDir.exists()) {
	                     parentDir.mkdirs();  //如果所在目录不存在,则新建.
	                 }*/
                    logfile.createNewFile()
                }
                pw = PrintWriter(FileWriter(logfile, true), true)
                pw.println(msg)
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                pw?.close()
            }
        }

        fun e(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun e(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }

        fun et(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun et(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }

        fun w(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun w(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }

        fun wt(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun wt(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }

        fun i(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun i(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }

        fun it(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun it(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }

        fun d(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun d(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }

        fun dt(TAG: String, msg: String) {
            log(TAG, msg)
        }

        fun dt(TAG: String, msg: String, tr: Throwable) {
            log(TAG, msg, tr)
        }
    }
}