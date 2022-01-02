package com.example.happybirthday

import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class BirthdayViewModel: ViewModel() {

    var pickDate = false
    var name = ""
    var dateDescription = 0
    var currDate: Date? = null
    var numberOfMonthOrYears = -1
    var chosenDay = -1
    var chosenMonth = -1
    var chosenYear = -1
    var dateLiveData = MutableLiveData<Boolean>()

    fun getIfContinueEnabled(babyName: EditText): Boolean {
        return babyName.text.isNotEmpty() && pickDate
    }

    fun getRandomAction(): Int {
        setRelevantNumber()
        val number: Int = Random().nextInt(3) + 1 // random number of 1 or 2 or 3
        return when (number) {
            1 -> R.id.action_introFragment_to_pelicanFragment
            2 -> R.id.action_introFragment_to_foxFragment
            else -> R.id.action_introFragment_to_elephantFragment
        }
    }

    fun setDateFromDatePicker(day: Int, month: Int, year: Int) {
        chosenDay = day
        chosenMonth = month
        chosenYear = year
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        currDate = calendar.time
        dateLiveData.postValue(pickDate)
    }

    fun getRelevantNumberIcon(): String {
        return "age_number_$numberOfMonthOrYears"
    }

    private fun setRelevantNumber() { //number of month or years
        val month = getMonthsBetweenDates()
        numberOfMonthOrYears = if (month > 12) {
            dateDescription = R.string.years
            month / 12
        } else {
            dateDescription = if (month > 1) {
                R.string.months
            } else {
                R.string.month
            }
            month
        }
    }

    private fun getMonthsBetweenDates(): Int {
        val start = Calendar.getInstance()
        start.time = currDate
        val end = Calendar.getInstance() // current time
        var monthsBetween = 0
        var dateDiff = end[Calendar.DAY_OF_MONTH] - start[Calendar.DAY_OF_MONTH]
        if (dateDiff < 0) {
            val borrrow = end.getActualMaximum(Calendar.DAY_OF_MONTH)
            dateDiff = end[Calendar.DAY_OF_MONTH] + borrrow - start[Calendar.DAY_OF_MONTH]
            monthsBetween--
            if (dateDiff > 0) {
                monthsBetween++
            }
        } else {
            monthsBetween++
        }
        monthsBetween += end[Calendar.MONTH] - start[Calendar.MONTH]
        monthsBetween += (end[Calendar.YEAR] - start[Calendar.YEAR]) * 12
        return monthsBetween
    }
}