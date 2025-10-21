package com.example.apartamentos.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apartamentos.models.MensajesModel;
import com.example.apartamentos.models.ClientesModel;
import com.example.apartamentos.models.ReservacionesModel;
import com.example.apartamentos.services.MensajesService;
import com.example.apartamentos.services.ClientesService;
import com.example.apartamentos.services.ReservacionesService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/mensajes")
public class MensajesController {
    
    @Autowired
    private MensajesService mensajesService;

    @Autowired
    private ClientesService clientesService;

    @Autowired
    private ReservacionesService reservacionesService;

    @GetMapping
    public List<MensajesModel> getAllMensajes() {
        return mensajesService.getAllMensajes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajesModel> getMensajeById(@PathVariable Long id) {
        Optional<MensajesModel> mensaje = mensajesService.getMensajesById(id);
        return mensaje.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createMensaje(@RequestBody MensajesModel mensaje) {
        // Validar que el remitente, destinatario y reservación existan
        Optional<ClientesModel> remitente = clientesService.getClienteById(mensaje.getRemitente().getId());
        if (remitente.isEmpty()) {
            return ResponseEntity.badRequest().body("El remitente con ID " + mensaje.getRemitente().getId() + " no existe.");
        }

        Optional<ClientesModel> destinatario = clientesService.getClienteById(mensaje.getDestinatario().getId());
        if (destinatario.isEmpty()) {
            return ResponseEntity.badRequest().body("El destinatario con ID " + mensaje.getDestinatario().getId() + " no existe.");
        }

        Optional<ReservacionesModel> reservacion = reservacionesService.getReservacionesById(mensaje.getReservacion().getId());
        if (reservacion.isEmpty()) {
            return ResponseEntity.badRequest().body("La reservación con ID " + mensaje.getReservacion().getId() + " no existe.");
        }

        // Asignar las entidades completas
        mensaje.setRemitente(remitente.get());
        mensaje.setDestinatario(destinatario.get());
        mensaje.setReservacion(reservacion.get());

        // El servidor establece los valores por defecto
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setLeido(false); // Un mensaje nuevo nunca está leído

        MensajesModel nuevoMensaje = mensajesService.saveMensajes(mensaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoMensaje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MensajesModel> updateMensaje(@PathVariable Long id,
            @RequestBody MensajesModel mensajeDetails) {
                Optional<MensajesModel> mensajeOptional = mensajesService.getMensajesById(id);
                if (mensajeOptional.isPresent()) {
                    MensajesModel mensajeExistente = mensajeOptional.get();

                    mensajeExistente.setAsunto(mensajeDetails.getAsunto());
                    mensajeExistente.setContenido(mensajeDetails.getContenido());
                    mensajeExistente.setLeido(mensajeDetails.isLeido());

                    MensajesModel updatedMensaje = mensajesService.saveMensajes(mensajeExistente);
                    return ResponseEntity.ok(updatedMensaje);
                } else {
                    return ResponseEntity.notFound().build();
                }
            }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id) {
        if (mensajesService.getMensajesById(id).isPresent()) {
            mensajesService.deleteMensajes(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}