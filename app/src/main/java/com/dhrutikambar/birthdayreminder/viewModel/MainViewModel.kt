package com.dhrutikambar.birthdayreminder.viewModel

import androidx.lifecycle.ViewModel
import com.dhrutikambar.birthdayreminder.utils.BirthdayDetail

class MainViewModel : ViewModel() {

    fun getBirthDayList(): ArrayList<BirthdayDetail> {
        return arrayListOf<BirthdayDetail>().apply {
            add(BirthdayDetail(name = "Amar", date = "27-07-24"))
            add(BirthdayDetail(name = "Raja", date = "28-07-24"))
            add(BirthdayDetail(name = "Ramesh", date = "29-07-24"))
            add(BirthdayDetail(name = "Rohan", date = "30-07-24"))

        }
    }
}