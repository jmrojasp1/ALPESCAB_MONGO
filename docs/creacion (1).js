// creacion.js

use alpescab;

// Usuarios
db.createCollection("usuarios", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: ["nombre", "correo", "celular", "cedula", "roles"],
      properties: {
        nombre: { bsonType: "string" },
        correo: { bsonType: "string" },
        celular: { bsonType: "string" },
        cedula: { bsonType: "string" },
        roles: {
          bsonType: "array",
          items: {
            bsonType: "string",
            enum: ["SERVICIO", "CONDUCTOR"]
          }
        },
        tarjetaCredito: {
          bsonType: ["object", "null"],
          properties: {
            numero: { bsonType: "string" },
            nombreTarjeta: { bsonType: "string" },
            fechaVencimiento: { bsonType: "string" },
            codigoSeguridad: { bsonType: "string" }
          }
        }
      }
    }
  }
});

// vehiculos
db.createCollection("vehiculos", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "tipo", "marca", "modelo", "color",
        "placa", "ciudadPlaca",
        "capacidadPasajeros", "nivelTransporte",
        "idConductor"
      ],
      properties: {
        tipo: { bsonType: "string", enum: ["CARRO", "CAMIONETA", "MOTO"] },
        marca: { bsonType: "string" },
        modelo: { bsonType: "string" },
        color: { bsonType: "string" },
        placa: { bsonType: "string" },
        ciudadPlaca: { bsonType: "string" },
        capacidadPasajeros: { bsonType: "int" },
        nivelTransporte: {
          bsonType: "string",
          enum: ["ESTANDAR", "CONFORT", "LARGE"]
        },
        idConductor: { bsonType: "objectId" },
        disponibilidades: {
          bsonType: "array",
          items: {
            bsonType: "object",
            required: ["diaSemana", "horaInicio", "horaFin", "tiposServicioHabilitados"],
            properties: {
              diaSemana: { bsonType: "string" },
              horaInicio: { bsonType: "string" },
              horaFin: { bsonType: "string" },
              tiposServicioHabilitados: {
                bsonType: "array",
                items: {
                  bsonType: "string",
                  enum: ["PASAJEROS", "COMIDA", "MERCANCIAS"]
                }
              }
            }
          }
        }
      }
    }
  }
});

// Servicios
db.createCollection("servicios", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "tipoServicio",
        "idUsuarioServicio",
        "idConductor",
        "idVehiculo",
        "puntosRuta",
        "distanciaKm",
        "costoTotal",
        "fechaSolicitud",
        "estado"
      ],
      properties: {
        tipoServicio: {
          bsonType: "string",
          enum: ["PASAJEROS", "COMIDA", "MERCANCIAS"]
        },
        nivelTransporte: {
          bsonType: ["string", "null"],
          enum: ["ESTANDAR", "CONFORT", "LARGE", null]
        },
        idUsuarioServicio: { bsonType: "objectId" },
        idConductor: { bsonType: "objectId" },
        idVehiculo: { bsonType: "objectId" },
        puntosRuta: {
          bsonType: "array",
          minItems: 1,
          items: {
            bsonType: "object",
            required: ["orden", "latitud", "longitud", "direccion", "ciudad"],
            properties: {
              orden: { bsonType: "int" },
              latitud: { bsonType: "double" },
              longitud: { bsonType: "double" },
              direccion: { bsonType: "string" },
              ciudad: { bsonType: "string" }
            }
          }
        },
        distanciaKm: { bsonType: "double" },
        costoTotal: { bsonType: "double" },
        fechaSolicitud: { bsonType: "date" },
        horaInicio: { bsonType: ["date", "null"] },
        horaFin: { bsonType: ["date", "null"] },
        duracionMin: { bsonType: ["int", "null"] },
        ciudadPrincipal: { bsonType: "string" },
        estado: {
          bsonType: "string",
          enum: ["PENDIENTE", "EN_CURSO", "FINALIZADO"]
        }
      }
    }
  }
});

// Resena
db.createCollection("revisiones", {
  validator: {
    $jsonSchema: {
      bsonType: "object",
      required: [
        "tipo",
        "idServicio",
        "idRevisor",
        "idRevisado",
        "calificacion",
        "fecha"
      ],
      properties: {
        tipo: {
          bsonType: "string",
          enum: ["CONDUCTOR_A_USUARIO", "USUARIO_A_CONDUCTOR"]
        },
        idServicio: { bsonType: "objectId" },
        idRevisor: { bsonType: "objectId" },
        idRevisado: { bsonType: "objectId" },
        calificacion: { bsonType: "int" },
        comentario: { bsonType: ["string", "null"] },
        fecha: { bsonType: "date" }
      }
    }
  }
});
