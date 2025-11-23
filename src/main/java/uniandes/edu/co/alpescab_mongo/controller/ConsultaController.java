package uniandes.edu.co.alpescab_mongo.controller;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Servicio;
import uniandes.edu.co.alpescab_mongo.service.ConsultaService;
import uniandes.edu.co.alpescab_mongo.service.ConsultaService.ServiciosCiudad;
import uniandes.edu.co.alpescab_mongo.service.ConsultaService.TopConductor;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping("/usuarios/{idUsuario}/servicios")
    public List<Servicio> historicoUsuario(@PathVariable String idUsuario) {
        return consultaService.historicoPorUsuario(parse(idUsuario));
    }

    @GetMapping("/conductores/top")
    public List<TopConductor> topConductores() {
        return consultaService.topConductores();
    }

    @GetMapping("/ciudad")
    public List<ServiciosCiudad> serviciosCiudad(
            @RequestParam String ciudad,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date fin) {
        return consultaService.serviciosPorCiudadYRango(ciudad, inicio, fin);
    }

    private ObjectId parse(String id) {
        try {
            return new ObjectId(id);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id inválido");
        }
    }
}
