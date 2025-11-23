package uniandes.edu.co.alpescab_mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.alpescab_mongo.model.Servicio;

public interface ServicioRepository extends MongoRepository<Servicio, ObjectId> {
    java.util.List<Servicio> findByIdUsuarioServicioOrderByFechaSolicitudDesc(ObjectId idUsuarioServicio);
    java.util.List<Servicio> findByIdConductor(ObjectId idConductor);
    boolean existsByIdConductorAndEstado(ObjectId idConductor, String estado);
}
