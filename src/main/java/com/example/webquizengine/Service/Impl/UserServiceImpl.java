package com.example.webquizengine.Service.Impl;

import com.example.webquizengine.DTO.UserDto;
import com.example.webquizengine.Entities.User;
import com.example.webquizengine.Exceptions.UserAlreadyExistsException;
import com.example.webquizengine.Repository.UserRepository;
import com.example.webquizengine.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder encoder) {
        this.repository = userRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public void registerUser(UserDto userDto) {
        if (repository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with username '" + userDto.getUsername() + "' already exists");
        }
        User newUser = modelMapper.map(userDto, User.class);
        newUser.setPassword(encoder.encode(userDto.getPassword()));
        newUser.setAuthority("ROLE_USER");
        repository.save(newUser);
    }
}
