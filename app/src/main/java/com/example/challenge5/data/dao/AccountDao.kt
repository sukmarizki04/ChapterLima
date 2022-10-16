package com.example.challenge5.data.dao

import androidx.room.*
import com.example.challenge5.data.entity.AccountEntity

@Dao
interface AccountDao {
    @Query("SELECT * FROM ACCOUNT_DATABASE WHERE USER_NAME == :userName")
    suspend fun readAccountByUsername(userName: String): AccountEntity?

    @Insert
    suspend fun insertAccount(account: AccountEntity)

    @Update
    suspend fun updateAccount(account: AccountEntity)

    @Delete
    suspend fun deleteAccount(account: AccountEntity)
}