package com.sap1ens.kafka.game_analytics.dao;

import com.sap1ens.kafka.game_analytics.model.User;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UserRegistry {
    private static Random random = new Random();

    private static List<User> users = IntStream.range(1, 10)
        .mapToObj(i -> User.generate())
        .collect(Collectors.toList());

    public static List<User> findUsers() {
        return users;
    }

    public static User findUser() {
        return users.get(random.nextInt(users.size()));
    }
}
