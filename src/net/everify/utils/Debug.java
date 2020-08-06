package net.everify.utils;

import net.everify.mail.JavaMail;


public class Debug {

    public static void main(String[] args) {
        JavaMail.sendVerificationEmail("contact.aksyo@gmail.com", 3360, null);
    }

}
