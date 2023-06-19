package com.xht.jetpack.room.stock

import android.text.TextUtils
import android.widget.TextView
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.xht.jetpack.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

/**
 * EthStockDBManager
 * @author Martin Hu
 * @email hy569835826@163.com
 * @data  2022/3/31 , 14:32
 * @description 码表数据库 DB 管理类
 * 如果码表字段有更新 , 则版本号也要对应升级
 */
@Database(entities = [StockInfo::class], version = 57, exportSchema = false)
abstract class EthStockDBManager : RoomDatabase(),
    CoroutineScope by CoroutineScope(Dispatchers.IO) {

    abstract fun stockInfoDao(): StockInfoDao?

    companion object {

        const val VERSION_MODULE_NAME = "local_stock_list_version"

        private var INSTANCE: EthStockDBManager? = null

        fun getInstance(): EthStockDBManager? {
            if (INSTANCE == null) {
                synchronized(EthStockDBManager::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            MyApplication.getAppContext(),
                            EthStockDBManager::class.java,
                            "stock.db"
                        )
                            .fallbackToDestructiveMigration()
                            .createFromAsset("databases/sunline.db")
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }

    /**
     * StockInfo 内存缓存
     */
    private val mTermsCache = ConcurrentHashMap<String, String>()

    /**
     * 根据股票代码获取本地码表记录
     *
     * @return
     */
    fun getStockName(assetid: String?): String? {
        if (TextUtils.isEmpty(assetid)) {
            return null
        }
        var result: String? = null
        mTermsCache[assetid]?.also {
            result = it //如果缓存不为空直接返回数据
        }
        if (result == null) {
            result = stockInfoDao()?.searchByAssetid(assetid)?.name()?.apply {
                mTermsCache[assetid!!] = this
            }
        }
        return result
    }


    /**
     * 根据股票代码获取本地码表记录
     *
     * @return
     */
    fun bindStockName(textView: TextView, assetid: String?) {
        val result = mTermsCache[assetid]
        textView.tag = assetid
        if (result.isNullOrEmpty()) {
            launch {
                val name = getStockName(assetid)
                launch(Dispatchers.Main) {
                    if (!name.isNullOrEmpty() && textView.tag == assetid)
                        textView.text = name
                }
            }
        } else {
            textView.text = result
        }

    }

    fun clearCache() {
        mTermsCache.clear()
    }


}