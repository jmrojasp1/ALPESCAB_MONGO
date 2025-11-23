package uniandes.edu.co.alpescab_mongo.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Servicio;
import uniandes.edu.co.alpescab_mongo.repository.ServicioRepository;

@Service
@RequiredArgsConstructor
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public List<Servicio> findAll() {
        return servicioRepository.findAll();
    }

    public Optional<Servicio> findById(ObjectId id) {
        return servicioRepository.findById(id);
    }

    public Servicio create(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Optional<Servicio> update(ObjectId id, Servicio servicio) {
        return servicioRepository.findById(id)
                .map(existing -> {
                    servicio.setId(id);
                    return servicioRepository.save(servicio);
                });
    }

    public void delete(ObjectId id) {
        servicioRepository.deleteById(id);
    }
}
