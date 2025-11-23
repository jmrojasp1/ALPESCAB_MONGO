package uniandes.edu.co.alpescab_mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.alpescab_mongo.model.Usuario;

public interface UsuarioRepository extends MongoRepository<Usuario, ObjectId> {
}
