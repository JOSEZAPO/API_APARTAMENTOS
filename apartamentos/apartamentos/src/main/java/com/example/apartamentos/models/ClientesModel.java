package com.example.apartamentos.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "CLIENTES")
public class ClientesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_CLIENTE", nullable = false, length = 20)
    private TipoCliente tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROL_CLIENTE", nullable = false, length = 20)
    private RolCliente rol;

    @Column(name = "NOMBRE", nullable = false, length = 35)
    private String nombre;

    @Column(name = "APELLIDO_PAT", nullable = false, length = 35)
    private String apellidoPat;

    @Column(name = "APELLIDO_MAT", nullable = false, length = 35)
    private String apellidoMat;
    
    @Column(name = "EMAIL", unique = true, nullable = false, length = 50)
    private String email;
    
    @Column(name = "TELEFONO", nullable = false, length = 13)
    private String telefono;

    @Column(name = "FECHA_NACIMIENTO", nullable = false)
    private LocalDate fechaDeNacimiento;
    
    @Column(name = "INE", nullable = false, length = 20)
    private String ine;
    
    @Column(name = "DIRECCION", nullable = false, length = 100)
    private String direccion;

    @Column(name = "FECHA_REGISTRO", nullable = false)
    private LocalDateTime fechaRegistro;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "ESTATUS", nullable = false, length = 20)
    private StatusCliente estatus;
    
    @Column(name = "PASSWORD", nullable = false, length = 20)
    private String password;
    
    @Column(name = "ULTIMO_ACCESO", nullable = false)
    private LocalDateTime ultimoAcceso;

    // Constructor sin argumentos (requerido por JPA)
    public ClientesModel() {
    }

    // Constructor con argumentos para crear nuevas instancias
    public ClientesModel(TipoCliente tipo, RolCliente rol, String nombre, String apellidoPat, String apellidoMat,
                         String email, String telefono, LocalDate fechaDeNacimiento, String ine, String direccion,
                         LocalDateTime fechaRegistro, StatusCliente estatus, String password, LocalDateTime ultimoAcceso) {
        this.tipo = tipo;
        this.rol = rol;
        this.nombre = nombre;
        this.apellidoPat = apellidoPat;
        this.apellidoMat = apellidoMat;
        this.email = email;
        this.telefono = telefono;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.ine = ine;
        this.direccion = direccion;
        this.fechaRegistro = fechaRegistro;
        this.estatus = estatus;
        this.password = password;
        this.ultimoAcceso = ultimoAcceso;
    }

    //Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoCliente getTipo() {
        return tipo;
    }

    public void setTipo(TipoCliente tipo) {
        this.tipo = tipo;
    }

    public RolCliente getRol() {
        return rol;
    }

    public void setRol(RolCliente rol) {
        this.rol = rol;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPat() {
        return apellidoPat;
    }

    public void setApellidoPat(String apellidoPat) {
        this.apellidoPat = apellidoPat;
    }

    public String getApellidoMat() {
        return apellidoMat;
    }

    public void setApellidoMat(String apellidoMat) {
        this.apellidoMat = apellidoMat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public LocalDate getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(LocalDate fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getINE() {
        return ine;
    }

    public void setINE(String ine) {
        this.ine = ine;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public StatusCliente getEstatus() {
        return estatus;
    }

    public void setEstatus(StatusCliente estatus) {
        this.estatus = estatus;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }
}
