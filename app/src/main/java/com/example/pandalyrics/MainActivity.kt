package com.example.pandalyrics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pandalyrics.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PandaLyrics.loadLyrics(this, songName = "Grenade" , songPath = "null just testing without audio", artistName = "Bruno Mars" ){

            binding.songName.text = "Grenade"
            binding.lyricsText.text = it
        }


    }


}

