package net.everify.commands;

import java.util.HashMap;
import java.util.UUID;

public class AwaitingVerification {

    private static HashMap<UUID, Integer> awaiting = new HashMap<>();
    private static HashMap<UUID, Integer> attempts = new HashMap<>();

    public AwaitingVerification(UUID id, int code) {
        if(!awaiting.containsKey(id)) {
            awaiting.put(id, code);
            attempts.put(id, 0);
        }
    }

    public static boolean isAwaitingVerification(UUID id) {
        return awaiting.containsKey(id);
    }

    public static boolean verify(UUID id, int code) {
        return awaiting.get(id) == code;
    }

    public static void removeID(UUID id) {
        awaiting.remove(id);
        attempts.remove(id);
    }

    public static void addAttempts(UUID id) {
        if(attempts.containsKey(id)) {
            attempts.replace(id, attempts.get(id), attempts.get(id) + 1);
        }
    }

    public static Integer getAttempts(UUID id) {
        return attempts.get(id);
    }

    public static void removeAttempts(UUID id) {
        if(attempts.containsKey(id)) {
            attempts.replace(id, attempts.get(id), 0);
        }
    }


}
