package com.example.apartamentos.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<ReservacionesModel> getAllReservaciones(){
        return reservacionesService.getAllReservaciones();
    }    

    @GetMapping("/{id}")
    public ResponseEntity<ReservacionesModel> getReservacionesById(@PathVariable Long id){
        Optional<ReservacionesModel> reservaciones = reservacionesService.getReservacionesById(id);
        return reservaciones.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createReservaciones(@RequestBody ReservacionesModel reservaciones){
        // --- 1. VALIDACIÓN DE DATOS DE ENTRADA ---
        // Verificamos que la propiedad y el cliente enviados en el JSON realmente existan en la base de datos.
        Optional<PropiedadesModel> propiedadOpt = propiedadesService.getPropiedadById(reservaciones.getIdPropiedad().getId());
        if (propiedadOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La propiedad con ID " + reservaciones.getIdPropiedad().getId() + " no existe.");
        }
        Optional<ClientesModel> clienteOpt = clientesService.getClienteById(reservaciones.getIdCliente().getId());
        if (clienteOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El cliente con ID " + reservaciones.getIdCliente().getId() + " no existe.");
        }

        // --- 2. VALIDACIÓN DE LÓGICA DE NEGOCIO ---
        // Verificamos que las fechas sean lógicas y que no se exceda la capacidad.
        if (reservaciones.getFechaEntrada().isAfter(reservaciones.getFechaSalida())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La fecha de entrada no puede ser posterior a la fecha de salida.");
        }
        if (reservaciones.getNumeroDeHuespedes() > propiedadOpt.get().getCapacidadPersonas()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El número de huéspedes excede la capacidad de la propiedad.");
        }

        // --- 3. ASIGNACIÓN DE VALORES POR EL SERVIDOR ---
        // El servidor asigna los valores que no deben ser controlados por el cliente.
        
        // La fecha de la reservación es la actual.
        reservaciones.setFechaReservacion(LocalDateTime.now());
        // El estado inicial siempre es PENDIENTE.
        reservaciones.setEstado(EstadoReservacion.PENDIENTE);
        // Generamos un código de reservación único.
        reservaciones.setCodigoReservacion(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        // Las fechas de check-in y check-out se quedan como null. Se actualizarán después.

        ReservacionesModel nuevaReservacion = reservacionesService.saveReservaciones(reservaciones);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReservacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservacionesModel> updateReservaciones(@PathVariable Long id, @RequestBody ReservacionesModel reservacionesDetails){
        Optional<ReservacionesModel> reservacionesOptional = reservacionesService.getReservacionesById(id);
        if (reservacionesOptional.isPresent()) {
            ReservacionesModel reservacionesExistente = reservacionesOptional.get();

            //Se van a actualizar los campos dentro de la enteidad Reservaciones
            reservacionesExistente.setNumeroDeHuespedes(reservacionesDetails.getNumeroDeHuespedes());
            reservacionesExistente.setPrecioTotal(reservacionesDetails.getPrecioTotal());
            reservacionesExistente.setEstado(reservacionesDetails.getEstado());
            reservacionesExistente.setNotas(reservacionesDetails.getNotas());

            ReservacionesModel updatedReservaciones = reservacionesService.saveReservaciones(reservacionesExistente);
            return ResponseEntity.ok(updatedReservaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservaciones(@PathVariable Long id){
        if (reservacionesService.getReservacionesById(id).isPresent()) {
            reservacionesService.deleteReservaciones(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
    
    
    
