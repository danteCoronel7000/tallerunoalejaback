package com.taller1.colegioJMA2.paraleloComponent.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateParaleloResponse {
    private Integer id;
    private String name;
    private String message;
}
