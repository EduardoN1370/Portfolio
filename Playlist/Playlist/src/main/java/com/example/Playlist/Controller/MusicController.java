package com.example.Playlist.Controller;

import com.example.Playlist.Module.Music;
import com.example.Playlist.Module.State;
import com.example.Playlist.Repo.RepoMusic;
import com.example.Playlist.Service.MusicService;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/music")
public class MusicController {


    private final RepoMusic repoMusic;

    private  MusicService musicService;

    public MusicController(RepoMusic repoMusic, MusicService musicService) {
        this.repoMusic = repoMusic;
        this.musicService = musicService;
    }



    @GetMapping()
    public ResponseEntity<List<Music>> allMusic(Principal principal,Pageable pageable) {
        List<Music> listAll= repoMusic.findAllMusic(principal.getName(),State.PUBLICO,State.COMPARTIDO);
        Page<Music> pageAll = musicService.listToPage( pageable, listAll);
        return ResponseEntity.ok(pageAll.getContent());
    }


    @GetMapping("/size")
    public ResponseEntity<Long> SizeOwner(Principal principal) {
        return ResponseEntity.ok(repoMusic.sizeTest(principal.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Music> findById(@PathVariable Long id,Principal principal) {
        Optional<Music> isEmpty = repoMusic.findById(id);
        if(isEmpty.isPresent()){
            Optional<Music> isMusic=musicService.findById(isEmpty.get(),principal);
            if(isMusic.isPresent()){
                return new ResponseEntity<>(isEmpty.get(), HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<Void> addMusic(@RequestBody Music music, UriComponentsBuilder ucb){

        if(music.getState() == State.PUBLICO || music.getState() == State.PRIVADO){

            List<String> emptyList = new ArrayList<>();
            music.setListShare(emptyList);

        }

        Music musicObj = repoMusic.save(music);

        URI locationOfNewMusic = ucb.path("/music/{id}").buildAndExpand(musicObj.getId()).toUri();

        return ResponseEntity.created(locationOfNewMusic).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateMusicById(@RequestBody Music music,@PathVariable Long id,Principal principal){
        Optional<Music> oldMusicData = repoMusic.findMusicByIdAndOwner(id,principal.getName());
        if(oldMusicData.isPresent()){
            Music updateMusicData = oldMusicData.get();
            updateMusicData.setTitle(music.getTitle());
            updateMusicData.setArtist(music.getArtist());
            updateMusicData.setOwner(music.getOwner());
            updateMusicData.setState(music.getState());
            updateMusicData.setListShare(music.getListShare());
            repoMusic.save(updateMusicData);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusicById(@PathVariable Long id,Principal principal){

            Optional<Music> musicData = repoMusic.findMusicByIdAndOwner(id,principal.getName());

            if (musicData.isPresent()) {

                    repoMusic.deleteById(id);

               return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}


