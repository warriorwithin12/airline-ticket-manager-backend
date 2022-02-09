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
  
 ## DONE list:
 - [x] **CRUD** (HTTP: Get, Post, Patch, Delete).
 - [x] **BaseModel** and childs (entities model).
 - [x] **BaseService** and childs (autowired services to interact with JPA repositories).
 - [x] **BaseCRUDRestController** and childs.
 - [x] **Java Token authentication** (in jwt-auth branch)
 - [x] **Spring security**. User role based authentications with JDBC implementation.
 
 ## TODO list:
 - [ ] Testing: 
    - Unitario
    - Integraciones
 - [ ] Securización:
    - OAuth2
    - Google Auth
 - [ ] Add parent entity to REST responses:
     - Field Success/Unsuccess
     - Error handling
 - [ ] Docker
