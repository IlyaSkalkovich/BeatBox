package com.example.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.os.Build
import android.util.Log
import android.widget.SeekBar
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.IOException

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5
private const val BEAT_BOX_DEBUG = "BeatBoxDebugInfo"

class BeatBox(private val assets: AssetManager): BaseObservable() {
    private val sounds: List<Sound>
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    @Bindable
    var playbackSpeed = 100
    @Bindable
    val minProgress = 50
    @Bindable
    val maxProgress = 200

    init {
        sounds = loadSounds()
    }

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

    fun loadSounds(): List<Sound> {
        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }

        val sounds = mutableListOf<Sound>()

        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)

            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Could not load sound $filename", ioe)
            }
        }

        return sounds
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            soundPool.play(it,
                1.0f,
                1.0f,
                1,
                0,
                playbackSpeed.toFloat().div(100)
            )
        }
    }

    fun onProgressChanged(
        seekBar: SeekBar?,
        progress: Int,
        fromUser: Boolean
    ) {
        playbackSpeed = progress
        notifyPropertyChanged(BR.playbackSpeed)
    }

    fun release() {
        soundPool.release()
    }
}