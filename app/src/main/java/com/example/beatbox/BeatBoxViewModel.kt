package com.example.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BeatBoxViewModel(assets: AssetManager) : ViewModel() {
    val beatBox = BeatBox(assets)

    class BeatBoxViewModelFactory(private val assets: AssetManager): ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(AssetManager::class.java).newInstance(assets)
        }
    }

    override fun onCleared() {
        super.onCleared()
        beatBox.release()
    }
}