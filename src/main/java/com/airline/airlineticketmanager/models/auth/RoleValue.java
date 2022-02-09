package com.airline.airlineticketmanager.models.auth;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@ToString @EqualsAndHashCode
public final class RoleValue implements Comparable<RoleValue> {

    // Preload roles to work like Enum
    public static final RoleValue ROLE_ADMIN = new RoleValue("ADMIN");
    public static final RoleValue ROLE_USER = new RoleValue("USER");
    public static final RoleValue ROLE_API = new RoleValue("API");
    public static final RoleValue ROLE_API_READ = new RoleValue("API_READ");
    public static final RoleValue ROLE_API_WRITE = new RoleValue("API_WRITE");

    // String representation of Role
    private final String name;
    // The Spring Security role preffix
    private static final String preffix = "ROLE_";

    // A collection of all Roles loaded
    private static final Map<String, RoleValue> map = new LinkedHashMap<>();

    // Initialize defined roles in constant fields
    static {
        loadClassData();
    }

    /**
     * Iterate over all fields of RoleValue class with reflection
     * and calls to putInMap() to initialize the class map with all values.
     */
    private static void loadClassData() {
        Arrays.stream(RoleValue.class.getDeclaredFields())
            .filter(declaredField -> declaredField.getType() == RoleValue.class)
            .forEach(RoleValue::putInMap);
    }

    /**
     * Initialize RoleValue map.
     *
     * @param declaredField A fields of class obtained with reflection.
     */
    private static void putInMap(Field declaredField) {
        try {
            map.putIfAbsent(declaredField.getName(), (RoleValue) declaredField.get(null));
        } catch (IllegalAccessException e) {
            System.err.println("Could not initialize RoleValue Map value: " + declaredField.getName() + " " + e);
        }
    }

    /**
     * Create new RoleValue from external.
     * Puts in class map collection.
     *
     * @param value String representation of new RoleValue.
     */
    public static void putValue(String value) {
        map.putIfAbsent(value, new RoleValue(value));
    }


    /**
     * Private constructor of RoleValue.
     * Append a prefix "ROLE_" used by Spring Security.
     *
     * @param name The String representation of role.
     */
    private RoleValue(String name){
        this.name = preffix + name;
    }

    /**
     * Implement the valueOf() method present in enums.
     *
     * @param name The RoleValue to retreive.
     * @return A RoleValue instance.
     * @throws IllegalArgumentException if no RoleValue exists with name specified.
     */
    public static RoleValue valueOf(String name) {
        RoleValue roleValue = map.get(preffix + name);
        if (roleValue == null) {
            throw new IllegalArgumentException("No RoleValue by the name " + name + " found");
        }
        return roleValue;
    }

    /**
     * As like enums, we define values() method
     * to retrieve all RoleValues stored in map.
     *
     * @return A RoleValue array.
     */
    public static RoleValue[] values() {
        return map.values().toArray(RoleValue[]::new).clone();
    }

    /**
     * Implements comparable interface.
     * Delegates to comparable implementation of String class with name field.
     *
     * @param o RoleValue to compare.
     * @return Integer with compare result (0 if equal).
     */
    @Override
    public int compareTo(RoleValue o) {
        return this.name.compareTo(o.getName());
    }
}
