package com.example.Playlist.Module;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;


@Entity
@Table(name="Musics")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Music {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Artist")
    private String artist;

    @Column(name = "Title")
    private String title;

    @Column (name = "Owner")
    private String owner;

    @Enumerated(EnumType.STRING)
    @Column(name = "State")
    private State state;

    @ElementCollection
    @CollectionTable(name="shared_menbers",joinColumns = @JoinColumn(name="music_id"))
    @Column(name = "listShare")
    private List<String> listShare;



}
