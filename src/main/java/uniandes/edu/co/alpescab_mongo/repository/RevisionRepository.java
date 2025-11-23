package uniandes.edu.co.alpescab_mongo.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import uniandes.edu.co.alpescab_mongo.model.Revision;

public interface RevisionRepository extends MongoRepository<Revision, ObjectId> {
}
