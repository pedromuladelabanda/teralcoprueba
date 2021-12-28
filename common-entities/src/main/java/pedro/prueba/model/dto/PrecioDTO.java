package pedro.prueba.model.dto;


import java.sql.Date;

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
public class PrecioDTO {

    private long precio;
    private Date fechaFin;
    private Date fechaInicio;
}
