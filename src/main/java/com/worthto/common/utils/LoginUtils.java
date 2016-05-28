package com.worthto.common.utils;

import com.worthto.common.bean.BaseUser;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by wenjie on 16/1/7.
 */
public class LoginUtils {

    private final static String SESSION_KEY_USER = "session_login_user";
    private final static String COOKIE_KEY = "cookie_loginuser";

    public static void handleLoginSession(HttpServletRequest request, HttpServletResponse response, BaseUser user) {
        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(60 * 10 * 3); //单位是秒 秒x分钟x小时
        session.setAttribute(SESSION_KEY_USER, user);

        Cookie cookie;
        if (user != null) {
            cookie = new Cookie(COOKIE_KEY, user.getUser());
            cookie.setMaxAge(24 * 60 * 60 * 365);
        } else {
            cookie = new Cookie(COOKIE_KEY, "");
            cookie.setMaxAge(0);//不设置时间的话，无法存入本地COOKIE
        }
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void handleLogoutSession(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie(COOKIE_KEY, "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static BaseUser getLoginUser(HttpServletRequest request) {
        return (BaseUser) (request.getSession(false) != null ? request.getSession().getAttribute(SESSION_KEY_USER) : null);
    }

    public static boolean isLoginUser(HttpServletRequest request) {
        return getLoginUser(request) != null;
    }

    public static void updateUser(HttpServletRequest request, BaseUser newUser) {
        if (isLoginUser(request)) {
            HttpSession session = request.getSession(true);
            session.setAttribute(SESSION_KEY_USER, newUser);
        }
    }

    private static <T> T getSessionObj(HttpServletRequest request, String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        return request.getSession(false) != null ? (T) request.getSession(false).getAttribute(key) : null;
    }

}
