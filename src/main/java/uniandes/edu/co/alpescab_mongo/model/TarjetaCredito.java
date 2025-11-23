package uniandes.edu.co.alpescab_mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaCredito {
    private String numero;
    private String nombreTarjeta;
    private String fechaVencimiento;
    private String codigoSeguridad;
}
