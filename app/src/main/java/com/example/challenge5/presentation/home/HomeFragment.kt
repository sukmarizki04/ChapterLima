package com.example.challenge5.presentation.home

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge5.R
import com.example.challenge5.databinding.FragmentHomeBinding
import com.example.challenge5.models.Data
import com.example.challenge5.models.MovieResponse
import com.example.challenge5.service.TheMovieDBApiService
import com.example.login.PreferenceKey
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    lateinit var sharedPreferences: SharedPreferences
    val sharedPreferencesLogin = "sharedPreferencesLogin"
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedPreferences = requireContext().getSharedPreferences(sharedPreferencesLogin,
            Context.MODE_PRIVATE
        )

        recyclerView = binding.rvMovieList

        binding.tvWelcome.text = "Welcome, "+ sharedPreferences.getString(PreferenceKey.PREF_USER_NAME.first, "")

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        getMovieData { movies : List<Data> ->
            recyclerView.adapter = HomeAdapter(movies) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_profile -> {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getMovieData(callback: (List<Data>) -> Unit){
        TheMovieDBApiService.instance.getPopularMovies().enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

        })
    }
}