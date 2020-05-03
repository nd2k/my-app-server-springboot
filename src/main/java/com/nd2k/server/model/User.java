package com.nd2k.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "user")
public class User {

    @Id
    private ObjectId id;

    @Indexed(unique = true)
    @Email
    @NotBlank(message = "Email field is mandatory.")
    private String email;

    @NotBlank(message = "Password field is mandatory.")
    @Size(min = 4, max = 50, message = "Password field must contain between 4 & 50 charaters.")
    private String password;

    private Boolean isActive;

    @CreatedDate
    private DateTime createAt;

    @LastModifiedDate
    private DateTime modifiedAt;

}
