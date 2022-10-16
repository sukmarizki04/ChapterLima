package com.example.challenge5.presentation.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge5.data.dao.AccountDao
import com.example.challenge5.data.entity.AccountEntity
import kotlinx.coroutines.launch

class ProfileViewModel (
    val database : AccountDao, application: Application) : AndroidViewModel(application) {
    fun updateAccount(account: AccountEntity) {
        viewModelScope.launch {
            getDataFromDatabase(account)
        }
    }

    private suspend fun getDataFromDatabase(account: AccountEntity) {
        database.updateAccount(account)
    }
}