package com.example.apartamentos.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PAGOS")
public class PagosModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_RESERVACION", nullable = false)
    private ReservacionesModel idReservacion;

    @Column(name = "MONTO", nullable = false)
    private double monto;

    @Enumerated(EnumType.STRING)
    @Column(name = "METODO_PAGO", nullable = false)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_PAGO", nullable = false)
    private EstadoPago estadoPago;

    @Column(name = "FECHA_PAGO", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "REFERENCIA_DE_PAGO", length = 100)
    private String referenciaTransaccion;

    @Column(name = "DATOS_PAGO")
    private String datosPago;

    @Column(name = "FECHA_CREACIÓN")
    private LocalDateTime fechaCreacion;


    // Constructor sin argumentos (requerido por JPA)
    public PagosModel() {
    }

    // Constructor con argumentos
    public PagosModel(ReservacionesModel idReservacion, double monto, MetodoPago metodoPago, EstadoPago estadoPago,
                      LocalDateTime fechaPago, String referenciaTransaccion, String datosPago, LocalDateTime fechaCreacion) {
                        this.idReservacion = idReservacion;
                        this.monto = monto;
                        this.metodoPago = metodoPago;
                        this.estadoPago = estadoPago;
                        this.fechaPago = fechaPago;
                        this.referenciaTransaccion = referenciaTransaccion;
                        this.datosPago = datosPago;
                        this.fechaCreacion = fechaCreacion;
                    }

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

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getReferenciaTransaccion() {
        return referenciaTransaccion;
    }

    public void setReferenciaTransaccion(String referenciaTransaccion) {
        this.referenciaTransaccion = referenciaTransaccion;
    }

    public String getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(String datosPago) {
        this.datosPago = datosPago;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


    //Getters and setters
    
}
