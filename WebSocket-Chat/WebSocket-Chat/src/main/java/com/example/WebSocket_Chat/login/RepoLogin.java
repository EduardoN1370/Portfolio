package com.example.WebSocket_Chat.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoLogin extends CrudRepository<Login, Long> {

    public Login findByUsername(String username);
}
