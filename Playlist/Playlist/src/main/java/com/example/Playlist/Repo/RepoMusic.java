package com.example.Playlist.Repo;

import com.example.Playlist.Module.Music;
import com.example.Playlist.Module.State;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepoMusic extends CrudRepository<Music, Long>, PagingAndSortingRepository<Music, Long> {



    @Query(value = "SELECT COUNT(*) AS music_count FROM (SELECT * FROM musics  u WHERE u.owner = :owner) AS listMusic", nativeQuery = true)
    Long sizeTest(@Param("owner") String owner);


    @Query("SELECT u FROM Music u WHERE u.owner = :owner  " +
            "UNION SELECT p FROM Music p WHERE p.state = :state  " +
            "UNION SELECT m FROM Music m WHERE m.state = :share AND :owner MEMBER OF m.listShare")
    List<Music> findAllMusic(@Param("owner") String owner, @Param("state") State state,@Param("share") State share);

    Optional<Music> findMusicByIdAndOwner(Long id, String owner);



}

