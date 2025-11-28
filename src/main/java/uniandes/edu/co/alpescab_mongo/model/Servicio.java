package uniandes.edu.co.alpescab_mongo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "servicios")
public class Servicio {

    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;

    private String tipoServicio;
    private String nivelTransporte;

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId idUsuarioServicio;

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId idConductor;

    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId idVehiculo;

    @Builder.Default
    private List<PuntoRuta> puntosRuta = new ArrayList<>();

    private Double distanciaKm;
    private Double costoTotal;
    private Date fechaSolicitud;
    private Date horaInicio;
    private Date horaFin;
    private Integer duracionMin;
    private String ciudadPrincipal;
    private String estado;

    @Builder.Default
    private List<ObjectId> revisiones = new ArrayList<>();
}
