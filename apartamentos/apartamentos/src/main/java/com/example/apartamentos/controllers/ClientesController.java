package com.example.apartamentos.controllers;

import com.example.apartamentos.services.ClientesService;
import com.example.apartamentos.services.PropiedadesService;
import com.example.apartamentos.models.TipoCliente;
import com.example.apartamentos.models.ClientesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.apartamentos.models.PropiedadesModel;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClientesController {
    @Autowired
    private ClientesService clienteService;

    @Autowired
    private PropiedadesService propiedadesService;

    // Obtener todos los clientes
    @GetMapping
    public List<ClientesModel> getAllClientes() {
        return clienteService.getAllClientes();
    }

    // Obtener un cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClientesModel> getClienteById(@PathVariable Long id) {
        Optional<ClientesModel> cliente = clienteService.getClienteById(id);
        return cliente.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtener clientes por tipo (PROPIETARIO o HUESPED)
    @GetMapping("/buscar")
    public List<ClientesModel> getClientesByTipo(@RequestParam("tipo") TipoCliente tipo) {
        return clienteService.getClientesByTipo(tipo);
    }

    // Obtener todas las propiedades de un cliente específico
    @GetMapping("/{clienteId}/propiedades")
    public ResponseEntity<List<PropiedadesModel>> getPropiedadesByCliente(@PathVariable("clienteId") Long clienteId) {
        // Primero, verificamos si el cliente existe para dar una respuesta 404
        // adecuada.
        Optional<ClientesModel> cliente = clienteService.getClienteById(clienteId);
        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PropiedadesModel> propiedades = propiedadesService.getPropiedadesByPropietarioId(clienteId);
        return ResponseEntity.ok(propiedades);
    }

    // Crear un nuevo cliente
    @PostMapping
    public ClientesModel createCliente(@RequestBody ClientesModel cliente) {
        // Establecemos la fecha de registro y el último acceso al momento de la
        // creación.
        cliente.setFechaRegistro(LocalDateTime.now());
        cliente.setUltimoAcceso(LocalDateTime.now());
        return clienteService.saveCliente(cliente);
    }

    // Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClientesModel> updateCliente(@PathVariable Long id,
            @RequestBody ClientesModel clienteDetails) {
        Optional<ClientesModel> clienteOptional = clienteService.getClienteById(id);
        if (clienteOptional.isPresent()) {
            ClientesModel clienteExistente = clienteOptional.get();

            // Actualizamos los campos del cliente existente con los detalles proporcionados
            clienteExistente.setTipo(clienteDetails.getTipo());
            clienteExistente.setNombre(clienteDetails.getNombre());
            clienteExistente.setApellidoPat(clienteDetails.getApellidoPat());
            clienteExistente.setApellidoMat(clienteDetails.getApellidoMat());
            clienteExistente.setEmail(clienteDetails.getEmail());
            clienteExistente.setTelefono(clienteDetails.getTelefono());
            clienteExistente.setFechaDeNacimiento(clienteDetails.getFechaDeNacimiento());
            clienteExistente.setINE(clienteDetails.getINE());
            clienteExistente.setDireccion(clienteDetails.getDireccion());
            clienteExistente.setEstatus(clienteDetails.getEstatus());

            // Campos como la contraseña o la fecha de registro usualmente no se actualizan
            // de esta forma.
            // Si se desea actualizar la contraseña, se debería hacer en un endpoint
            // separado y seguro.
            // clienteExistente.setPassword(clienteDetails.getPassword());

            ClientesModel updatedCliente = clienteService.saveCliente(clienteExistente);
            return ResponseEntity.ok(updatedCliente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        if (clienteService.getClienteById(id).isPresent()) {
            clienteService.deleteCliente(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
