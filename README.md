# Airline Ticket Manager

## Dominios CRUD

- Un avión tiene:
  - N capacidad
  - Modelo: _(Airbus A330-200 (299 pax), Boeing 737-800 (180 pax), ...)_

- Un pasajero tiene:
  - Identificador
  - Código (NIF, NIE, Pasaporte, ...)
  - Nombre
  - Apellidos
  - Correo electronico
  - Dirección

- Un vuelo tiene:
  - Origen
  - Destinación
  - Fecha
  - 1 Avión
  - N billetes disponibles (fijado por N capacidad del avión)

- Un billete tiene:
  - 1 pasajero asociado
  - 1 vuelo asociado
  - Precio/importe
  - Tasas
