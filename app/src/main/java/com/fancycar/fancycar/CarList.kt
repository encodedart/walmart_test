package com.fancycar.fancycar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CarList(val app: Application) : AndroidViewModel(app) {
    private val carListLiveData: MutableLiveData<List<CarDetail>> = MutableLiveData()
    private val carRepo = CarRepo()
    private val carList = mutableListOf<CarDetail>()

    fun getCarList(): MutableLiveData<List<CarDetail>> {
        return carListLiveData
    }

    fun loadCarList() {
        viewModelScope.launch {
            val result = carRepo.getCarList(this@CarList.app.applicationContext)
            when (result) {
                is Result.Success<List<CarDetail>> -> {
                    carList.addAll(result.data)
                    carListLiveData!!.postValue(result.data)
                }
                else -> carListLiveData!!.postValue(null)
            }
        }
    }

    fun sortName(ascending: Boolean) {
        carListLiveData?.let {
            val list = mutableListOf<CarDetail>()
            list.addAll(it.value!!)
            if (ascending) {
                list.sortBy { car -> car.name }
            } else {
                list.sortByDescending { car -> car.name }
            }
            carListLiveData?.postValue(list)
        }
    }

    fun clearSort() {
        carListLiveData?.postValue(carList)
    }

    fun filter(onlyDealer: Boolean) {
        if (!onlyDealer) {
            carListLiveData?.postValue(carList)

        } else {
            carListLiveData?.let {
                carListLiveData?.postValue(it.value!!.filter { it.inDealer })
            }
        }
    }

}