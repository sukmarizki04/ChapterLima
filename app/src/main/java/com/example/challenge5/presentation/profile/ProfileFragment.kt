package com.example.challenge5.presentation.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.challenge5.R
import com.example.challenge5.data.AppDatabase
import com.example.challenge5.data.entity.AccountEntity
import com.example.challenge5.databinding.FragmentProfileBinding
import com.example.login.LoginViewModelFactory
import com.example.login.PreferenceKey

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var profileViewModel: ProfileViewModel
    lateinit var sharedPreferences: SharedPreferences
    val sharedPreferencesLogin = "sharedPreferencesLogin"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
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
        profileViewModel = ViewModelProvider(this, viewModelFactory)[ProfileViewModel::class.java]

        binding.btnProfileUpdate.setOnClickListener { toUpdateAccount() }
        binding.btnProfileLogout.setOnClickListener { toLogout() }
    }

    private fun toUpdateAccount() {
        val usernameProfile = binding.etProfileUsername.text.toString()
        val fullnameProfile = binding.etProfileFullName.text.toString()
        val address = binding.etProfileAddress.text.toString()

        profileViewModel.updateAccount(AccountEntity(userName = usernameProfile,
            fullName = fullnameProfile, address = address))
        Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
    }

    private fun toLogout() {
        val option = NavOptions.Builder()
            .setPopUpTo(R.id.profileFragment, true)
            .build()

        sharedPreferences.edit{
            this.putBoolean(PreferenceKey.PREF_USER_LOGIN_KEY.first, false)
        }

        findNavController().navigate(R.id.action_profileFragment_to_loginFragment, null, option)
    }
}