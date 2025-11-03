package com.example.apartamentos.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "MENSAJES")
public class MensajesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_REMITENTE", nullable = false)
    private ClientesModel remitente;

    @ManyToOne
    @JoinColumn(name = "ID_DESTINATARIO", nullable = false)
    private ClientesModel destinatario;

    @ManyToOne
    @JoinColumn(name = "ID_RESERVACION", nullable = false)
    private ReservacionesModel reservacion;

    @Column (name = "ASUNTO", nullable = false)
    private String asunto;

    @Column (name = "CONTENIDO", nullable = false)
    private String contenido;

    @Column (name = "LEIDO", nullable = false)
    private boolean leido;

    @Column (name = "FECHA_ENVIO", nullable = false)
    private LocalDateTime fechaEnvio;

    // Constructor sin argumentos (requerido por JPA)
    public MensajesModel() {
    }

    public MensajesModel(ClientesModel remitente, ClientesModel destinatario, ReservacionesModel reservacion, String asunto, 
                        String contenido, boolean leido, LocalDateTime fechaEnvio) {
                    this.remitente = remitente;
                    this.destinatario = destinatario;
                    this.reservacion = reservacion;
                    this.asunto = asunto;
                    this.contenido = contenido;
                    this.leido = leido;
                    this.fechaEnvio = fechaEnvio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientesModel getRemitente() {
        return remitente;
    }

    public void setRemitente(ClientesModel remitente) {
        this.remitente = remitente;
    }

    public ClientesModel getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(ClientesModel destinatario) {
        this.destinatario = destinatario;
    }

    public ReservacionesModel getReservacion() {
        return reservacion;
    }

    public void setReservacion(ReservacionesModel reservacion) {
        this.reservacion = reservacion;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    
}