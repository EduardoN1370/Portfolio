package com.example.Playlist;
import com.example.Playlist.Module.Music;
import com.example.Playlist.Module.State;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import net.minidev.json.JSONArray;
import java.net.URI;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PlaylistApplicationTests {
	private final String username = "sarah";
	private final String password = "abc123";
	private String location ="/music/18";
	@Autowired
	TestRestTemplate restTemplate;




	@Test
	void shouldNotReturnAMusicWhenDataisNotSaved(){
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(username,password)
				.getForEntity("/music/9999", String.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(responseEntity.getBody()).isBlank();
	}


	@Test

	void shouldCreateANewMusic(){

		List<String> listShared = new ArrayList<String>();
		listShared.add(username);
		Music music = new Music(null,"MGMT","Little Dark Age","carlos", State.COMPARTIDO,listShared);

		ResponseEntity<Void> response = restTemplate.withBasicAuth(username,password).
				postForEntity("/music",music, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);


		URI location = response.getHeaders().getLocation();
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(username,password)
				.getForEntity(location, String.class);

		DocumentContext documentContext = JsonPath.parse(responseEntity.getBody());
		System.out.println(location);
		String artist = documentContext.read("$.artist");
		assertThat(artist).isEqualTo("MGMT");

		String title = documentContext.read("$.title");
		assertThat(title).isEqualTo("Little Dark Age");


	}

	@Test
	void shouldReturnAllMusicWhenListIsRequested(){
		ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music?page1", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int musicLength = documentContext.read("$.length()");
		assertThat(musicLength).isEqualTo(5);

		JSONArray arrayArtist = documentContext.read("$..artist");
		assertThat(arrayArtist).containsExactlyInAnyOrder("Axel Thesleff","Tainy","Steve Lacy","Kendrick Lamar","Tame Impala");

		JSONArray arrayTitle = documentContext.read("$..title");
		assertThat(arrayTitle).containsExactlyInAnyOrder("Bad Karma","DESDE LAS 10","Dark Red","Count Me Out","New Person, Same Old Mistakes");
	}

	@Test
	void shouldReturnAPageOfMusic(){
		ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music?page=0&size=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray read = documentContext.read("$[*]");
		assertThat(read.size()).isEqualTo(1);

		String artist = documentContext.read("$[0].artist");
		assertThat(artist).isEqualTo("Kendrick Lamar");

	}

	@Test
	void shouldNotReturnAListPrivateMusicThatisNotMine(){
		ResponseEntity<String> response = restTemplate
				.withBasicAuth(username, password)
				.getForEntity("/music?page=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray read = documentContext.read("$[*]");

		for (int i = 0; i < read.size(); i++) {
			String state = documentContext.read("$[" + i + "].state");
			if (state.equals("PRIVADO")) {
				String owner = documentContext.read("$[" + i + "].owner");
				assertThat(owner).isNotEqualTo(state);
			}


		}
	}

	@Test
	void shouldNotReturnANotShareMusic() {
		ResponseEntity<String> response = restTemplate
				.withBasicAuth(username, password)
				.getForEntity("/music?page=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray read = documentContext.read("$[*]");

		for (int i = 0; i < read.size(); i++) {
			String state = documentContext.read("$[" + i + "].state");
			String owner = documentContext.read("$[" + i + "].owner");
			if (state.equals("COMPARTIDO") && !(owner.equals(username))) {
				Object listObject = documentContext.read("$[" + i + "].listShare");
				String listString = listObject.toString().replace("[", "")
						.replace("]", "")
						.replace("\"", "");
				List<String> Array = Arrays.asList(listString.split(","));
				assertThat(Array).contains(username);

			}


		}
	}

/*
		ObjectMapper mapper = new ObjectMapper();
		for( int i = 0; i<read.size(); i++){
			Map<String, Object> atributosMusic = mapper.convertValue(read.get(i), Map.class);
			String state=atributosMusic.get("state").toString();
			String owner=atributosMusic.get("owner").toString();
			if(state.equals("COMPARTIDO") && !(owner.equals("sarah")) ){
				Object listObject = atributosMusic.get("listShare");
				String hola =listObject.toString().replace("[","").replace("]","");
				List<String> list=Arrays.asList(hola.split(","));
				if(list.contains("sarah")){
					System.out.println("Esta bien");
				}

			}

		}
*/


	@Test
	void shouldReturnAPrivateMusic() {

	ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music/12",String.class);
	assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	DocumentContext documentContext = JsonPath.parse(response.getBody());
	String artist = documentContext.read("$.state");
	assertThat(artist).isEqualTo("PRIVADO");
	}

	@Test
	void shouldNotReturnAPrivateMusic() {
		ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music/26",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}


	@Test
	void shouldReturnAPublicMusic() {
		ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music/24",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String artist = documentContext.read("$.state");
		assertThat(artist).isEqualTo("PUBLICO");
	}

	@Test
	void shouldReturnAPublicMusicNotMine() {
		ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music/30",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String artist = documentContext.read("$.state");
		assertThat(artist).isEqualTo("PUBLICO");
	}


	@Test
	void shouldReturnAShareMusic() {
		///Es una compartida perteneciente al que hace la response

		ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music/95",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(response.getBody());
		String owner = documentContext.read("$.owner");
		assertThat(owner).isEqualTo(username);
		String artist = documentContext.read("$.state");
		assertThat(artist).isEqualTo("COMPARTIDO");


		///Es una compartida perteneciente a otro que hace la response

		ResponseEntity<String> response2 = restTemplate.withBasicAuth(username,password).getForEntity("/music/45",String.class);
		assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext2 = JsonPath.parse(response2.getBody());
		String owner2 = documentContext2.read("$.owner");
		assertThat(owner2).isNotEqualTo(username);
		String artist2 = documentContext2.read("$.state");
		assertThat(artist2).isEqualTo("COMPARTIDO");


	}

	@Test
	void shouldNotReturnAShareMusicThatisNotMine() {
		ResponseEntity<String> response = restTemplate.withBasicAuth(username,password).getForEntity("/music/47",String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	@Test
	void shouldUpdateAExistingMusic(){
		List<String> listShared = new ArrayList<String>();
		Music musicUpdate = new Music(null,"Kendrick Lamar","Count Me Out","sarah",State.PRIVADO,listShared);
		HttpEntity<Music> request = new HttpEntity<>(musicUpdate);
		ResponseEntity<Void> response = restTemplate.withBasicAuth(username,password).exchange("/music/12", HttpMethod.PUT,request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		ResponseEntity<String> responseEntity = restTemplate.withBasicAuth(username,password).getForEntity("/music/12",String.class);
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		DocumentContext documentContext = JsonPath.parse(responseEntity.getBody());
		String title = documentContext.read("$.title");
		assertThat(title).isEqualTo("Count Me Out");
	}
	@Test
	void shouldNotUpdateAExistingMusic(){
		List<String> listShared = new ArrayList<String>();
		Music musicUpdate = new Music(null,"Steve Lacy","Dark Red","kumar",State.PRIVADO,listShared);
		HttpEntity<Music> request = new HttpEntity<>(musicUpdate);
		ResponseEntity<Void> response = restTemplate.withBasicAuth(username,password).exchange("/music/26", HttpMethod.PUT, request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	@Test
	void shouldNotUpdateANotExistingMusic(){
		List<String> listShared = new ArrayList<String>();
		Music musicUpdate = new Music(null,"Steve Lacy","Dark Red","sarah",State.PRIVADO,listShared);
		HttpEntity<Music> request = new HttpEntity<>(musicUpdate);
		ResponseEntity<Void> response = restTemplate.withBasicAuth(username,password).exchange("/music/99999", HttpMethod.PUT, request, Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shoudDeleteAExistingMusic(){
/*
		for(int i=97; i<126;i++){
			String URI = String.format("/music/%d",i);
		}
*/
		ResponseEntity<Void> response = restTemplate.withBasicAuth("carlos","qrs456").exchange(location,HttpMethod.DELETE,null,Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);


		ResponseEntity<String> responseDelete = restTemplate.withBasicAuth(username,password).getForEntity(location,String.class);
		assertThat(responseDelete.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}


	@Test
	void shoudNotDeleteAExistingMusic(){
		ResponseEntity response = restTemplate.withBasicAuth(username,password).exchange("/music/38",HttpMethod.DELETE,null,Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}


	@Test
	void shouldNotDeleteANotExistingMusic(){
		ResponseEntity response = restTemplate.withBasicAuth(username,password).exchange("/music/9999",HttpMethod.DELETE,null,Void.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}



}
