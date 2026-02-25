package Reaktor_redes_main.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="redes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Red {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String ssid;

    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String password;

    @Column
    private String seguridad;

}
