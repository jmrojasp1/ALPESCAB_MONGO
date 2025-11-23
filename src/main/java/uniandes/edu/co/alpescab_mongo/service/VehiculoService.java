package uniandes.edu.co.alpescab_mongo.service;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Vehiculo;
import uniandes.edu.co.alpescab_mongo.repository.VehiculoRepository;

@Service
@RequiredArgsConstructor
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Optional<Vehiculo> findById(ObjectId id) {
        return vehiculoRepository.findById(id);
    }

    public Vehiculo create(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    public Optional<Vehiculo> update(ObjectId id, Vehiculo vehiculo) {
        return vehiculoRepository.findById(id)
                .map(existing -> {
                    vehiculo.setId(id);
                    return vehiculoRepository.save(vehiculo);
                });
    }

    public void delete(ObjectId id) {
        vehiculoRepository.deleteById(id);
    }
}
