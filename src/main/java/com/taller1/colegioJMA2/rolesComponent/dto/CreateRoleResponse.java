package com.taller1.colegioJMA2.rolesComponent.dto;

public class CreateRoleResponse {
    private Integer id;
    private String name;
    private String message;

    public CreateRoleResponse(Integer id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    // Getters y setters
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
