package uniandes.edu.co.alpescab_mongo.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Disponibilidad;
import uniandes.edu.co.alpescab_mongo.model.Servicio;
import uniandes.edu.co.alpescab_mongo.model.Usuario;
import uniandes.edu.co.alpescab_mongo.model.Vehiculo;
import uniandes.edu.co.alpescab_mongo.repository.ServicioRepository;
import uniandes.edu.co.alpescab_mongo.repository.UsuarioRepository;
import uniandes.edu.co.alpescab_mongo.repository.VehiculoRepository;

@Service
@RequiredArgsConstructor
public class OperacionService {

    private final UsuarioRepository usuarioRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ServicioRepository servicioRepository;

    public Usuario registrarUsuarioServicio(Usuario usuario) {
        if (!usuario.getRoles().contains("SERVICIO")) {
            usuario.getRoles().add("SERVICIO");
        }
        return usuarioRepository.save(usuario);
    }

    public Usuario registrarUsuarioConductor(Usuario usuario) {
        if (!usuario.getRoles().contains("CONDUCTOR")) {
            usuario.getRoles().add("CONDUCTOR");
        }
        return usuarioRepository.save(usuario);
    }

    public Vehiculo registrarVehiculo(ObjectId idConductor, Vehiculo vehiculo) {
        Usuario conductor = usuarioRepository.findById(idConductor)
                .filter(u -> u.getRoles().contains("CONDUCTOR"))
                .orElseThrow(() -> new IllegalArgumentException("Conductor no existe o no tiene rol CONDUCTOR"));
        vehiculo.setIdConductor(conductor.getId());
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo agregarDisponibilidad(ObjectId idVehiculo, Disponibilidad disponibilidad) {
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

        validarTraslape(vehiculo.getDisponibilidades(), disponibilidad);
        vehiculo.getDisponibilidades().add(disponibilidad);
        return vehiculoRepository.save(vehiculo);
    }

    public Vehiculo modificarDisponibilidad(ObjectId idVehiculo, int index, Disponibilidad disponibilidad) {
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

        List<Disponibilidad> actuales = vehiculo.getDisponibilidades();
        if (index < 0 || index >= actuales.size()) {
            throw new IllegalArgumentException("Índice de disponibilidad inválido");
        }
         
        List<Disponibilidad> otras = actuales.stream()
                .filter(d -> !d.equals(actuales.get(index)))
                .collect(Collectors.toList());
        validarTraslape(otras, disponibilidad);
        actuales.set(index, disponibilidad);
        return vehiculoRepository.save(vehiculo);
    }

    public Servicio solicitarServicio(Servicio servicio) {
        if (servicio.getFechaSolicitud() == null) {
            servicio.setFechaSolicitud(new Date());
        }
        servicio.setEstado("PENDIENTE");

         
        if (servicio.getIdVehiculo() != null) {
            Vehiculo vehiculo = vehiculoRepository.findById(servicio.getIdVehiculo())
                    .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
            validarConductorDisponible(vehiculo.getIdConductor(), servicio.getFechaSolicitud());
            servicio.setIdConductor(vehiculo.getIdConductor());
        } else if (servicio.getIdConductor() != null) {
            validarConductorDisponible(servicio.getIdConductor(), servicio.getFechaSolicitud());
        } else {
            asignarPrimerConductorDisponible(servicio);
        }
        return servicioRepository.save(servicio);
    }

    public Servicio finalizarServicio(ObjectId idServicio, Date horaFin, Double costoTotal, Double distanciaKm) {
        Servicio servicio = servicioRepository.findById(idServicio)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado"));
        servicio.setHoraFin(horaFin);
        servicio.setCostoTotal(costoTotal != null ? costoTotal : servicio.getCostoTotal());
        servicio.setDistanciaKm(distanciaKm != null ? distanciaKm : servicio.getDistanciaKm());
        if (servicio.getHoraInicio() != null && horaFin != null) {
            long minutes = (horaFin.getTime() - servicio.getHoraInicio().getTime()) / 60000;
            servicio.setDuracionMin((int) minutes);
        }
        servicio.setEstado("FINALIZADO");
        return servicioRepository.save(servicio);
    }

    private void validarConductorDisponible(ObjectId idConductor, Date fecha) {
        if (servicioRepository.existsByIdConductorAndEstado(idConductor, "EN_CURSO")) {
            throw new IllegalStateException("Conductor actualmente en servicio");
        }

        LocalDate dia = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        System.out.println(">>> [validarConductorDisponible]");
        System.out.println("    idConductor = " + idConductor);
        System.out.println("    fecha       = " + fecha);
        System.out.println("    dia         = " + dia + " (dayOfWeek=" + dia.getDayOfWeek() + ")");

        List<Vehiculo> vehiculos = vehiculoRepository.findAll().stream()
                .filter(v -> idConductor.equals(v.getIdConductor()))
                .collect(Collectors.toList());

        System.out.println("    vehiculos del conductor = " + vehiculos.size());
        vehiculos.forEach(v -> {
            System.out.println("      - vehiculo " + v.getId());
            System.out.println("        disponibilidades = " + v.getDisponibilidades());
        });

        boolean disponible = vehiculos.stream().anyMatch(v -> {
            boolean r = estaDisponibleEnDia(v, dia);
            System.out.println("        -> estaDisponibleEnDia(" + v.getId() + ", " + dia + ") = " + r);
            return r;
        });

        System.out.println("    resultado disponible = " + disponible);

        if (!disponible) {
            throw new IllegalStateException("Conductor sin disponibilidad para la fecha indicada");
        }
    }


    private boolean estaDisponibleEnDia(Vehiculo vehiculo, LocalDate dia) {
        if (CollectionUtils.isEmpty(vehiculo.getDisponibilidades())) {
            return false;
        }

        String diaSemana = switch (dia.getDayOfWeek()) {
            case MONDAY    -> "LUNES";
            case TUESDAY   -> "MARTES";
            case WEDNESDAY -> "MIERCOLES"; // sin tilde, como en tu BD
            case THURSDAY  -> "JUEVES";
            case FRIDAY    -> "VIERNES";
            case SATURDAY  -> "SABADO";
            case SUNDAY    -> "DOMINGO";
        };

        return vehiculo.getDisponibilidades().stream()
                .anyMatch(d -> diaSemana.equalsIgnoreCase(d.getDiaSemana()));
    }


    private void asignarPrimerConductorDisponible(Servicio servicio) {
        LocalDate dia = servicio.getFechaSolicitud().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Optional<Vehiculo> vehiculo = vehiculoRepository.findAll().stream()
                .sorted(Comparator.comparing(Vehiculo::getId))  
                .filter(v -> !servicioRepository.existsByIdConductorAndEstado(v.getIdConductor(), "EN_CURSO"))
                .filter(v -> estaDisponibleEnDia(v, dia))
                .findFirst();
        Vehiculo seleccionado = vehiculo.orElseThrow(() -> new IllegalStateException("No hay conductores disponibles"));
        servicio.setIdVehiculo(seleccionado.getId());
        servicio.setIdConductor(seleccionado.getIdConductor());
    }

    private void validarTraslape(List<Disponibilidad> actuales, Disponibilidad nueva) {
        if (CollectionUtils.isEmpty(actuales)) {
            return;
        }
        for (Disponibilidad d : actuales) {
            if (d.getDiaSemana().equalsIgnoreCase(nueva.getDiaSemana())) {
                boolean solapa = horaDentro(nueva.getHoraInicio(), d.getHoraInicio(), d.getHoraFin())
                        || horaDentro(nueva.getHoraFin(), d.getHoraInicio(), d.getHoraFin())
                        || horaDentro(d.getHoraInicio(), nueva.getHoraInicio(), nueva.getHoraFin());
                if (solapa) {
                    throw new IllegalArgumentException("Disponibilidad se superpone con una existente");
                }
            }
        }
    }

    private boolean horaDentro(String h, String inicio, String fin) {
        return h.compareTo(inicio) >= 0 && h.compareTo(fin) <= 0;
    }
}
