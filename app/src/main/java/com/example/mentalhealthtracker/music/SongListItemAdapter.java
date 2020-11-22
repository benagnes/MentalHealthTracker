package com.example.mentalhealthtracker.music;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mentalhealthtracker.R;
import com.example.mentalhealthtracker.music.db.Song;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SongListItemAdapter extends RecyclerView.Adapter<SongListItemAdapter.SongListItemHolder> {
    private List<Song> songList;
    private OnSongClickedListener sListener;

    public SongListItemAdapter(List<Song> songList) {
        this.songList = songList;
    }

    public interface OnSongClickedListener  {
        void onSongClicked(int position);
    }

    public void setOnSongClickListener(OnSongClickedListener listener) {
        sListener = listener;
    }

    @NonNull
    @Override
    public SongListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.song_list_layout, parent, false
        );

        return new SongListItemHolder(v, sListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SongListItemHolder holder, int position) {
        Song indexedSong = this.songList.get(position);

        Picasso.get().load(indexedSong.getThumbnailURL()).into(holder.songThumbnail);
        holder.songHeader.setText(indexedSong.getTitle());
        holder.songSubHeader.setText(indexedSong.getArtists());

        if (indexedSong.isPlaying()) {
            holder.card.setCardBackgroundColor(Color.parseColor("#1C1C1C"));
        } else {
            holder.card.setCardBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return this.songList.size();
    }

    public void setSongs(List<Song> songs) {
        this.songList = songs;
        notifyDataSetChanged();
    }

    public List<Song> getSongs() {
        return this.songList;
    }

    // Gets the index of the first song that's selected
    public int getSelectedSongId() {
        for (Song s: songList) {
            if (s.isPlaying())
                return s.getId();
        }
        return -1;
    }

    public static class SongListItemHolder extends RecyclerView.ViewHolder {
        public ImageView songThumbnail;
        public TextView songHeader;
        public TextView songSubHeader;
        public CardView card;

        public SongListItemHolder(@NonNull View itemView, OnSongClickedListener listener) {
            super(itemView);

            songThumbnail = itemView.findViewById(R.id.songThumbnail);
            songHeader = itemView.findViewById(R.id.songHeader);
            songSubHeader = itemView.findViewById(R.id.songSubHeader);
            card = itemView.findViewById(R.id.songCard);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        listener.onSongClicked(position);
                    }
                }
            });
        }
    }
}
