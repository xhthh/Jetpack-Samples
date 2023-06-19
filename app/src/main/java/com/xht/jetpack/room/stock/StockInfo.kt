package com.xht.jetpack.room.stock

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity(tableName = "STOCK_INFO")
class StockInfo {

    /**
     * 股票代码 + 市场代码
     */
    @PrimaryKey
    @ColumnInfo(name = "ASSET_ID")
    var id: String = ""

    /**
     * 股票代码
     */
    @ColumnInfo(name = "CODE")
    var c: String? = ""

    /**
     * 市场,SH：0；other：1
     */
    @ColumnInfo(name = "MID")
    var mid: Int? = 0

    /**
     * 中文名
     */
    @Deprecated("该参数不会删除 , 但是请切换使用 name 参数进行替换 ")
    @ColumnInfo(name = "ZH_NAME")
    var zh: String? = null

    /**
     * 是否可投资 标记
     */
    @ColumnInfo(name = "I")
    var i: Int? = 0

    /**
     * 是否过期
     */
    @ColumnInfo(name = "E")
    var e: Int? = 0

    /**
     * 索引
     */
    @ColumnInfo(name = "KWS")
    var kws: String? = ""

    /**
     * 是否上市
     */
    @ColumnInfo(name = "L")
    var l: Int? = 0

    /**
     * 上市时间
     */
    @ColumnInfo(name = "LTS")
    var lts: Long? = 0

    /**
     * 是否停牌
     */
    @ColumnInfo(name = "S")
    var s: Int? = 0

    /**
     * 股票类型
     */
    @ColumnInfo(name = "TYPE")
    var t: Int? = 0

    /**
     * 拼音简称
     */
    @ColumnInfo(name = "SS")
    var ss: String? = null

    /**
     * 英文名
     */
    @Deprecated("该参数不会删除 , 但是请切换使用 name 参数进行替换 ")
    @ColumnInfo(name = "ENG_NAME")
    var en: String? = null


    override fun toString(): String {
        return "StockInfo(简体=$zh, 拼音=$ss, 英文=$en)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StockInfo

        if (id != other.id) return false
        if (c != other.c) return false
        if (mid != other.mid) return false
        if (zh != other.zh) return false
        if (i != other.i) return false
        if (e != other.e) return false
        if (kws != other.kws) return false
        if (l != other.l) return false
        if (lts != other.lts) return false
        if (s != other.s) return false
        if (t != other.t) return false
        if (ss != other.ss) return false
        if (en != other.en) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (c?.hashCode() ?: 0)
        result = 31 * result + (mid ?: 0)
        result = 31 * result + (zh?.hashCode() ?: 0)
        result = 31 * result + (i ?: 0)
        result = 31 * result + (e ?: 0)
        result = 31 * result + (kws?.hashCode() ?: 0)
        result = 31 * result + (l ?: 0)
        result = 31 * result + (lts?.hashCode() ?: 0)
        result = 31 * result + (s ?: 0)
        result = 31 * result + (t ?: 0)
        result = 31 * result + (ss?.hashCode() ?: 0)
        result = 31 * result + (en?.hashCode() ?: 0)
        return result
    }
}

/**
 * 直接根据码表查找对应的语言版本
 */
fun StockInfo.name(): String? {
    return zh;
}