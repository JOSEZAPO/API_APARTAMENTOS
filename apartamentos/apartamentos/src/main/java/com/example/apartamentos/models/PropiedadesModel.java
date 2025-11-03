package com.example.apartamentos.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne; // Changed from OneToMany
import jakarta.persistence.JoinColumn; 
import jakarta.persistence.Table;

import java.time.LocalDateTime;


@Entity
@Table(name = "PROPIEDADES")
public class PropiedadesModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @ManyToOne // Corrected relationship: Many properties to One client
    @JoinColumn(name ="ID_PROPIETARIO", nullable = false)
    private ClientesModel idPropietario;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_PROPIEDAD", nullable = false, length = 20)
    private TipoPropiedad tipo;
    
    @Column (name = "TITULO", nullable = false, length = 50)
    private String titulo;

    @Column (name = "DESCRIPCION", nullable = false, length = 200)
    private String descripcion;

    @Column (name = "DIRECCION", nullable = false, length = 100)
    private String direccion;

    @Column (name = "CIUDAD", nullable = false, length = 50)
    private String ciudad;

    @Column (name = "CODIGO_POSTAL", nullable = false, length = 5)
    private String codigoPostal;

    @Column (name = "PAIS", nullable = false)
    private String pais;

    @Column (name = "LATITUD", nullable = false)
    private double latitud;

    @Column (name = "LONGITUD", nullable = false)
    private double longitud;

    @Column (name = "PRECIO_NOCHE", nullable = false)
    private double precioNoche;

    @Column (name = "CAPACIDAD_PERSONAS", nullable = false)
    private int capacidadPersonas;

    @Column (name = "NUMERO_HABITACIONES", nullable = false)
    private int numeroHabitaciones;

    @Column (name = "NUMERO_SANITARIOS", nullable = false)
    private int numeroSanitarios;

    @Column (name = "METROS_CUADRADOS", nullable = false)
    private int metrosCuadrados;

    @Column (name = "COMODIDADES")
    private String comodidades;

    @Column (name = "NORMAS")
    private String normas;

    @Enumerated(EnumType.STRING)
    @Column (name = "ESTATUS", nullable = false, length = 20)
    private StatusPropiedad estatus;

    @Column (name = "FECHA_CREACION")
    private LocalDateTime fechaCreacion;

    @Column (name = "FECHA_ACTUALIZACION")
    private LocalDateTime fechaActualizacion;

    // Constructor sin argumentos (requerido por JPA)
    public PropiedadesModel() {
    }

    // Constructor con argumentos para crear nuevas instancias
    public PropiedadesModel(ClientesModel idPropietario, TipoPropiedad tipo, String titulo, String descripcion,
                            String direccion, String ciudad, String codigoPostal, String pais, double latitud,
                            double longitud, double precioNoche, int capacidadPersonas, int numeroHabitaciones,
                            int numeroSanitarios, int metrosCuadrados, String comodidades, String normas,
                            StatusPropiedad estatus, LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.idPropietario = idPropietario;
        this.tipo = tipo;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.latitud = latitud;
        this.longitud = longitud;
        this.precioNoche = precioNoche;
        this.capacidadPersonas = capacidadPersonas;
        this.numeroHabitaciones = numeroHabitaciones;
        this.numeroSanitarios = numeroSanitarios;
        this.metrosCuadrados = metrosCuadrados;
        this.comodidades = comodidades;
        this.normas = normas;
        this.estatus = estatus;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
    }

    //-------------Getters and Setters------------ (sin cambios, solo se ajustan los nombres de los métodos)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientesModel getIdPropietario() {
        return idPropietario;
    }

    public void setIdPropietario(ClientesModel idPropietario) {
        this.idPropietario = idPropietario;
    }

    public TipoPropiedad getTipo() {
        return tipo;
    }

    public void setTipo(TipoPropiedad tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getPrecioNoche() {
        return precioNoche;
    }

    public void setPrecioNoche(double precioNoche) {
        this.precioNoche = precioNoche;
    }

    public int getCapacidadPersonas() {
        return capacidadPersonas;
    }

    public void setCapacidadPersonas(int capacidadPersonas) {
        this.capacidadPersonas = capacidadPersonas;
    }

    public int getNumeroHabitaciones() {
        return numeroHabitaciones;
    }

    public void setNumeroHabitaciones(int numeroHabitaciones) {
        this.numeroHabitaciones = numeroHabitaciones;
    }

    public int getNumeroSanitarios() {
        return numeroSanitarios;
    }

    public void setNumeroSanitarios(int numeroSanitarios) {
        this.numeroSanitarios = numeroSanitarios;
    }

    public int getMetrosCuadrados() {
        return metrosCuadrados;
    }

    public void setMetrosCuadrados(int metrosCuadrados) {
        this.metrosCuadrados = metrosCuadrados;
    }

    public String getComodidades() {
        return comodidades;
    }

    public void setComodidades(String comodidades) {
        this.comodidades = comodidades;
    }

    public String getNormas() {
        return normas;
    }

    public void setNormas(String normas) {
        this.normas = normas;
    }

    public StatusPropiedad getEstatus() {
        return estatus;
    }

    public void setEstatus(StatusPropiedad estatus) {
        this.estatus = estatus;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
