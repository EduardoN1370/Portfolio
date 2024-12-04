package com.example.Playlist.Service;

import com.example.Playlist.Module.Music;
import com.example.Playlist.Module.State;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class MusicService {

    private Pageable createPageRequestUsing (Pageable pageable) {
        return  PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize()
        );

    }
    public Page<Music> listToPage(Pageable pageable, List<Music> allMusics) {
        Pageable pageRequest = createPageRequestUsing(pageable);
        int start = (int) pageRequest.getOffset();
        int end = Math.min(start + pageRequest.getPageSize(), allMusics.size());
        List<Music> musics = allMusics.subList(start, end);
        return new PageImpl<Music>(musics, pageRequest, allMusics.size());
    }

    public Optional<Music> findById(Music music, Principal principal) {

        if(!music.getOwner().equals(principal.getName()) && music.getState().equals(State.COMPARTIDO)  )  {

            if (music.getListShare().contains(principal.getName()))
            {
                return Optional.of(music);
            }
            else{
                return Optional.empty();
            }

        }
        if(!music.getOwner().equals(principal.getName()) && music.getState().equals(State.PRIVADO)  )  {
                return Optional.empty();
        }

        return Optional.of(music);

    }
}

