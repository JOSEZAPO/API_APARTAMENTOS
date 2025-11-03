package com.example.apartamentos.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartamentos.models.ResenasModel;
import com.example.apartamentos.services.ResenasService;

@RestController
@RequestMapping("/api/resenas")
public class ResenasController {

    @Autowired
    private ResenasService resenasService;

    @GetMapping
    public List<ResenasModel> getAllResenas() {
        return resenasService.getAllResenas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResenasModel> getResenasById(@PathVariable Long id) {
        Optional<ResenasModel> resena = resenasService.getResenaById(id);
        return resena.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResenasModel createResenas(@RequestBody ResenasModel resena) {

        resena.setFechaResena(LocalDateTime.now());
        resena.setFechaRespuesta(LocalDateTime.now());

        return resenasService.saveResena(resena);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResenasModel> updateResenas(@PathVariable Long id,
            @RequestBody ResenasModel resenaDetails) {
        Optional<ResenasModel> resenaOptional = resenasService.getResenaById(id);
        if (resenaOptional.isPresent()) {
            ResenasModel resenaExistente = resenaOptional.get();

            resenaExistente.setCalificacionLimpieza(resenaDetails.getCalificacionLimpieza());
            resenaExistente.setCalificacionUbicacion(resenaDetails.getCalificacionUbicacion());
            resenaExistente.setCalificacionComunicacion(resenaDetails.getCalificacionComunicacion());
            resenaExistente.setCalificacionGeneral(resenaDetails.getCalificacionGeneral());
            resenaExistente.setComentario(resenaDetails.getComentario());
            resenaExistente.setRespuestaPropietario(resenaDetails.getRespuestaPropietario());

            ResenasModel updatedResena = resenasService
                    .saveResena(resenaExistente);
            return ResponseEntity.ok(updatedResena);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResenas(@PathVariable Long id) {
        if (resenasService.getResenaById(id).isPresent()) {
            resenasService.deleteResena(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
