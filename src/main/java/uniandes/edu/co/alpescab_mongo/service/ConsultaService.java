package uniandes.edu.co.alpescab_mongo.service;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uniandes.edu.co.alpescab_mongo.model.Servicio;
import uniandes.edu.co.alpescab_mongo.repository.ServicioRepository;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ServicioRepository servicioRepository;
    private final MongoTemplate mongoTemplate;

    public List<Servicio> historicoPorUsuario(ObjectId idUsuario) {
        return servicioRepository.findByIdUsuarioServicioOrderByFechaSolicitudDesc(idUsuario);
    }

    public List<TopConductor> topConductores() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.group("idConductor").count().as("totalServicios"),
                Aggregation.sort(Sort.Direction.DESC, "totalServicios"),
                Aggregation.limit(20));
        AggregationResults<TopConductor> results = mongoTemplate.aggregate(agg, "servicios", TopConductor.class);
        return results.getMappedResults();
    }

    public List<ServiciosCiudad> serviciosPorCiudadYRango(String ciudad, Date inicio, Date fin) {
        Criteria criteria = Criteria.where("ciudadPrincipal").is(ciudad)
                .andOperator(Criteria.where("fechaSolicitud").gte(inicio), Criteria.where("fechaSolicitud").lte(fin));

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("tipoServicio", "nivelTransporte").count().as("total"),
                Aggregation.sort(Sort.Direction.DESC, "total"));
        AggregationResults<ServiciosCiudad> results = mongoTemplate.aggregate(agg, "servicios", ServiciosCiudad.class);

        long totalServicios = results.getMappedResults().stream().mapToLong(ServiciosCiudad::getTotal).sum();
        results.getMappedResults().forEach(r -> r.setPorcentaje(totalServicios == 0 ? 0 : (r.getTotal() * 100.0 / totalServicios)));
        return results.getMappedResults();
    }

    @lombok.Data
    public static class TopConductor {
        private ObjectId idConductor;
        private long totalServicios;
    }

    @lombok.Data
    public static class ServiciosCiudad {
        private String tipoServicio;
        private String nivelTransporte;
        private long total;
        private double porcentaje;
    }
}
