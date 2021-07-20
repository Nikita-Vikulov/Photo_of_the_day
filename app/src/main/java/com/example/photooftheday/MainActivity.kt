package com.example.photooftheday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.photooftheday.databinding.FragmentSettingsBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }

    class SettingsFragment : Fragment() {
        private var _binding: FragmentSettingsBinding? = null
        private val binding get() = _binding!!
        private lateinit var changeThemeBtn: Button

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = FragmentSettingsBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

           /* binding.changeThemeBtn.setOnCheckedChangeListener  { buttonView, isChecked ->
                if (isChecked) {
                    ThemeHolder.theme = R.style.PhotoOfTheDay
                    requireActivity().recreate()
                } else {
                    ThemeHolder.theme = R.style.AppTheme
                    requireActivity().recreate()
                }

            }*/

            binding.changeThemeBtn.setOnClickListener {
                ThemeHolder.theme = if (true) {
                    R.style.PhotoOfTheDay
                } else {
                    R.style.AppTheme
                }
                requireActivity().recreate()
            }
        }
    }
}

object ThemeHolder {
    @StyleRes
    var theme: Int = R.style.AppTheme
}
