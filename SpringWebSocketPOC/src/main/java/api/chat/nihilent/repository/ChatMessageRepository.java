package api.chat.nihilent.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import api.chat.nihilent.model.ChatMessageModel;

public interface ChatMessageRepository extends MongoRepository<ChatMessageModel, String> {
    List<ChatMessageModel> findAllByOrderByCreateDateAsc();
}