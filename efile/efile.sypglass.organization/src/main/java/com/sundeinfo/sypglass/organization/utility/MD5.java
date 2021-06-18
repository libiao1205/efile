package com.sundeinfo.sypglass.organization.utility;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class MD5 {

    public static String encodePassword(String password) {
        return passwordEncoder().encodePassword(password,null);
    }

    public static boolean checkPassword(String dbPassword,String inputPassword) {
        return passwordEncoder().isPasswordValid(dbPassword,inputPassword,null);
    }

    private static Md5PasswordEncoder passwordEncoder() {
        return new Md5PasswordEncoder();
    }


}
