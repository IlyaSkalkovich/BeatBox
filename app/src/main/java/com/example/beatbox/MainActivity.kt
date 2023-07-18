package com.example.beatbox

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beatbox.databinding.ActivityMainBinding
import com.example.beatbox.databinding.ListItemSoundBinding

class MainActivity : AppCompatActivity() {
    private lateinit var viewModelFactory: BeatBoxViewModel.BeatBoxViewModelFactory
    private lateinit var binding: ActivityMainBinding
    private lateinit var soundViewModel: SoundViewModel

    private val beatBoxViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[BeatBoxViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModelFactory = BeatBoxViewModel.BeatBoxViewModelFactory(assets)

        soundViewModel = SoundViewModel(beatBoxViewModel.beatBox)




        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBoxViewModel.beatBox.loadSounds())
        }

    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = soundViewModel
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) :
        RecyclerView.Adapter<SoundHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = ListItemSoundBinding.inflate(layoutInflater)

            //soundViewModel = SoundViewModel(beatBoxViewModel.beatBox)


            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]

            binding.viewModel = soundViewModel
            //binding.executePendingBindings()

            holder.bind(sound)
        }

        override fun getItemCount() = sounds.size
    }
}