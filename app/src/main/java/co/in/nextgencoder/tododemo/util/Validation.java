package co.in.nextgencoder.tododemo.util;

public class Validation {

    public Validation() {}

    public String validateUser(String mail, String password) {
        if (mail == null || mail.isEmpty()) {
            return "Enter mail to continue";
        }
        if (password == null || password.isEmpty()) {
            return "Enter password to continue";
        }
        if (password.length() < 8) {
            return "Password length should be greater than 8";
        }
        return "successful";
    }
}
