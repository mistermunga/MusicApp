package com.example.MusicApp.controller;

import com.example.MusicApp.model.Song;
import com.example.MusicApp.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    private static final String MUSIC_DIR = "src/main/resources/static/music/";

    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @PostMapping
    public Song addSong(@RequestBody Song song) {
        return songService.saveSong(song);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }

    @PutMapping("/updatePlayCount/{id}")
    public void updatePlayCount(@PathVariable Long id) {
        songService.updatePlayCount(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("songId") Long songId) {
        try {
            // Save the file to the server
            String fileName = file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(MUSIC_DIR + fileName));

            // Update the Song entity with the file name
            Song song = songService.getSongById(songId);
            if (song != null) {
                song.setFileName(fileName);
                songService.saveSong(song);
            }

            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("File upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}