package uniandes.edu.co.alpescab_mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disponibilidad {
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    @Builder.Default
    private List<String> tiposServicioHabilitados = new ArrayList<>();
}
