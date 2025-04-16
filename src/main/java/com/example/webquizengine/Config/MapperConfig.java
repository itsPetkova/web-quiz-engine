package com.example.webquizengine.Config;

import com.example.webquizengine.DTO.CompletedQuizDto;
import com.example.webquizengine.Entities.CompletedQuiz;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.typeMap(CompletedQuiz.class, CompletedQuizDto.class).addMappings(mapper ->
                mapper.map(src -> src.getQuiz().getId(), CompletedQuizDto::setId));

        return modelMapper;
    }

}
