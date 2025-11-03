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

import com.example.apartamentos.models.PropiedadImagenesModel;
import com.example.apartamentos.services.PropiedadImagenesService;


@RestController
@RequestMapping("/api/propiedad-imagenes")
public class PropiedadImagenesController {
    
    @Autowired
    private PropiedadImagenesService propiedadImagenesService;

    //Obtener todas las propiedades de las imagenes
    @GetMapping
    public List<PropiedadImagenesModel> getAllPropiedadImagenes() {
        return propiedadImagenesService.getAllPropiedadImagenes();
    }

    //Obtener todas las propiedades de las imagenes por id
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> getPropiedadImagenesById(@PathVariable Long id) {
        Optional<PropiedadImagenesModel> propiedadImagenes = propiedadImagenesService.getPropiedadImagenesById(id);
        return propiedadImagenes.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        
    }
    
    //Crear una nueva propiedad de imagen
    @PostMapping
    public PropiedadImagenesModel createPropiedadImagenes(@RequestBody PropiedadImagenesModel propiedadImagenes) {
    propiedadImagenes.setFechaSubida(LocalDateTime.now());
        return propiedadImagenesService.savePropiedadImagenes(propiedadImagenes);
    }

    //Actualizar la propiedad de la imagen
    @PutMapping("/{id}")
    public ResponseEntity<PropiedadImagenesModel> updatePropiedadImagenes(@PathVariable Long id, @RequestBody PropiedadImagenesModel propiedadImagenesDetails){
        Optional<PropiedadImagenesModel> propiedadImagenesOptional = propiedadImagenesService.getPropiedadImagenesById(id);
        if (propiedadImagenesOptional.isPresent()) {
            PropiedadImagenesModel propiedadImagenesExistente = propiedadImagenesOptional.get();

            //Se actualizan los campos de las propiedades de las imagenes existentes
            //Esto en caso de que en el if implementado 4 filas arriba se encontrará el id de la propiedad de la imagen
            propiedadImagenesExistente.setUrlImagen(propiedadImagenesDetails.getUrlImagen());
            propiedadImagenesExistente.setOrden(propiedadImagenesDetails.getOrden());
            propiedadImagenesExistente.setEsPrincipal(propiedadImagenesDetails.isEsPrincipal());

            //Los campos de la fecha subida o los id no se deben de actualizar

            PropiedadImagenesModel updatedPropiedadImagenes = propiedadImagenesService.savePropiedadImagenes(propiedadImagenesExistente);
            return ResponseEntity.ok(updatedPropiedadImagenes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Eliminar una propiedad de imagen
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePropiedadImagenes(@PathVariable Long id) {
        if (propiedadImagenesService.getPropiedadImagenesById(id).isPresent()) {
            propiedadImagenesService.deletePropiedadImagenes(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}