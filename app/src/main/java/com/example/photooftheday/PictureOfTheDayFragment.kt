package com.example.photooftheday

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.photooftheday.databinding.MainFragmentBinding

import coil.load
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior


class PictureOfTheDayFragment : Fragment() {
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this).get(PictureOfTheDayViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getData().observe(
            viewLifecycleOwner,
            Observer<PictureOfTheDayData> { renderData(it) }
        )
        binding.inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data =
                    Uri.parse("https://en.wikipedis.org/wiki/${binding.inputEditText.text.toString()}")
            })
        }
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        setBottomAppBar(view)
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                    toast("Link is empty")
                } else {
                    //Отобразите фото
                    //showSuccess()
                    //Coil в работе: достаточно вызвать у нашего ImageView
                    //нужную extension-функцию и передать ссылку и заглушки для placeholder
                    binding.imageView.load(url){
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                }
            }
            is PictureOfTheDayData.Loading -> {
                toast("Loading picture")
            }
            is PictureOfTheDayData.Error -> {
                //showError(data.error.message)
                toast(data.error.message)
            }

        }

    }
    private fun toast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> Toast.makeText(context, "Favourite", Toast.LENGTH_SHORT).show()
            R.id.app_bar_settings -> activity?.supportFragmentManager?.beginTransaction()?.add(R.id.container, MainActivity.SettingsFragment())?.addToBackStack(null)?.commit()
            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(
            view.findViewById(R.id.bottom_app_bar)
        )
        setHasOptionsMenu(true)

        with(binding) {
            fab.setOnClickListener {
                if (isMain) {
                    isMain = false
                    bottomAppBar.navigationIcon = null
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                    fab.setImageDrawable(
                        ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    ))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                } else {
                    isMain = true
                    bottomAppBar.navigationIcon = ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_hamburger_menu_bottom_bar
                    )
                    bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                    fab.setImageDrawable(ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    ))
                    bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
                }
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}