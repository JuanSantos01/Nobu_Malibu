package com.restaurant.controller;

import com.restaurant.dto.ChatRequest;
import com.restaurant.dto.ChatResponse;
import com.restaurant.services.IA.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ChatbotController {

    private final ChatbotService chatbotService;

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String response = chatbotService.chat(request.getMessage());

        return new ChatResponse(response);
    }
}