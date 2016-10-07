package com.mx.domain;

/**
 * Created by shenghai on 15/3/30.
 */
public class UserUtils {
    private static ThreadLocal<User> user = new ThreadLocal<>();
    public static User getCurrentUser() {
        return user.get();
    }

    public static void putCurrentUser(User tyUser) {
        user.set(tyUser);
    }
}
