package com.example.apartamentos.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartamentos.models.PagosModel;
import com.example.apartamentos.models.ReservacionesModel;
import com.example.apartamentos.services.PagosService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.apartamentos.services.ReservacionesService;



@RestController
@RequestMapping("/api/pagos")
public class PagosController {
    
    @Autowired
    private PagosService pagosService;

    @Autowired
    private ReservacionesService reservacionesService;

    @GetMapping
    public List<PagosModel>getAllPagos(){
        return pagosService.getAllPagos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagosModel> getPagoById(@PathVariable Long id) {
        Optional<PagosModel> pagos = pagosService.getPagoById(id);
        return pagos.map(ResponseEntity::ok
            ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createPagos(@RequestBody PagosModel pagos){
        // Validar que la reservación asociada existe
        if (pagos.getIdReservacion() == null || pagos.getIdReservacion().getId() == null) {
            return ResponseEntity.badRequest().body("El idReservacion es obligatorio.");
        }
        Optional<ReservacionesModel> reservacion = reservacionesService.getReservacionesById(pagos.getIdReservacion().getId());
        if (reservacion.isEmpty()) {
            return ResponseEntity.badRequest().body("La reservación con ID " + pagos.getIdReservacion().getId() + " no existe.");
        }

        pagos.setIdReservacion(reservacion.get()); // Asignar el objeto completo
        pagos.setFechaPago(LocalDateTime.now());
        pagos.setFechaCreacion(LocalDateTime.now()); // Asignar fecha de creación
        PagosModel nuevoPago = pagosService.savePago(pagos);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPago);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagosModel> updatePagos(@PathVariable Long id, 
            @RequestBody PagosModel pagosDetails){
        Optional<PagosModel> pagosOptional = pagosService.getPagoById(id);
        if (pagosOptional.isPresent()){
            PagosModel pagoExistente = pagosOptional.get();

            pagoExistente.setMonto(pagosDetails.getMonto());
            pagoExistente.setMetodoPago(pagosDetails.getMetodoPago());
            pagoExistente.setEstadoPago(pagosDetails.getEstadoPago());
            
            PagosModel updatePago = pagosService
                .savePago(pagoExistente);
                return ResponseEntity.ok(updatePago);
        }else{
            return ResponseEntity.notFound().build();   
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagos(@PathVariable Long id){
        if (pagosService.getPagoById(id).isPresent()){
            pagosService.deletePago(id);
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
