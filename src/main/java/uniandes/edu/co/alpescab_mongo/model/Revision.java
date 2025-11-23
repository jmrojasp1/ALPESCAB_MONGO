package uniandes.edu.co.alpescab_mongo.model;

import java.util.Date;

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
@Document(collection = "revisiones")
public class Revision {

    @Id
    private ObjectId id;

    private String tipo;
    private ObjectId idServicio;
    private ObjectId idRevisor;
    private ObjectId idRevisado;
    private Integer calificacion;
    private String comentario;
    private Date fecha;
}
