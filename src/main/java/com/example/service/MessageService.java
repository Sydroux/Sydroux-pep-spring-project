package com.example.service;

import java.util.Optional;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

@Transactional
@Service
public class MessageService {
    MessageRepository messageRepository;

    @Autowired
    public MessageService (MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message postMessage (Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Message getMessageById (int id) {
        Optional<Message> existingMessage = messageRepository.findMessageByMessageId(id);
        if (existingMessage.isPresent()) {
            return existingMessage.get();
        } else {
            return null;
        }
    }

    public int deleteMessage (int id) {
        return messageRepository.deleteMessageByMessageId(id);
    }

    public int updateMessage (int id, String text) {
        return messageRepository.updateMessageByMessageId(text, id);
    }

    public List<Message> getMessageByAccountId (int id) {
        return messageRepository.findMessagesByAccountId(id).get();
    }
}
