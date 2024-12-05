package com.example.beatbox

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

const val MAIN_ACTIVITY_DEBUG = "MainActivityDebugInfo"

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: BeatBoxViewModel.BeatBoxViewModelFactory
    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var soundViewModel: SoundViewModel

    private val beatBoxViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BeatBoxViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModelFactory = BeatBoxViewModel.BeatBoxViewModelFactory(assets)

        mainBinding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBoxViewModel.beatBox.loadSounds())
        }

        soundViewModel = SoundViewModel(beatBoxViewModel.beatBox)

        mainBinding.beatBox = beatBoxViewModel.beatBox
        mainBinding.executePendingBindings()

    }

    private inner class SoundHolder(private val listItemBinding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(listItemBinding.root) {

        init {
            listItemBinding.viewModel = SoundViewModel(beatBoxViewModel.beatBox)
        }

        fun bind(sound: Sound) {
            listItemBinding.apply {
                viewModel?.sound = sound

                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = ListItemSoundBinding.inflate(layoutInflater)

            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]

            holder.bind(sound)
        }

        override fun getItemCount() = sounds.size
    }
}