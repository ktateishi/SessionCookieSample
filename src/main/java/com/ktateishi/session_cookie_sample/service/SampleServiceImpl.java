package com.ktateishi.session_cookie_sample.service;

import com.ktateishi.session_cookie_sample.model.entity.UserEntity;
import com.ktateishi.session_cookie_sample.model.form.UserForm;
import com.ktateishi.session_cookie_sample.repository.SampleRepository;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class SampleServiceImpl implements SampleService {

    private SampleRepository repository;

    public SampleServiceImpl(SampleRepository sampleRepository) {
        this.repository = sampleRepository;
    }

    @Override
    public UserEntity findUser(UserForm form) {
        return repository.selectUser(form);
    }

    @Override
    public String cookieEncode(UserEntity entity) {
        String text = "id=" + entity.getId() + ";name=" + entity.getName();
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public UserEntity cookieDecode(String encodedText) {
        UserEntity user = new UserEntity();
        String rawText = new String(Base64.getDecoder().decode(encodedText), StandardCharsets.UTF_8);
        for (String fieldMapText : rawText.split(";")) {
            String[] splittedText = fieldMapText.split("=");
            switch (splittedText[0]) {
                case "id":
                    user.setId(splittedText[1]);
                    break;
                case "name":
                    user.setName(splittedText[1]);
                    break;
                default:
                    break;
            }
        }
        return user;
    }

}
