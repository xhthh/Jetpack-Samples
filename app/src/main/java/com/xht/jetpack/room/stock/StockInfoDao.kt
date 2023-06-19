package com.xht.jetpack.room.stock

import androidx.room.*

@Dao
open interface StockInfoDao {

    /**
     * 获取所有股票数量
     */
    @Query("SELECT COUNT(*) FROM STOCK_INFO ")
    fun getStockSize(): Int?

    /**
     * 获取所有股票
     */
    @Query("SELECT * FROM STOCK_INFO ")
    fun getStockInfo(): List<StockInfo>?

    /**
     * 插入一只股票. 如果存在则替换
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveStock(stockInfo: StockInfo)

    /**
     * 插入集合 . 如果存在则替换
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveStocks(stockInfo: List<StockInfo>)

    /**
     * 删除一组股票
     */
    @Delete
    fun deleteStocks(stockInfo: List<StockInfo>): Int


    /**
     * 删除一组股票
     */
    @Query("DELETE FROM STOCK_INFO WHERE ASSET_ID in (:stocks) ")
    fun deleteStocksWithAssetID(stocks: List<String>?): Int

    /**
     * 通过 assetid 进行查询
     */
    @Query("SELECT * FROM STOCK_INFO WHERE ASSET_ID = :assetid")
    fun searchByAssetid(assetid: String?): StockInfo?


    /**
     * 通过 assetid 进行查询
     */
    @Query("SELECT ZH_NAME FROM STOCK_INFO WHERE ASSET_ID = :assetid")
    fun searchCNNameByAssetid(assetid: String?): String?

    /**
     * 通过 assetid 进行查询
     */
    @Query("SELECT ENG_NAME FROM STOCK_INFO WHERE ASSET_ID = :assetid")
    fun searchENNameByAssetid(assetid: String?): String?

    /**
     * 通过 股票代码精确搜索
     */
    @Query("SELECT * FROM STOCK_INFO WHERE CODE LIKE '%'||:code  ||'%' and CODE!= :code and E= 0 Limit 150")
    fun searchByLikeCode(code: String?): List<StockInfo>?

    /**
     * 通过 股票代码精确搜索
     */
    @Query("SELECT * FROM STOCK_INFO WHERE CODE = :code and E= 0")
    fun searchByCode(code: String?): List<StockInfo>?

    /**
     * 通过 股票名称全称搜索 进行模糊查询 , 限制 150 条
     */
    @Query(
        "SELECT * FROM STOCK_INFO WHERE substr(KWS,instr(KWS, '#')+1, length(KWS)) like '%'|| :condition ||'%' and E= 0" +
                "  and TYPE not in(90,91,92,100,101,102,105) Limit 150"
    )
    fun searchLocalByFullName(condition: String): List<StockInfo>?

    /**
     * 股票名称缩写模糊搜索
     */
    @Query(
        "SELECT * FROM STOCK_INFO WHERE substr(KWS,1,instr(KWS, '#')-1) like '%'|| :condition ||'%' and E= 0" +
                " and TYPE not in(90,91,92,100,101,102,105) Limit 150"
    )
    fun searchLocalBySCTypeLike(condition: String): List<StockInfo>?

    /**
     * 股票热门缩写精确搜索
     */
    @Query(
        "SELECT * FROM STOCK_INFO WHERE substr(SS,1,length(SS))  like '%'|| :condition  ||'%' and E= 0" +
                " and TYPE not in(90,91,92,100,101,102,105) Limit 150"
    )
    fun searchLocalByHotSs(condition: String): List<StockInfo>?

    /**
     * 股票名称缩写精确搜索
     */
    @Query("SELECT * FROM STOCK_INFO WHERE substr(KWS,1,instr(KWS, '#')-1) =:condition and E= 0")
    fun searchLocalBySC(condition: String): List<StockInfo>?


    /**
     * 股票英文精确搜索
     */
    @Query(
        "SELECT * FROM STOCK_INFO WHERE ENG_NAME = :condition  ||'%' and E= 0" +
                " and TYPE not in(90,91,92,100,101,102,105) Limit 150"
    )
    fun searchLocalByENG_NAME(condition: String): List<StockInfo>?

    /**
     * 股票英文模糊搜索
     */
    @Query(
        "SELECT * FROM STOCK_INFO WHERE ENG_NAME like '%'|| :condition ||'%' and E= 0" +
                " and TYPE not in(90,91,92,100,101,102,105) Limit 150"
    )
    fun searchLocalLikeENG_NAME(condition: String): List<StockInfo>?

    /**
     * 中文模糊搜索
     */
    @Query(
        "SELECT * FROM STOCK_INFO WHERE ZH_NAME  like '%'|| :condition ||'%' and E= 0" +
                " and TYPE not in(90,91,92,100,101,102,105) Limit 150"
    )
    fun searchLocalByZN(condition: String): List<StockInfo>?
}