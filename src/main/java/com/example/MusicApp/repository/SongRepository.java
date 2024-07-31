package com.example.MusicApp.repository;

import com.example.MusicApp.model.Song;
import org.springframework.data.repository.CrudRepository;

public interface SongRepository extends CrudRepository<Song, Long> {
}
