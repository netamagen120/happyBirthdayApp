package com.example.happybirthday

import android.widget.EditText
import androidx.lifecycle.ViewModel

class BirthdayViewModel: ViewModel() {

    var pickDate = false

    fun getIfContinueEnabled(babyName: EditText): Boolean {
        return babyName.text.isNotEmpty() && pickDate
    }
}