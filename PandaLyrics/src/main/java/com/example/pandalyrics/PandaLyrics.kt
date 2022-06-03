package com.example.pandalyrics

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit.Callback
import retrofit.RetrofitError
import retrofit.client.Response
import java.io.File

class PandaLyrics {

    companion object {

        fun loadLyrics(context: Context, songName: String, songPath: String, artistName: String,  callback:(String)-> Unit ) {

            var lyrics: String? = LyricsExtractor.getLyrics(File(songPath))

            if (lyrics != null) {
               callback(lyrics)
            } else {

                if (isNetworkAvailable(context)) {

                    var artist: String = artistName

                    val i = artist.lastIndexOf(" feat")
                    if (i != -1) {
                        artist = artist.substring(0, i)
                    }
                    val load = LyricsLoader.getInstance(context)

                    load.getLyrics(artist, songName.replace("_", " "), object : Callback<String?> {

                        override fun failure(error: RetrofitError?) {
                            callback("Sorry, no lyrics found for this song.")
                        }

                        override fun success(s: String?, response: Response?) {
                            s?.let {
                                lyrics = s
                                lyrics = if (s == "Sorry, We don't have lyrics for this song yet.\n") {
                                    s
                                } else {
                                    s
                                }
                                lyrics?.let{
                                    callback(it)
                                }
                            }?: callback("Sorry, no lyrics found for this song.")
                        }
                    })

                } else {
                    callback("Sorry, No internet Connection available.")
                }
            }
        }

        private fun isNetworkAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }

    }


}