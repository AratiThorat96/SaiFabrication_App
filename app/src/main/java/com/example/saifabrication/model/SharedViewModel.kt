package com.example.saifabrication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _savedImages = MutableLiveData<List<String>>()
    val savedImages: LiveData<List<String>> get() = _savedImages

    private val imageList = mutableListOf<String>()

    fun addImage(imageName: String) {
        imageList.add(imageName)
        _savedImages.value = imageList
    }
}
