package com.example.photooftheday

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photooftheday.api.EarthFragment
import com.example.photooftheday.api.MarsFragment
import com.example.photooftheday.api.WeatherFragment
import com.example.photooftheday.databinding.ActivityApiBottomBinding


class ApiBottomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityApiBottomBinding
   // private var _binding: ActivityApiBottomBinding? = null
   // private val binding get() = _binding!!

  /*  override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityApiBottomBinding.inflate(inflater, container, false)
        return binding.root
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiBottomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, MarsFragment())
                        .commitAllowingStateLoss()
                    true
                }
                R.id.bottom_view_weather -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, WeatherFragment())
                        .commitAllowingStateLoss()
                    true
                }
                else -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.activity_api_bottom_container, EarthFragment())
                        .commitAllowingStateLoss()
                    true
                }
            }
        }
        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_earth
        binding.bottomNavigationView.getOrCreateBadge(R.id.bottom_view_earth)
        val badge = binding.bottomNavigationView.getBadge(R.id.bottom_view_earth)
        badge?.maxCharacterCount = 2
        badge?.number = 999

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item tapped
                }
                R.id.bottom_view_mars -> {
                    //Item tapped
                }
                R.id.bottom_view_weather -> {
                    //Item tapped
                }
            }
        }
    }
}
