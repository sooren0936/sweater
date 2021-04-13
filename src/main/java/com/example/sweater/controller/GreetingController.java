package com.example.sweater.controller;

import com.example.sweater.domain.Message;
import com.example.sweater.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    final private MessageRepository messageRepository;

    @Autowired
    public GreetingController(final MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") final String name,
                           final Map<String, Object> model) {

        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(final Map<String, Object> model) {
        final Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping("add")
    public String add(@RequestParam final String text,
                      @RequestParam final String tag,
                      final Map<String, Object> model) {

        messageRepository.save(new Message(text, tag));
        final Iterable<Message> messages = messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam final String tag,
                         final Map<String, Object> model) {

        final Iterable<Message> messages = tag != null && !tag.isEmpty()
            ? messageRepository.findByTag(tag)
            : messageRepository.findAll();

        model.put("messages", messages);
        return "main";
    }
}
