package uniandes.edu.co.alpescab_mongo.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Revision;
import uniandes.edu.co.alpescab_mongo.repository.RevisionRepository;

@Service
@RequiredArgsConstructor
public class RevisionService {

    private final RevisionRepository revisionRepository;

    public List<Revision> findAll() {
        return revisionRepository.findAll();
    }

    public Optional<Revision> findById(ObjectId id) {
        return revisionRepository.findById(id);
    }

    public Revision create(Revision revision) {
        return revisionRepository.save(revision);
    }

    public Optional<Revision> update(ObjectId id, Revision revision) {
        return revisionRepository.findById(id)
                .map(existing -> {
                    revision.setId(id);
                    return revisionRepository.save(revision);
                });
    }

    public void delete(ObjectId id) {
        revisionRepository.deleteById(id);
    }
}
