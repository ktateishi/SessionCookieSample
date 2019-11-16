package com.ktateishi.session_cookie_sample.service;

import com.ktateishi.session_cookie_sample.model.entity.UserEntity;
import com.ktateishi.session_cookie_sample.model.form.UserForm;

public interface SampleService {

    UserEntity findUser(UserForm form);

    String cookieEncode(UserEntity entity);

    UserEntity cookieDecode(String encodedText);
}
