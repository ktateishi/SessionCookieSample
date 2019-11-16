package com.ktateishi.session_cookie_sample.repository;


import com.ktateishi.session_cookie_sample.model.entity.UserEntity;
import com.ktateishi.session_cookie_sample.model.form.UserForm;

public interface SampleRepository {
    UserEntity selectUser(UserForm form);
}
