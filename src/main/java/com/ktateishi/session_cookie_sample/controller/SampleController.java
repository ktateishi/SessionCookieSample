package com.ktateishi.session_cookie_sample.controller;

import com.ktateishi.session_cookie_sample.model.entity.UserEntity;
import com.ktateishi.session_cookie_sample.model.form.UserForm;
import com.ktateishi.session_cookie_sample.service.SampleService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

import static com.ktateishi.session_cookie_sample.symbol.Symbols.COOKIE_AGE;
import static com.ktateishi.session_cookie_sample.symbol.Symbols.COOKIE_KEY;

@Controller
public class SampleController {

    private SampleService service;

    public SampleController(SampleService sampleService) {
        this.service = sampleService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public UserForm setupForm() {
        return new UserForm();
    }


    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/login")
    public String login(@Validated UserForm form, BindingResult bindingResult,
                        HttpServletResponse response, Model model) {

        // 入力チェックエラーが存在した場合はログイン画面を表示する
        if (bindingResult.hasErrors()) {
            return "index.html";
        }

        // ユーザ検索
        UserEntity user = service.findUser(form);

        if (user != null) {
            // ログイン成功
            Cookie cookie = new Cookie(COOKIE_KEY, service.cookieEncode(user));
            // 7日間保管
            cookie.setMaxAge(COOKIE_AGE);
            // クッキー保管
            response.addCookie(cookie);
        } else {
            // ログイン失敗
            model.addAttribute("loginError", "IDまたはパスワードが違います。");
            return "index.html";
        }

        model.addAttribute("user", user);

        return "output.html";
    }

    @RequestMapping("/output2")
    public String output2(HttpServletRequest request, Model model) {
        // クッキー情報取得
        UserEntity user = service.cookieDecode(
                Arrays.stream(request.getCookies())
                        .filter(cookie -> COOKIE_KEY.equals(cookie.getName()))
                        .findFirst()
                        .get()
                        .getValue());
        model.addAttribute("user", user);
        return "output2.html";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        // クッキー情報削除
        Cookie cookies[] =request.getCookies();
        for (Cookie cookie : cookies ) {
            // id という名前のCookieを削除します。
            if (COOKIE_KEY.equals(cookie.getName())) {
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
        return "index";
    }

}
