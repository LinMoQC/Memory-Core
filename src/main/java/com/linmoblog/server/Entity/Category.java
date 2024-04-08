package com.linmoblog.server.Entity;


import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Integer categoryKey;
    private String categoryTitle;
    private String pathName;
    private String introduce;
    private String icon;
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Color must start with '#' and followed by six or three hexadecimal characters.")
    private String color;
}
