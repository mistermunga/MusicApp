package com.example.MusicApp.service;

import com.example.MusicApp.model.Song;
import com.example.MusicApp.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    public List<Song> getAllSongs() {
        return (List<Song>) songRepository.findAll();
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    public Song saveSong(Song song) {
        return songRepository.save(song);
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }

    public void updatePlayCount(Long id) {
        Song song = getSongById(id);
        if (song != null) {
            song.setPlayCount(song.getPlayCount() + 1);
            songRepository.save(song);
        }
    }
}
