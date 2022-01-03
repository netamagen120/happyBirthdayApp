package com.example.happybirthday

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.anniversary_layout.*
import kotlinx.android.synthetic.main.anniversary_layout.babyIcon

abstract class AnniversaryFragment : Fragment() {
    private val viewModel: BirthdayViewModel by activityViewModels()
    private val galleryResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val uri = data?.data
            uri?.let {
                viewModel.anniversaryUri = it
                viewModel.anniversaryBitmap = null
                profileImage.setImageURI(it)
            }
        }
    }
    private val takePictureResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val bitmap: Bitmap? = data?.extras?.get("data") as Bitmap?
            bitmap?.let {
                viewModel.anniversaryBitmap = it
                viewModel.anniversaryUri = null
                profileImage.setImageBitmap(it)
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.anniversary_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        containerLayout.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                getBackgroundColor()
            )
        )
        babyTitle.text = viewModel.name
        val identifier = resources.getIdentifier(
            viewModel.getRelevantNumberIcon(),
            "drawable",
            requireActivity().packageName
        )
        babyAge.setImageResource(identifier)
        babyAgeDescription.text = getString(viewModel.dateDescription)
        babyIcon.setImageResource(getDefaultBabyIcon())
        animalBg.setImageResource(getBackgroundDrawable())
        imageContainer.setBackgroundResource(getCircleBgDrawable())
        backButton.setOnClickListener { activity?.onBackPressed() }
        val onIconClicked = (activity as MainActivity).OnIconClicked(
            requireContext(),
            takePictureResultLauncher,
            galleryResultLauncher
        )
        cameraIcon.apply {
            setOnClickListener(onIconClicked)
            visibility = View.GONE
        }
        containerLayout.viewTreeObserver.addOnGlobalLayoutListener(OnGlobalAnniversaryLayoutListener())
        // if pick already anniversary picture, show it
        viewModel.anniversaryBitmap?.let {
            profileImage.setImageBitmap(it)
        } ?: kotlin.run {
            viewModel.anniversaryUri?.let {
                profileImage.setImageURI(it)
            }
        }
    }

    inner class OnGlobalAnniversaryLayoutListener : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            val radius = babyIcon.measuredHeight / 2
            val constraintLayout = containerLayout as ConstraintLayout
            val constraintSet = ConstraintSet()
            // set image icon in 45 angle
            constraintSet.clone(constraintLayout)
            constraintSet.constrainCircle(
                R.id.cameraIcon,
                R.id.imageContainer,
                radius,
                45f
            )
            constraintSet.applyTo(constraintLayout)
            cameraIcon.apply {
                setImageResource(getDefaultCameraIcon())
                visibility = View.VISIBLE
            }
            containerLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
        }

    }

    abstract fun getDefaultBabyIcon(): Int

    abstract fun getDefaultCameraIcon(): Int

    abstract fun getBackgroundDrawable(): Int

    abstract fun getBackgroundColor(): Int

    abstract fun getCircleBgDrawable(): Int
}