package com.example.beatbox

import android.os.Build
import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() {
    @Bindable
    var playbackSpeed = 100
    @Bindable
    val minProgress = 50
    @Bindable
    val maxProgress = 200

    val title: String?
        @Bindable get() = sound?.name

    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it, playbackSpeed.toFloat().div(100))
        }
    }

    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange()
        }

    fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
    ) {
        playbackSpeed = progress
        notifyChange()
    }
}