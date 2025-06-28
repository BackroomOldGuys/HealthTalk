package com.brold.healthTalk.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String email;
    private String password;
    private String nickname;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private String level;
    private String painArea;
    private String purpose;
    private String profileImageUrl;
}
