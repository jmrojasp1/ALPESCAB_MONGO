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
import uniandes.edu.co.alpescab_mongo.model.Disponibilidad;
import uniandes.edu.co.alpescab_mongo.model.Vehiculo;
import uniandes.edu.co.alpescab_mongo.service.VehiculoService;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping
    public List<Vehiculo> findAll() {
        return vehiculoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> findById(@PathVariable String id) {
        return vehiculoService.findById(parseObjectId(id))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Vehiculo> create(@RequestBody Vehiculo vehiculo) {
        Vehiculo created = vehiculoService.create(vehiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vehiculo> update(@PathVariable String id, @RequestBody Vehiculo vehiculo) {
        return vehiculoService.update(parseObjectId(id), vehiculo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        vehiculoService.delete(parseObjectId(id));
        return ResponseEntity.noContent().build();
    }

    private ObjectId parseObjectId(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato de id inválido");
        }
    }

    @PostMapping("/{id}/disponibilidades")
    public ResponseEntity<Vehiculo> agregarDisponibilidad(
            @PathVariable String id,
            @RequestBody Disponibilidad disponibilidad
    ) {
        ObjectId objectId = parseObjectId(id);

        return vehiculoService.agregarDisponibilidad(objectId, disponibilidad)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
