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
@Table (name = "PROPIEDADES_IMAGENES")
public class PropiedadImagenesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PROPIEDAD", nullable = false)
    private PropiedadesModel idPropiedad;

    @Column (name ="URL_IMAGEN")
    private String urlImagen;

    @Column (name = "ORDEN")
    private int orden;

    @Column (name = "ES_PRINCIPAL")
    private boolean esPrincipal;

    @Column (name = "FECHA_SUBIDA")
    private LocalDateTime fechaSubida;

    // Constructor sin argumentos (requerido por JPA)
    public PropiedadImagenesModel() {
    }

    // Constructor con argumentos para crear nuevas instancias
    public PropiedadImagenesModel(PropiedadesModel idPropiedad, String urlImagen, int orden, 
                                boolean esPrincipal, LocalDateTime fechaSubida) {
                                    this.idPropiedad = idPropiedad;
                                    this.urlImagen = urlImagen;
                                    this.orden = orden;
                                    this.esPrincipal = esPrincipal;
                                    this.fechaSubida = fechaSubida;
                                }

 //-------------Getters and Setters------------  
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropiedadesModel getIdPropiedad() {
        return idPropiedad;
    }

    public void setIdPropiedad(PropiedadesModel idPropiedad) {
        this.idPropiedad = idPropiedad;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    public boolean isEsPrincipal() {
        return esPrincipal;
    }

    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
}
