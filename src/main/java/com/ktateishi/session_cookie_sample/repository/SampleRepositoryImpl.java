package com.ktateishi.session_cookie_sample.repository;

import com.ktateishi.session_cookie_sample.model.entity.UserEntity;
import com.ktateishi.session_cookie_sample.model.form.UserForm;
import org.springframework.stereotype.Repository;

@Repository
public class SampleRepositoryImpl implements SampleRepository {

    @Override
    //　本来であればDBに接続するべきだが、今回はDBに接続したつもりで実装
    public UserEntity selectUser(UserForm form) {
        UserEntity entity = new UserEntity();
        if ("100".equals(form.getId()) && "password1".equals(form.getPassword())) {
            entity.setId(form.getId());
            entity.setName("佐藤");
        } else if ("101".equals(form.getId()) && "password2".equals(form.getPassword())) {
            entity.setId(form.getId());
            entity.setName("鈴木");
        } else if ("102".equals(form.getId()) && "password3".equals(form.getPassword())) {
            entity.setId(form.getId());
            entity.setName("高橋");
        } else {
            entity = null;
        }
        return entity;
    }

}
