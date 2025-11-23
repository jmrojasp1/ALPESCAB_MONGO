package uniandes.edu.co.alpescab_mongo.controller;

import java.util.Date;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Disponibilidad;
import uniandes.edu.co.alpescab_mongo.model.Servicio;
import uniandes.edu.co.alpescab_mongo.model.Usuario;
import uniandes.edu.co.alpescab_mongo.model.Vehiculo;
import uniandes.edu.co.alpescab_mongo.service.OperacionService;

@RestController
@RequestMapping("/api/operaciones")
@RequiredArgsConstructor
public class OperacionController {

    private final OperacionService operacionService;

    @PostMapping("/usuarios/servicio")
    public ResponseEntity<Usuario> registrarUsuarioServicio(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operacionService.registrarUsuarioServicio(usuario));
    }

    @PostMapping("/usuarios/conductor")
    public ResponseEntity<Usuario> registrarUsuarioConductor(@RequestBody Usuario usuario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operacionService.registrarUsuarioConductor(usuario));
    }

    @PostMapping("/conductores/{idConductor}/vehiculos")
    public ResponseEntity<Vehiculo> registrarVehiculo(@PathVariable String idConductor, @RequestBody Vehiculo vehiculo) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(operacionService.registrarVehiculo(parse(idConductor), vehiculo));
    }

    @PostMapping("/vehiculos/{idVehiculo}/disponibilidades")
    public ResponseEntity<Vehiculo> agregarDisponibilidad(@PathVariable String idVehiculo, @RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(operacionService.agregarDisponibilidad(parse(idVehiculo), disponibilidad));
    }

    @PutMapping("/vehiculos/{idVehiculo}/disponibilidades/{index}")
    public ResponseEntity<Vehiculo> modificarDisponibilidad(@PathVariable String idVehiculo, @PathVariable int index,
                                                             @RequestBody Disponibilidad disponibilidad) {
        return ResponseEntity.ok(operacionService.modificarDisponibilidad(parse(idVehiculo), index, disponibilidad));
    }

    @PostMapping("/servicios/solicitar")
    public ResponseEntity<Servicio> solicitarServicio(@RequestBody Servicio servicio) {
        return ResponseEntity.status(HttpStatus.CREATED).body(operacionService.solicitarServicio(servicio));
    }

    @PostMapping("/servicios/{id}/finalizar")
    public ResponseEntity<Servicio> finalizarServicio(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        Date horaFin = payload.get("horaFin") != null ? new Date((Long) payload.get("horaFin")) : new Date();
        Double costoTotal = payload.get("costoTotal") != null ? ((Number) payload.get("costoTotal")).doubleValue() : null;
        Double distanciaKm = payload.get("distanciaKm") != null ? ((Number) payload.get("distanciaKm")).doubleValue() : null;
        return ResponseEntity.ok(operacionService.finalizarServicio(parse(id), horaFin, costoTotal, distanciaKm));
    }

    private ObjectId parse(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");
        }
    }
}
