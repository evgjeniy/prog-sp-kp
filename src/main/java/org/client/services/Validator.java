package org.client.services;

import org.server.models.Role;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern namePattern = Pattern.compile("^[A-ZА-Я][a-zа-я]+$");
    private static final Pattern datePattern = Pattern.compile("^(19|20)?[0-9]{2}-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$");
    private static final Pattern postPattern = Pattern.compile("^[[A-ZА-Я][a-zа-я]+ ?]+$");
    private static final Pattern salaryPattern = Pattern.compile("^[0-9]+.[0-9]{2}$");
    private static final Pattern loginPattern = Pattern.compile("^[A-Za-z]{6,20}$");
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$");
    private static final Pattern mailPattern = Pattern.compile(
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    public static boolean name(String name) {
        return namePattern.matcher(name).matches();
    }

    public static boolean sqlDate(String date) {
        return datePattern.matcher(date).matches();
    }

    public static boolean post(String post) {
        return postPattern.matcher(post).matches();
    }

    public static boolean salary(String salary) {
        return salaryPattern.matcher(salary).matches();
    }

    public static boolean login(String login) {
        return loginPattern.matcher(login).matches();
    }

    public static boolean password(String password) {
        return passwordPattern.matcher(password).matches();
    }

    public static boolean mail(String mail) {
        return mailPattern.matcher(mail).matches();
    }

    public static boolean role(Role role) {
        if (role == null) return false;
        return Pattern.compile("Admin|User|Manager").matcher(role.getName()).matches();
    }
}
