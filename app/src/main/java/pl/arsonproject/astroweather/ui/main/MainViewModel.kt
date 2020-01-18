package pl.arsonproject.astroweather.ui.main

import android.widget.Toast
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.arsonproject.astroweather.model.Location
import pl.arsonproject.astroweather.repository.ApiFactory

class MainViewModel: ViewModel(), Observable{

    val locationList : MutableLiveData<ArrayList<Location>> = MutableLiveData(arrayListOf())
    val errorMessage : MutableLiveData<String> = MutableLiveData()

    @Bindable
    val local = MutableLiveData<String>()

    fun searchLocation(){
        try {
            if (!local.value.isNullOrEmpty()) {
                viewModelScope.launch {
                    var weatherService = ApiFactory.weatherApi
                    var response = weatherService.getCurrentWeatherAsync(local.value!!).await()
                    if (response.isSuccessful) {
                        for (loc in response.body()?.data!!) {
                            locationList.value?.add(loc)
                            locationList.notifyObserver()
                        }
                    }
                }
            }else{
                errorMessage.value = "Wprowadź nazwę miejscowości"
            }
        } catch (e: Exception) {
            errorMessage.value = e.message
        }
    }

    @delegate:Transient
    private val mCallBacks: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        mCallBacks.add(callback)
    }
}

fun <T> MutableLiveData<T>.notifyObserver() {
    this.value = this.value
}