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
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private ObjectId id;

    private String nombre;
    private String correo;
    private String celular;
    private String cedula;
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    private TarjetaCredito tarjetaCredito;

    @Builder.Default
    private List<ObjectId> vehiculos = new ArrayList<>();
    @Builder.Default
    private List<ObjectId> revisionesRealizadas = new ArrayList<>();
    @Builder.Default
    private List<ObjectId> revisionesRecibidas = new ArrayList<>();
}
