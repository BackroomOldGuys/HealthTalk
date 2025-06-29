package com.brold.healthTalk.user.profile.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileInfoDto {
    private Long userId;
    private String email;
    private String nickname;
    private String gender;
    private int age;
    private double height;
    private double weight;
    private String level;
    private String painArea;
    private String purpose;
    private String profileImageUrl;
    private int followerCount;
    private int followingCount;
}
