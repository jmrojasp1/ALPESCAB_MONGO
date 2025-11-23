package uniandes.edu.co.alpescab_mongo.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "vehiculos")
public class Vehiculo {

    @Id
    private ObjectId id;

    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private String placa;
    private String ciudadPlaca;
    private Integer capacidadPasajeros;
    private String nivelTransporte;
    private ObjectId idConductor;
    @Builder.Default
    private List<Disponibilidad> disponibilidades = new ArrayList<>();
    @Builder.Default
    private List<ObjectId> servicios = new ArrayList<>();
}
