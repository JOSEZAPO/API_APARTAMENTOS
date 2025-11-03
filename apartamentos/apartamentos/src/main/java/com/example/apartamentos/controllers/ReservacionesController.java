package com.example.apartamentos.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartamentos.models.ClientesModel;
import com.example.apartamentos.models.EstadoReservacion;
import com.example.apartamentos.models.PropiedadesModel;
import com.example.apartamentos.models.ReservacionesModel;
import com.example.apartamentos.services.ClientesService;
import com.example.apartamentos.services.PropiedadesService;
import com.example.apartamentos.services.ReservacionesService;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/reservaciones")
public class ReservacionesController {

    @Autowired
    private ReservacionesService reservacionesService;

    @Autowired
    private PropiedadesService propiedadesService;

    @Autowired
    private ClientesService clientesService;

    @GetMapping
    public List<ReservacionesModel> getAllReservaciones() {
        return reservacionesService.getAllReservaciones();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservacionesModel> getReservacionesById(@PathVariable Long id) {
        Optional<ReservacionesModel> reservaciones = reservacionesService.getReservacionesById(id);
        return reservaciones.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<ReservacionesModel>> getReservacionesByCliente(@PathVariable Long clienteId) {
        // Primero, verificamos si el cliente existe para evitar búsquedas innecesarias
        // y devolver un 404 si no se encuentra.
        Optional<ClientesModel> cliente = clientesService.getClienteById(clienteId);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ReservacionesModel> reservaciones = reservacionesService.getReservacionesByClienteId(clienteId);
        return ResponseEntity.ok(reservaciones);
    }

    @GetMapping("/propiedad/{propiedadId}")
    public ResponseEntity<List<ReservacionesModel>> getReservacionesByPropiedad(@PathVariable Long propiedadId) {
        // Verificamos si la propiedad existe para devolver un 404 si no se encuentra.
        Optional<PropiedadesModel> propiedad = propiedadesService.getPropiedadById(propiedadId);
        if (propiedad.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ReservacionesModel> reservaciones = reservacionesService.getReservacionesByPropiedadId(propiedadId);
        return ResponseEntity.ok(reservaciones);
    }

    @GetMapping("/buscar-por-fecha")
    public List<ReservacionesModel> getReservacionesPorFecha(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return reservacionesService.getReservacionesPorFecha(fecha);
    }

    @PostMapping
    public ResponseEntity<?> createReservaciones(@RequestBody ReservacionesModel reservaciones) {
        // --- 0. PRE-VALIDACIÓN ---
        if (reservaciones.getIdPropiedad() == null || reservaciones.getIdCliente() == null) {
            return ResponseEntity.badRequest().body("Los IDs de propiedad y cliente son obligatorios.");
        }

        // --- 1. VALIDACIÓN DE DATOS DE ENTRADA ---
        // Verificamos que la propiedad y el cliente enviados en el JSON realmente
        // existan en la base de datos.
        Optional<PropiedadesModel> propiedadOpt = propiedadesService
                .getPropiedadById(reservaciones.getIdPropiedad().getId());
        if (propiedadOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La propiedad con ID " + reservaciones.getIdPropiedad().getId() + " no existe.");
        }
        Optional<ClientesModel> clienteOpt = clientesService.getClienteById(reservaciones.getIdCliente().getId());
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El cliente con ID " + reservaciones.getIdCliente().getId() + " no existe.");
        }

        // --- 2. VALIDACIÓN DE LÓGICA DE NEGOCIO ---
        // Verificamos que las fechas sean lógicas y que no se exceda la capacidad.
        if (reservaciones.getFechaEntrada().isAfter(reservaciones.getFechaSalida())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La fecha de entrada no puede ser posterior a la fecha de salida.");
        }
        if (reservaciones.getNumeroDeHuespedes() > propiedadOpt.get().getCapacidadPersonas()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El número de huéspedes excede la capacidad de la propiedad.");
        }

        // --- 3. CREACIÓN Y ASIGNACIÓN DE VALORES ---
        // Creamos una nueva instancia para asegurar que solo guardamos lo que
        // controlamos.
        ReservacionesModel nuevaReservacion = new ReservacionesModel();

        // Asignamos las entidades completas que ya validamos.
        nuevaReservacion.setIdPropiedad(propiedadOpt.get());
        nuevaReservacion.setIdCliente(clienteOpt.get());

        // Asignamos los valores que vienen del cliente y que son seguros.
        nuevaReservacion.setFechaEntrada(reservaciones.getFechaEntrada());
        nuevaReservacion.setFechaSalida(reservaciones.getFechaSalida());
        nuevaReservacion.setNumeroDeHuespedes(reservaciones.getNumeroDeHuespedes());

        // Calculamos el precio total basado en el precio por noche y la duración.
        long noches = java.time.temporal.ChronoUnit.DAYS.between(reservaciones.getFechaEntrada(),
                reservaciones.getFechaSalida());
        double precioTotal = propiedadOpt.get().getPrecioNoche() * (noches > 0 ? noches : 1);
        nuevaReservacion.setPrecioTotal(precioTotal);

        // Asignamos los valores controlados por el servidor.
        nuevaReservacion.setFechaReservacion(LocalDateTime.now());
        nuevaReservacion.setEstado(EstadoReservacion.PENDIENTE);
        nuevaReservacion.setCodigoReservacion(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        ReservacionesModel reservacionGuardada = reservacionesService.saveReservaciones(nuevaReservacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservacionGuardada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReservaciones(@PathVariable Long id,
            @RequestBody ReservacionesModel reservacionesDetails) {
        Optional<ReservacionesModel> reservacionesOptional = reservacionesService.getReservacionesById(id);
        if (reservacionesOptional.isPresent()) {
            ReservacionesModel reservacionesExistente = reservacionesOptional.get();

            // --- VALIDACIÓN DE LÓGICA DE NEGOCIO ---
            // Validamos que el nuevo número de huéspedes no exceda la capacidad de la
            // propiedad.
            if (reservacionesDetails.getNumeroDeHuespedes() > reservacionesExistente.getIdPropiedad()
                    .getCapacidadPersonas()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El número de huéspedes excede la capacidad de la propiedad.");
            }

            // Se van a actualizar los campos dentro de la entidad Reservaciones
            reservacionesExistente.setNumeroDeHuespedes(reservacionesDetails.getNumeroDeHuespedes());
            reservacionesExistente.setNotas(reservacionesDetails.getNotas());

            ReservacionesModel updatedReservaciones = reservacionesService.saveReservaciones(reservacionesExistente);
            return ResponseEntity.ok(updatedReservaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/fechas")
    public ResponseEntity<?> updateFechasReservaciones(@PathVariable Long id,
            @RequestBody ReservacionesModel reservacionesDetails) {
        Optional<ReservacionesModel> reservacionesOptional = reservacionesService.getReservacionesById(id);

        // --- VALIDACIÓN DE LÓGICA DE NEGOCIO ---
        if (reservacionesDetails.getFechaEntrada().isAfter(reservacionesDetails.getFechaSalida())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("La fecha de entrada no puede ser posterior a la fecha de salida.");
        }

        if (reservacionesOptional.isPresent()) {
            ReservacionesModel reservacionesExistente = reservacionesOptional.get();
            reservacionesExistente.setFechaEntrada(reservacionesDetails.getFechaEntrada());
            reservacionesExistente.setFechaSalida(reservacionesDetails.getFechaSalida());
            reservacionesExistente.setFechaChecking(reservacionesDetails.getFechaChecking());
            reservacionesExistente.setFechaCheckout(reservacionesDetails.getFechaCheckout());

            ReservacionesModel updatedFechasReservaciones = reservacionesService.saveReservaciones(reservacionesExistente);
            return ResponseEntity.ok(updatedFechasReservaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> updateEstadoReservacion(@PathVariable Long id,
            @RequestBody ReservacionesModel reservacionesDetails) {
        Optional<ReservacionesModel> reservacionesOptional = reservacionesService.getReservacionesById(id);
        if (reservacionesOptional.isPresent()) {
            ReservacionesModel reservacionesExistente = reservacionesOptional.get();

            // Actualizamos únicamente el estado de la reservación
            reservacionesExistente.setEstado(reservacionesDetails.getEstado());

            ReservacionesModel updatedReservacion = reservacionesService.saveReservaciones(reservacionesExistente);
            return ResponseEntity.ok(updatedReservacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservaciones(@PathVariable Long id) {
        if (reservacionesService.getReservacionesById(id).isPresent()) {
            reservacionesService.deleteReservaciones(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
