package com.example.webquizengine.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
public class UserDto {
    @Email(message = "Must be a well-formed email address")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "Invalid email format")
    private String username;

    @NotBlank
    @Size(min = 5)
    private String password;

    public UserDto() {
    }

    @JsonCreator
    public UserDto(
            @JsonProperty("email") String username,
            @JsonProperty("password") String password
    ) {
        this.username = username;
        this.password = password;
    }

}
