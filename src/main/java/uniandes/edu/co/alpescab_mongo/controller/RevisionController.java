package uniandes.edu.co.alpescab_mongo.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Revision;
import uniandes.edu.co.alpescab_mongo.service.RevisionService;

@RestController
@RequestMapping("/api/revisiones")
@RequiredArgsConstructor
public class RevisionController {

    private final RevisionService revisionService;

    @GetMapping
    public List<Revision> findAll() {
        return revisionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Revision> findById(@PathVariable String id) {
        return revisionService.findById(parseObjectId(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Revision> create(@RequestBody Revision revision) {
        Revision created = revisionService.create(revision);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Revision> update(@PathVariable String id, @RequestBody Revision revision) {
        return revisionService.update(parseObjectId(id), revision)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        revisionService.delete(parseObjectId(id));
        return ResponseEntity.noContent().build();
    }

    private ObjectId parseObjectId(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de id inválido");
        }
    }
}
