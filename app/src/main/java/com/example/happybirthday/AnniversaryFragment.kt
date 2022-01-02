package com.example.happybirthday

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import kotlinx.android.synthetic.main.anniversary_layout.*

abstract class AnniversaryFragment : Fragment() {
    private val viewModel: BirthdayViewModel by activityViewModels()
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
        backButton.setOnClickListener { activity?.onBackPressed() }
    }

    abstract fun getDefaultBabyIcon(): Int

    abstract fun getDefaultCameraIcon(): Int

    abstract fun getBackgroundDrawable(): Int

    abstract fun getBackgroundColor(): Int
}