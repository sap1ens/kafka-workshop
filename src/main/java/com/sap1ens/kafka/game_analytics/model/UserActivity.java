package com.sap1ens.kafka.game_analytics.model;

import com.sap1ens.kafka.game_analytics.dao.UserRegistry;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserActivity {
    private UUID userId;
    private String action;
    private Map<String, String> metadata = new HashMap<>();

    public UserActivity() {
    }

    public UserActivity(UUID userId, String action, Map<String, String> metadata) {
        this.userId = userId;
        this.action = action;
        this.metadata = metadata;
    }

    public static UserActivity generate() {
        return new UserActivity(
            UserRegistry.findUser().getId(),
            "game_played",
            new HashMap<>()
        );
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "UserActivity{" +
            "userId=" + userId +
            ", action='" + action + '\'' +
            ", metadata=" + metadata +
            '}';
    }
}
