package com.sap1ens.kafka.game_analytics.model;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private static Random random = new Random();
    private static Console[] consoles = Console.values();
    private static List<String> countries = Arrays.asList("US", "CA", "HU");

    private UUID id;
    private String username;
    private Console console;
    private String countryCode;

    public User() {
    }

    public User(UUID id, String username, Console console, String countryCode) {
        this.id = id;
        this.username = username;
        this.console = console;
        this.countryCode = countryCode;
    }

    public static User generate() {
        return new User(
            UUID.randomUUID(),
            RandomStringUtils.randomAlphanumeric(10),
            consoles[random.nextInt(consoles.length)],
            countries.get(random.nextInt(countries.size()))
        );
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", console=" + console +
            ", countryCode='" + countryCode + '\'' +
            '}';
    }
}
