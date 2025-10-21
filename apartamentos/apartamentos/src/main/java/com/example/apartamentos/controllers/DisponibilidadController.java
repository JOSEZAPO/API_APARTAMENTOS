package com.example.apartamentos.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartamentos.models.DisponibilidadModel;
import com.example.apartamentos.services.DisponibilidadService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/disponibilidad")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    @GetMapping
    public List<DisponibilidadModel> getAllDisponibilidad() {
        return disponibilidadService.getAllDisponibilidad();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> getDisponibilidadById(@PathVariable Long id) {
        Optional<DisponibilidadModel> disponibilidad = disponibilidadService.getDisponibilidadById(id);
        return disponibilidad.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public DisponibilidadModel createDisponibilidad(@RequestBody DisponibilidadModel disponibilidad) {

        disponibilidad.setFecha(LocalDateTime.now());
        return disponibilidadService.saveDisponibilidad(disponibilidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DisponibilidadModel> updateDisponibilidad(@PathVariable Long id,
            @RequestBody DisponibilidadModel disponibilidadDetails) {
        Optional<DisponibilidadModel> disponibilidadOptional = disponibilidadService.getDisponibilidadById(id);
        if (disponibilidadOptional.isPresent()) {
            DisponibilidadModel disponibilidadExistente = disponibilidadOptional.get();

            disponibilidadExistente.setFecha(disponibilidadDetails.getFecha());
            disponibilidadExistente.setDisponible(disponibilidadDetails.isDisponible());
            disponibilidadExistente.setPrecioEspecial(disponibilidadDetails.getPrecioEspecial());

            DisponibilidadModel updatedDisponibilidad = disponibilidadService
                    .saveDisponibilidad(disponibilidadExistente);
            return ResponseEntity.ok(updatedDisponibilidad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisponibilidad(@PathVariable Long id) {
        if (disponibilidadService.getDisponibilidadById(id).isPresent()) {
            disponibilidadService.deleteDisponibilidad(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
