package com.dev_marinov.cryptocash.presentation.showCurrency

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dev_marinov.cryptocash.R
import com.dev_marinov.cryptocash.databinding.FragmentShowCurrencyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowCurrencyFragment : Fragment() {

    private val viewModel by viewModels<ShowCurrencyViewModel>()
    private lateinit var binding: FragmentShowCurrencyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_show_currency, container, false)

        return binding.root
    }

    override fun onStop() {
        super.onStop()
        viewModel.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animation()

        viewModel.rate.observe(viewLifecycleOwner) {
            binding.tvRate.text = it.toString()
        }

        viewModel.dateTime.observe(viewLifecycleOwner) {
            if (it.date == "null" && it.time == "null") {
                viewModel.startJob()
                binding.btNow.text = getString(R.string.now)
            } else {
                binding.btNow.text = it.date + " " + it.time
                viewModel.cancelJobSaveRate()

                viewModel.rateStore.observe(viewLifecycleOwner) { rate ->
                    rate?.let { rateValue ->
                        binding.tvRate.text = rateValue.usd.toString()
                    }
                }
            }
        }

        binding.btChooseDate.setOnClickListener {
            findNavController().navigate(R.id.action_showCurrencyFragment_to_bottomSheetFragment)
        }
    }

    private fun animation() {
        val animationDrawable = binding.btChooseDate.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(0)
        animationDrawable.setExitFadeDuration(2000)
        animationDrawable.start()
    }
}



