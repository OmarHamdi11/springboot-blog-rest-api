package com.springboot.blog.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "Register Data Transfer Object - contains user information for registration"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @Schema(
            description = "Full name of the user",
            example = "omar ellafy"
    )
    private String name;

    @Schema(
            description = "Unique username for the account",
            example = "omar_ellafy"
    )
    private String username;

    @Schema(
            description = "Email address of the user",
            example = "omar_ellafy@example.com"
    )
    private String email;

    @Schema(
            description = "Password for the account",
            example = "SecurePass@123"
    )
    private String password;

}
