package com.brold.healthTalk.user.auth.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignUpDto {
    private String email;
    private String password;
    private String nickname;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private String bio;
    private String level;
    private String painArea;
    private String purpose;
    private String profileImageUrl;
}
