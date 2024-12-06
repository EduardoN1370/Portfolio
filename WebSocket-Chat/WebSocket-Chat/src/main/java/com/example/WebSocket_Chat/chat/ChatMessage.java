package com.example.WebSocket_Chat.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class ChatMessage {

    private String content;
    private String sender;
    private MessageType type;
}
