package com.example.beatbox

import android.os.Build
import android.util.Log
import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

const val SOUND_VM_DEBUG = "SoundVMDebugInfo"

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() {
    val title: String?
        @Bindable get() = sound?.name

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }
}