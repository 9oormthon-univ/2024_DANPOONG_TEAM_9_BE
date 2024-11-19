package com.goorm.LocC.auth.utils;

import lombok.Getter;

@Getter
public class KakaoProperties {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String ID = "id";
    public static final String ACCOUNT = "kakao_account";
    public static final String PROFILE = "profile";
    public static final String PROFILE_IMAGE_URL = "profile_image_url";
    public static final String NICKNAME = "nickname";
    public static final String EMAIL = "email";
    public static final String USER_INFO_URL = "https://kapi.kakao.com/v2/user/me";
}
