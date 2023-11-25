package com.rolstudio.media

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.rolstudio.media.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding

    private lateinit var runnable: Runnable
    private var handler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaPlayer = MediaPlayer.create(this, R.raw.music)


        binding.musicBar.progress = 0
        binding.musicBar.max = mediaPlayer.duration


        binding.playBtn.setOnClickListener {
            startService(Intent(this, MediaService::class.java))
        }
        binding.playBtn.setOnClickListener {
            if (!mediaPlayer.isPlaying){
                mediaPlayer.start()
                binding.playBtn.setImageResource(R.drawable.pause_button)
            }
            else{
                mediaPlayer.pause()
                binding.playBtn.setImageResource(R.drawable.play_music_button)
            }
        }


        binding.musicBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, pos: Int, changed: Boolean) {
                if(changed){
                    mediaPlayer.seekTo(pos)
                }
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        runnable = Runnable {
            binding.musicBar.progress = mediaPlayer.currentPosition
            handler.postDelayed(runnable,1000)
        }
        handler.postDelayed(runnable,1000)

        mediaPlayer.setOnCompletionListener {
            binding.playBtn.setImageResource(R.drawable.play_music_button)
            binding.musicBar.progress = 0
        }

        binding.previousBtn.setOnClickListener{
            if (binding.musicBar.progress >= 1) {
                mediaPlayer.seekTo(0)
            }
        }
        binding.nextBtn.setOnClickListener{
            if (binding.musicBar.progress >= 1) {
                mediaPlayer.seekTo(10000000)
            }
        }
    }
}