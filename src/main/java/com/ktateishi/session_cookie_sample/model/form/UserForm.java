package com.ktateishi.session_cookie_sample.model.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserForm {

    @Pattern(regexp = "\\d{3}", message = "半角数字3桁で入力してください。")
    @Size(min = 3, max = 3, message = "3桁で入力してください。")
    private String id;

    private String password;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
