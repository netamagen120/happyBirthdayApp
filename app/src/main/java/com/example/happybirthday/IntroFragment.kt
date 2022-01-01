package com.example.happybirthday

import android.app.Activity
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.intro_layout.*

class IntroFragment : Fragment() {
    private val viewModel: BirthdayViewModel by activityViewModels()
    private val galleryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val uri = data?.data
            uri?.let { babyIcon.setImageURI(it) }
        }
    }
    private val takePictureResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val bitmap: Bitmap? = data?.extras?.get("data") as Bitmap?
            bitmap?.let { babyIcon.setImageBitmap(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.intro_layout, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        continueButton.isEnabled = false
        val onTextChanged = OnTextChanged()
        babyName.addTextChangedListener(onTextChanged)
        val onDateChanged = OnDateChanged()
        birthDay.setOnDateChangedListener(onDateChanged)
        val onIconClicked = OnIconClicked()
        babyIcon.setOnClickListener(onIconClicked)
    }

    inner class OnIconClicked : View.OnClickListener {
        override fun onClick(v: View?) {
            PictureUtils.showChoosePictureDialog(
                requireContext(),
                takePictureResultLauncher,
                galleryResultLauncher
            )
        }
    }

    inner class OnDateChanged : DatePicker.OnDateChangedListener {
        override fun onDateChanged(
            view: DatePicker?,
            year: Int,
            monthOfYear: Int,
            dayOfMonth: Int
        ) {
            viewModel.pickDate = true
            continueButton.isEnabled = viewModel.getIfContinueEnabled(babyName)
        }
    }

    inner class OnTextChanged : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            continueButton.isEnabled = viewModel.getIfContinueEnabled(babyName)
        }

    }
}