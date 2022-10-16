package com.example.challenge5.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.challenge5.databinding.FragmentDetailBinding
import com.example.challenge5.models.Data
import com.example.challenge5.service.TheMovieDBApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMovieDetails()
    }

    private fun getMovieDetails() {
        val id = arguments?.getInt("ID")
        TheMovieDBApiService.instance.getDetailsMovies(id).enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                val code = response.code()
                    if(code == 200){
                        val body = response.body()
                        Log.d("name", body.toString())
                        val imageBase = "https://image.tmdb.org/t/p/w500/"
                        binding.tvDetailTitle.text = body?.title
                        binding.tvDetailReleaseDate.text = body?.releaseDate
                        binding.tvMovieDescription.text = body?.description
                        binding.tvDetailRating.text = body?.rating.toString()
                        Glide.with(requireContext()).load(imageBase + body?.backdropPath).into(binding.ivDetailBanner)
                        Glide.with(requireContext()).load(imageBase + body?.posterPath).into(binding.ivDetailMovie)
                    }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {

            }

        })
    }


}