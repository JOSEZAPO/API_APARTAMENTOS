package com.example.apartamentos.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "RESENAS")
public class ResenasModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ID_RESERVACION")
    private ReservacionesModel idReservacion;

    @Column (name = "CALIFICACION_LIMPIEZA")
    private int calificacionLimpieza;

    @Column (name = "CALIFICACION_UBICACION")
    private int calificacionUbicacion;

    @Column (name = "CALIFICACION_COMUNICACION")
    private int calificacionComunicacion;

    @Column (name = "CALIFICACION_GENERAL")
    private int calificacionGeneral;

    @Column (name = "COMENTARIO")
    private String comentario;

    @Column (name = "FECHA_RESENA")
    private LocalDateTime fechaResena;

    @Column (name = "RESPUESTA PROPIETARIO")
    private String respuestaPropietario;

    @Column (name = "FECHA_RESPUESTA")
    private LocalDateTime fechaRespuesta;

    // Constructor sin argumentos (requerido por JPA)
    public ResenasModel() {
    }

    // Constructor con argumentos
    public ResenasModel(ReservacionesModel idReservacion, int calificacionLimpieza, int calificacionUbicacion,
                        int calificacionComunicacion, int calificacionGeneral, String comentario,
                        LocalDateTime fechaResena, String respuestaPropietario, LocalDateTime fechaRespuesta) {
                            this.idReservacion = idReservacion;
                            this.calificacionLimpieza = calificacionLimpieza;
                            this.calificacionUbicacion = calificacionUbicacion;
                            this.calificacionComunicacion = calificacionComunicacion;
                            this.calificacionGeneral = calificacionGeneral;
                            this.comentario = comentario;
                            this.fechaResena = fechaResena;
                            this.respuestaPropietario = respuestaPropietario;
                            this.fechaRespuesta = fechaRespuesta;
                        }
                        
    //GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ReservacionesModel getIdReservacion() {
        return idReservacion;
    }

    public void setIdReservacion(ReservacionesModel idReservacion) {
        this.idReservacion = idReservacion;
    }

    public int getCalificacionLimpieza() {
        return calificacionLimpieza;
    }

    public void setCalificacionLimpieza(int calificacionLimpieza) {
        this.calificacionLimpieza = calificacionLimpieza;
    }

    public int getCalificacionUbicacion() {
        return calificacionUbicacion;
    }

    public void setCalificacionUbicacion(int calificacionUbicacion) {
        this.calificacionUbicacion = calificacionUbicacion;
    }

    public int getCalificacionComunicacion() {
        return calificacionComunicacion;
    }

    public void setCalificacionComunicacion(int calificacionComunicacion) {
        this.calificacionComunicacion = calificacionComunicacion;
    }

    public int getCalificacionGeneral() {
        return calificacionGeneral;
    }

    public void setCalificacionGeneral(int calificacionGeneral) {
        this.calificacionGeneral = calificacionGeneral;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaResena() {
        return fechaResena;
    }

    public void setFechaResena(LocalDateTime fechaResena) {
        this.fechaResena = fechaResena;
    }

    public String getRespuestaPropietario() {
        return respuestaPropietario;
    }

    public void setRespuestaPropietario(String respuestaPropietario) {
        this.respuestaPropietario = respuestaPropietario;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}
