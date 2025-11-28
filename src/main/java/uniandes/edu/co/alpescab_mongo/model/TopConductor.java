package uniandes.edu.co.alpescab_mongo.model;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.Data;

@Data
public class TopConductor {

    @Field("_id")          
    private ObjectId idConductor;

    private long totalServicios;
}
