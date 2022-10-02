package com.dev_marinov.cryptocash.presentation.bottomSheet

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.dev_marinov.cryptocash.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.dev_marinov.cryptocash.databinding.FragmentBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {

    private val viewModel: BottomSheetViewModel by viewModels()
    private lateinit var binding: FragmentBottomSheetBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("BottomSheetFragment", "333 загрузился")
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_sheet, container, false)

//        val currentDay = SimpleDateFormat("dd",Locale.getDefault()).format(Date())
//        Log.d("curday", " загрузился 33333333333===" + currentDay)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar: Calendar = Calendar.getInstance()

        binding.tvDate.setOnClickListener { getDate(calendar) }
        binding.tvTime.setOnClickListener { getTime(calendar) }

        // показывает СИНЮЮ ИЛИ ГОЛУБУЮ КНОПКУ при заполнении, но не сохранении
        viewModel.buttonOkBottomSheet.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.tvOk.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue))
                    binding.tvOk.isEnabled = true
                }
                false -> {
                    binding.tvOk.setTextColor(ContextCompat.getColor(requireContext(), R.color.blue_light))
                    binding.tvOk.isEnabled = false
                }
            }
        }

        viewModel.buttonDeleteBottomSheet.observe(viewLifecycleOwner) {
            when (it) {
                true -> {
                    binding.tvDelete.visibility = View.VISIBLE
                    binding.tvCancel.visibility = View.GONE
                    viewModel.setButtonOkBottomSheet()
                }
                false -> {
                    binding.tvDelete.visibility = View.GONE
                    binding.tvCancel.visibility = View.VISIBLE
                    viewModel.setButtonOkBottomSheet()
                }
            }
        }

        binding.tvOk.setOnClickListener {
            viewModel.saveData()
            viewModel.retrieveData()
//                viewModel.deleteDateTime()
            // здесь должна происиходить запись в базу данных
            transitionToShowCurrencyFragment()
        }
        binding.tvDelete.setOnClickListener {
            viewModel.deleteDateTimeDataStore()
            viewModel.deleteDataTimeLiveData()
            viewModel.setButtonOkBottomSheet()
            viewModel.setButtonDeleteBottomSheet()

            binding.tvDelete.visibility = View.GONE
            binding.tvCancel.visibility = View.VISIBLE
        }
        binding.tvCancel.setOnClickListener {
            transitionToShowCurrencyFragment()
        }

//        // УСТАНОВКА ДАТЫ И ВРЕМЯ В БОТОМЩИТ
//        viewModel.datetime.asLiveData().observe(viewLifecycleOwner) { Date ->
//            Date?.let {
//                if (it.datePicker == "null" && it.timePicker == "null") {
//                    binding.tvDate.text = getString(R.string.choose_date)
//                    binding.tvTime.text = getString(R.string.choose_time)
//                    binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
//                    binding.tvTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
//                } else {
//                    binding.tvDate.text = it.datePicker
//                    binding.tvTime.text = it.timePicker
//                    binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//                    binding.tvTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
//
//                    binding.tvDelete.visibility = View.VISIBLE
//                    binding.tvCancel.visibility = View.GONE
//                }
//            }
//        }
/////////////////////////////////////////////
        viewModel.date.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.tvDate.text = getString(R.string.choose_date)
                binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
                Log.d("333", " viewModel.date.observe NULL=" + it)
            } else {
                binding.tvDate.text = it
                binding.tvDate.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                Log.d("333", " viewModel.date.observe NOT NULL=" + it)
            }
        }

        viewModel.time.observe(viewLifecycleOwner) {
            if (it == null) {
                binding.tvTime.text = getString(R.string.choose_time)
                binding.tvTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
            }
            else {
                binding.tvTime.text = it
                binding.tvTime.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                Log.d("333", " загр it time live =" + it)
            }
        }
        ////////////////////////////////////////////////////////


        // ВСЕ НИЖНИЕ ВЬЮШКИ
        viewModel.curDateName.observe(viewLifecycleOwner) {
            binding.tvNowValue.text = it
            binding.tvPrevWeekValue.text = it
        }
        viewModel.prevDateName.observe(viewLifecycleOwner) { binding.tvYesterdayValue.text = it }
        viewModel.curDateTime.observe(viewLifecycleOwner) { binding.tvCurValue.text = it }
        //////////////////////////////////////

        viewModel.uploadedData.observe(viewLifecycleOwner) {
            val toast = Toast.makeText(
                requireContext(), R.string.copied_to_clipboard,
                Toast.LENGTH_SHORT
            )
            toast.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, 0, 30)
            toast.show()
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(calendar: Calendar) {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            android.R.style.Theme_DeviceDefault_Dialog, { datePicker, year, month, day ->
                //           Log.d("it", " загрузился 33333333333===" + year + " " + month + " " + day)
                viewModel.onDateSelected(year = year, month = month, day = day)
                //viewModel.retrieveData()
            }, year, month, day)
        datePickerDialog.show()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getTime(calendar: Calendar) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(
                requireContext(),
                android.R.style.Theme_Material_Dialog_Alert, { timePicker, hour, minute ->
                    viewModel.onTimeSelected(hour = hour, minute = minute)
                    //viewModel.retrieveData()
                }, hour, minute, true)
        timePickerDialog.show()

    }

    private fun transitionToShowCurrencyFragment() {
        findNavController().popBackStack()
//        findNavController().navigate(R.id.action_bottomSheetFragment_to_showCurrencyFragment)
    }
}