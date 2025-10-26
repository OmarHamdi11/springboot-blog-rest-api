package com.springboot.blog.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "Login Data Transfer Object - contains user credentials for authentication"
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {

    @Schema(
            description = "Username or email address of the user",
            example = "omar_ellafy"
    )
    private String usernameOrEmail;

    @Schema(
            description = "User's password",
            example = "password123"
    )
    private String password;
}
