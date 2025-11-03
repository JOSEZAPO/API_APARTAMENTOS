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
@Table (name = "RESERVACIONES")
public class ReservacionesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_PROPIEDAD", nullable = false)
    private PropiedadesModel idPropiedad;

    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE", nullable = false)
    private ClientesModel idCliente;

    @Column (name = "FECHA_ENTRADA")
    private LocalDateTime fechaEntrada;

    @Column (name = "FECHA_SALIDA")
    private LocalDateTime fechaSalida;

    @Column (name = "NUMERO_DE_HUESPEDES")
    private int numeroDeHuespedes;

    @Column (name = "PRECIO_TOTAL")
    private double precioTotal;

    @Enumerated(EnumType.STRING)
    @Column (name = "ESTADO", length = 100)
    private EstadoReservacion estado;

    @Column(name = "FECHA_RESERVACION")
    private LocalDateTime fechaReservacion;

    @Column(name = "NOTAS")
    private String notas;

    @Column(name = "CODIGO_RESERVACION")
    private String codigoReservacion;

    @Column(name = "FECHA_CHECKING")
    private LocalDateTime fechaChecking;

    @Column(name = "FECHA_CHECKOUT")
    private LocalDateTime fechaCheckout;

    // Constructor sin argumentos (requerido por JPA)
    public ReservacionesModel() {
    }

    // Constructor con argumentos para crear nuevas instancias
    public ReservacionesModel(PropiedadesModel idPropiedad, ClientesModel idCliente, LocalDateTime fechaEntrada, 
                            LocalDateTime fechaSalida, int numeroDeHuespedes, double precioTotal, 
                            EstadoReservacion estado, LocalDateTime fechaReservacion, String notas, String codigoReservacion, 
                            LocalDateTime fechaChecking, LocalDateTime fechaCheckout){
        this.idPropiedad = idPropiedad;
        this.idCliente = idCliente;
        this.fechaEntrada = fechaEntrada;
        this.fechaSalida = fechaSalida;
        this.numeroDeHuespedes = numeroDeHuespedes;
        this.precioTotal = precioTotal;
        this.estado = estado;
        this.fechaReservacion = fechaReservacion;
        this.notas = notas;
        this.codigoReservacion = codigoReservacion;
        this.fechaChecking = fechaChecking;
        this.fechaCheckout = fechaCheckout;
    }

    //GETTER AND SETTERS
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

    public ClientesModel getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(ClientesModel idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(LocalDateTime fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getNumeroDeHuespedes() {
        return numeroDeHuespedes;
    }

    public void setNumeroDeHuespedes(int numeroDeHuespedes) {
        this.numeroDeHuespedes = numeroDeHuespedes;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public EstadoReservacion getEstado() {
        return estado;
    }

    public void setEstado(EstadoReservacion estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaReservacion() {
        return fechaReservacion;
    }

    public void setFechaReservacion(LocalDateTime fechaReservacion) {
        this.fechaReservacion = fechaReservacion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getCodigoReservacion() {
        return codigoReservacion;
    }

    public void setCodigoReservacion(String codigoReservacion) {
        this.codigoReservacion = codigoReservacion;
    }

    public LocalDateTime getFechaChecking() {
        return fechaChecking;
    }

    public void setFechaChecking(LocalDateTime fechaChecking) {
        this.fechaChecking = fechaChecking;
    }

    public LocalDateTime getFechaCheckout() {
        return fechaCheckout;
    }

    public void setFechaCheckout(LocalDateTime fechaCheckout) {
        this.fechaCheckout = fechaCheckout;
    }
}
