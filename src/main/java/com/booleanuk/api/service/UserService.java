package com.booleanuk.api.service;

import com.booleanuk.api.exceptions.GameNotFoundException;
import com.booleanuk.api.exceptions.UserNotFoundException;
import com.booleanuk.api.model.Game;
import com.booleanuk.api.model.User;
import com.booleanuk.api.repository.GameRepository;
import com.booleanuk.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Transactional
    public User addGameToUser(Long userId, Long gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));

        user.getGames().add(game);
        game.getUsers().add(user);

        userRepository.save(user);
        gameRepository.save(game);
        return user;
    }

}