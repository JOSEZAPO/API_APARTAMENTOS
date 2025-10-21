package com.example.apartamentos.controllers;

import com.example.apartamentos.models.ClientesModel;
import com.example.apartamentos.models.TipoPropiedad;
import com.example.apartamentos.models.StatusCliente;
import com.example.apartamentos.models.PropiedadesModel;
import com.example.apartamentos.services.ClientesService;
import com.example.apartamentos.services.PropiedadesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/propiedades")
public class PropiedadesController {

    @Autowired
    private PropiedadesService propiedadesService;

    @Autowired
    private ClientesService clientesService;

    // Obtener todas las propiedades
    @GetMapping
    public List<PropiedadesModel> getAllPropiedades() {
        // Asumiendo que el método en PropiedadesService se llama getAllPropiedades()
        return propiedadesService.getAllPropiedades();
    }

    // Obtener una propiedad por ID
    @GetMapping("/{id}")
    public ResponseEntity<PropiedadesModel> getPropiedadById(@PathVariable Long id) {
        // Asumiendo que el método en PropiedadesService se llama getPropiedadById()
        Optional<PropiedadesModel> propiedad = propiedadesService.getPropiedadById(id);
        return propiedad.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener propiedades por tipo (CASA o DEPARTAMENTO)
    @GetMapping("/buscar")
    public List<PropiedadesModel> getPropiedadesByTipo(@RequestParam("tipo") TipoPropiedad tipo) {
        return propiedadesService.getPropiedadesByTipo(tipo);
    }

    // Crear una nueva propiedad
    @PostMapping
    public ResponseEntity<?> createPropiedad(@RequestBody PropiedadesModel propiedad) {
        // Validar que el propietario (Cliente) existe
        if (propiedad.getIdPropietario() == null || propiedad.getIdPropietario().getId() == null) {
            return ResponseEntity.badRequest().body("El idPropietario es obligatorio.");
        }
        Optional<ClientesModel> propietario = clientesService.getClienteById(propiedad.getIdPropietario().getId());
        if (propietario.isEmpty()) {
            return ResponseEntity.badRequest().body("El cliente propietario con ID " + propiedad.getIdPropietario().getId() + " no existe.");
        }

        propiedad.setIdPropietario(propietario.get()); // Asignar el objeto completo
        propiedad.setFechaCreacion(LocalDateTime.now());
        propiedad.setFechaActualizacion(LocalDateTime.now());
        PropiedadesModel nuevaPropiedad = propiedadesService.savePropiedad(propiedad);
        return ResponseEntity.ok(nuevaPropiedad);
    }

    // Actualizar una propiedad existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePropiedad(@PathVariable Long id, @RequestBody PropiedadesModel propiedadDetails) {
        Optional<PropiedadesModel> propiedadOptional = propiedadesService.getPropiedadById(id);
        if (propiedadOptional.isPresent()) {
            PropiedadesModel propiedadExistente = propiedadOptional.get();

            // Validar y actualizar el propietario si se proporciona
            if (propiedadDetails.getIdPropietario() != null && propiedadDetails.getIdPropietario().getId() != null) {
                Optional<ClientesModel> propietario = clientesService.getClienteById(propiedadDetails.getIdPropietario().getId());
                if (propietario.isEmpty()) {
                    return ResponseEntity.badRequest().body("El cliente propietario con ID " + propiedadDetails.getIdPropietario().getId() + " no existe.");
                }
                propiedadExistente.setIdPropietario(propietario.get());
            }

            // Actualizamos todos los campos necesarios
            propiedadExistente.setTipo(propiedadDetails.getTipo());
            propiedadExistente.setTitulo(propiedadDetails.getTitulo());
            propiedadExistente.setDescripcion(propiedadDetails.getDescripcion());
            propiedadExistente.setDireccion(propiedadDetails.getDireccion());
            propiedadExistente.setCiudad(propiedadDetails.getCiudad());
            propiedadExistente.setCodigoPostal(propiedadDetails.getCodigoPostal());
            propiedadExistente.setPais(propiedadDetails.getPais());
            propiedadExistente.setLatitud(propiedadDetails.getLatitud());
            propiedadExistente.setLongitud(propiedadDetails.getLongitud());
            propiedadExistente.setPrecioNoche(propiedadDetails.getPrecioNoche());
            propiedadExistente.setCapacidadPersonas(propiedadDetails.getCapacidadPersonas());
            propiedadExistente.setNumeroHabitaciones(propiedadDetails.getNumeroHabitaciones());
            propiedadExistente.setNumeroSanitarios(propiedadDetails.getNumeroSanitarios());
            propiedadExistente.setMetrosCuadrados(propiedadDetails.getMetrosCuadrados());
            propiedadExistente.setComodidades(propiedadDetails.getComodidades());
            propiedadExistente.setNormas(propiedadDetails.getNormas());
            propiedadExistente.setEstatus(propiedadDetails.getEstatus());
            propiedadExistente.setFechaActualizacion(LocalDateTime.now());

            PropiedadesModel updatedPropiedad = propiedadesService.savePropiedad(propiedadExistente);
            return ResponseEntity.ok(updatedPropiedad);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una propiedad
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropiedad(@PathVariable Long id) {
        Optional<PropiedadesModel> propiedadOptional = propiedadesService.getPropiedadById(id);
        if (propiedadOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        PropiedadesModel propiedad = propiedadOptional.get();
        ClientesModel propietario = propiedad.getIdPropietario();

        // Validar que el propietario no esté activo.
        // Solo se puede eliminar una propiedad si el cliente propietario ya no es un cliente activo.
        if (propietario != null && propietario.getEstatus() == StatusCliente.ACTIVO) {
            return ResponseEntity.badRequest().body("No se puede eliminar la propiedad porque el propietario con ID " + propietario.getId() + " sigue activo.");
        }

        // Si el propietario no existe o no está activo, se puede eliminar la propiedad.
        propiedadesService.deletePropiedad(id);
        return ResponseEntity.noContent().build();
    }
}
