package com.example.challenge5.presentation.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge5.data.dao.AccountDao
import com.example.challenge5.data.entity.AccountEntity
import kotlinx.coroutines.launch

class RegisterViewModel (
    val database : AccountDao, application: Application) : AndroidViewModel(application) {
    fun insertAccount(account: AccountEntity) {
        viewModelScope.launch {
            getDataFromDatabase(account)
        }
    }

    private suspend fun getDataFromDatabase(account: AccountEntity) {
        database.insertAccount(account)
    }
}