package com.vaider.service.enums;

import java.util.Arrays;

/**
 * @author ppuchinsky
 */
public enum ServiceCommands {
    HELP("/help"),
    CANCEL("/cancel"),
    START("/start"),
    GAME("/game");

    private final String cmd;

    ServiceCommands(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public static ServiceCommands getCommand(String cmd) {
        return Arrays.stream(ServiceCommands.values())
                .filter(command -> command.cmd.equals(cmd))
                .findFirst()
                .orElse(null);
    }
}
