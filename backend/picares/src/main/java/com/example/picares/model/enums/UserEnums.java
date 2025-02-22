package com.example.picares.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserEnums {
    USER("user", 1),
    ADMIN("admin", 2);

    private final String name;
    private final int value;

    public static UserEnums getEnumByName(String name) {
        if (name == null) {
            return null;
        }
        for (UserEnums enums : UserEnums.values()) {
            if (enums.name.equals(name)) {
                return enums;
            }
        }
        return null;
    }
}
