package com.example.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.challenge5.R
import com.example.challenge5.data.AppDatabase
import com.example.challenge5.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    lateinit var sharedPreferences: SharedPreferences
    val sharedPreferencesLogin = "sharedPreferencesLogin"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = requireContext().getSharedPreferences(sharedPreferencesLogin,
            Context.MODE_PRIVATE
        )

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).accountDatabaseDao()
        val viewModelFactory = LoginViewModelFactory(dataSource, application)
        loginViewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]

        binding.tvDontHaveAccount.setOnClickListener{ toRegistPage() }
        binding.btnLogin.setOnClickListener{ toLoggingIn() }

        autoConnect()
    }

    private fun toLoggingIn() {
        val usernameLogin = binding.etLoginUsername.text.toString()
        val passwordLogin = binding.etLoginPassword.text.toString()

        loginViewModel.readAccountById(usernameLogin).observe(viewLifecycleOwner){
            if (it.userName == usernameLogin && it.password == passwordLogin){
                Toast.makeText(requireContext(), "Login Success", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                sharedPreferences.edit {
                    this.putBoolean(PreferenceKey.PREF_USER_LOGIN_KEY.first, true)
                    this.putString(PreferenceKey.PREF_USER_NAME.first, usernameLogin)
                }
            }
            else{
                Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toRegistPage() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun autoConnect(){
        val connected = sharedPreferences.getBoolean(PreferenceKey.PREF_USER_LOGIN_KEY.first,
            PreferenceKey.PREF_USER_LOGIN_KEY.second)

        if (connected){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        else{

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

object PreferenceKey{
    val PREF_USER_LOGIN_KEY = Pair("PREF_LOGIN_APP_KEY", false)
    val PREF_USER_NAME = Pair("PREF_USER_NAME", false)
}