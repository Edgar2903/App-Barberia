package Barberia_API.Barberia_API.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RolUsuarioDTO {

    @NotBlank(message = "El rol es obligatorio")
    @Pattern(
            regexp = "USER|ADMIN",
            message = "El rol debe ser USER o ADMIN"
    )
    private String rol;

    public RolUsuarioDTO() {
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}