package com.example.login

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.challenge5.data.dao.AccountDao
import com.example.challenge5.presentation.profile.ProfileViewModel
import com.example.challenge5.presentation.register.RegisterViewModel

class LoginViewModelFactory(
    private val dataSource: AccountDao,
    private val application: Application
) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)){
            return LoginViewModel(dataSource, application) as T
        }
        if (modelClass.isAssignableFrom(RegisterViewModel::class.java)){
            return RegisterViewModel(dataSource, application) as T
        }
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)){
            return ProfileViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}