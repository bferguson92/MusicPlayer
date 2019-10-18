package com.example.musicplayer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R

class MusicDisplayAdapter(private val musicList: List<String>) :
    RecyclerView.Adapter<MusicDisplayAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_item_music,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.apply {
            trackName.text = musicList[position]
        }
    }

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trackName: TextView = view.findViewById(R.id.text_view_music_title)
    }
}