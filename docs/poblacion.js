// poblacion.js

// evitar datos duplicados si se re-ejecuta
db.usuarios.deleteMany({});
db.vehiculos.deleteMany({});
db.servicios.deleteMany({});
db.revisiones.deleteMany({});

// 1. USUARIOS
const u1Id = ObjectId(); // usuario servicio + conductor
const u2Id = ObjectId(); // solo conductor
const u3Id = ObjectId(); // solo servicio
const u4Id = ObjectId(); // solo conductor

db.usuarios.insertMany([
  {
    _id: u1Id,
    nombre: "Julian Restrepo",
    correo: "julian.restrepo@gmail.com",
    celular: "3001112233",
    cedula: "10101010",
    roles: ["SERVICIO", "CONDUCTOR"],
    tarjetaCredito: {
      numero: "4111111111111111",
      nombreTarjeta: " JULIAN RESTREPO",
      fechaVencimiento: "12/28",
      codigoSeguridad: "123"
    }
  },
  {
    _id: u2Id,
    nombre: "Juan Manuel Rojas",
    correo: "juan.rojas@gmail.com",
    celular: "3002223344",
    cedula: "20202020",
    roles: ["CONDUCTOR"]
  },
  {
    _id: u3Id,
    nombre: "Mariana Castillo",
    correo: "mariana.castillo@gmail.com",
    celular: "3003334455",
    cedula: "30303030",
    roles: ["SERVICIO"],
    tarjetaCredito: {
      numero: "5555555555554444",
      nombreTarjeta: "MARIANA CASTILLO",
      fechaVencimiento: "07/27",
      codigoSeguridad: "456"
    }
  },
  {
    _id: u4Id,
    nombre: "Miguel Torres",
    correo: "miguel.torres@gmail.com",
    celular: "3004445566",
    cedula: "40404040",
    roles: ["CONDUCTOR"]
  }
]);

// 2. VEHICULOS
const v1Id = ObjectId();
const v2Id = ObjectId();
const v3Id = ObjectId();

db.vehiculos.insertMany([
  {
    _id: v1Id,
    tipo: "CARRO",
    marca: "Kia",
    modelo: "Rio 2022",
    color: "Blanco",
    placa: "ABC123",
    ciudadPlaca: "Bogotá",
    capacidadPasajeros: NumberInt(4),
    nivelTransporte: "ESTANDAR",
    idConductor: u2Id,
    disponibilidades: [
      {
        diaSemana: "LUNES",
        horaInicio: "07:00",
        horaFin: "19:00",
        tiposServicioHabilitados: ["PASAJEROS"]
      },
      {
        diaSemana: "MARTES",
        horaInicio: "07:00",
        horaFin: "19:00",
        tiposServicioHabilitados: ["PASAJEROS", "COMIDA"]
      }
    ]
  },
  {
    _id: v2Id,
    tipo: "CAMIONETA",
    marca: "Toyota",
    modelo: "Hilux 2021",
    color: "Negro",
    placa: "DEF456",
    ciudadPlaca: "Medellín",
    capacidadPasajeros: NumberInt(5),
    nivelTransporte: "CONFORT",
    idConductor: u1Id,
    disponibilidades: [
      {
        diaSemana: "MIERCOLES",
        horaInicio: "06:00",
        horaFin: "20:00",
        tiposServicioHabilitados: ["PASAJEROS", "MERCANCIAS"]
      }
    ]
  },
  {
    _id: v3Id,
    tipo: "MOTO",
    marca: "Bajaj",
    modelo: "Boxer 2020",
    color: "Rojo",
    placa: "GHI789",
    ciudadPlaca: "Bogotá",
    capacidadPasajeros: NumberInt(1),
    nivelTransporte: "ESTANDAR",
    idConductor: u4Id,
    disponibilidades: [
      {
        diaSemana: "JUEVES",
        horaInicio: "08:00",
        horaFin: "18:00",
        tiposServicioHabilitados: ["COMIDA", "MERCANCIAS"]
      }
    ]
  }
]);

// 3. SERVICIOS
const s1Id = ObjectId();
const s2Id = ObjectId();
const s3Id = ObjectId();
const s4Id = ObjectId();
const s5Id = ObjectId();
const s6Id = ObjectId();

db.servicios.insertMany([...]); // truncated here for brevity in python
