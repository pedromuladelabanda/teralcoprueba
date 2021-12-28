package pedro.prueba.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
@NoArgsConstructor

public class CocheDTO {

    private String nombre;
    private String color;
    private int cilindrada;
    private int potencia;
}
