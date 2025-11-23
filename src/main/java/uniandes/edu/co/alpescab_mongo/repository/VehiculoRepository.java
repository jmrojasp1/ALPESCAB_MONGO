package uniandes.edu.co.alpescab_mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.alpescab_mongo.model.Vehiculo;

public interface VehiculoRepository extends MongoRepository<Vehiculo, ObjectId> {
}
