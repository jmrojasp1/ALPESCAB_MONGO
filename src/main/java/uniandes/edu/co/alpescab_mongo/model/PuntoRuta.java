package uniandes.edu.co.alpescab_mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoRuta {
    private int orden;
    private double latitud;
    private double longitud;
    private String direccion;
    private String ciudad;
}
