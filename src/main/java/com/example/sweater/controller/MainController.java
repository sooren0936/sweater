package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.domain.User;
import com.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    private final MessageRepository messageRepository;

    @Autowired
    public MainController(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/")
    public String greeting(final Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") final String filter,
                       final Model model) {
        final Iterable<Message> messages = filter != null && !filter.isEmpty()
            ? messageRepository.findByTag(filter)
            : messageRepository.findAll();

        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam final String text,
                      @RequestParam final String tag,
                      final Map<String, Object> model) {
        messageRepository.save(new Message(text, tag, user));
        final Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }
}
