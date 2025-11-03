package com.example.apartamentos.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartamentos.models.ReservacionesModel;
import com.example.apartamentos.repositories.ReservacionesRepository;

@Service
public class ReservacionesService {
    
    @Autowired
    private ReservacionesRepository reservacionesRepository;

    public List<ReservacionesModel> getAllReservaciones() {
        return reservacionesRepository.findAll();
    }

    public List<ReservacionesModel> getReservacionesByClienteId(Long clienteId) {
    return reservacionesRepository.findByIdCliente_Id(clienteId);
}

    public List<ReservacionesModel> getReservacionesByPropiedadId(Long propiedadId) {
        return reservacionesRepository.findByIdPropiedad_Id(propiedadId);
    }

    public Optional<ReservacionesModel> getReservacionesById(Long id) {
        return reservacionesRepository.findById(id);
    }

    public ReservacionesModel saveReservaciones(ReservacionesModel reservaciones) {
        return reservacionesRepository.save(reservaciones);
    }

    public void deleteReservaciones(Long id) {
        reservacionesRepository.deleteById(id);
    }

    public List<ReservacionesModel> getReservacionesPorFecha(LocalDate fecha) {
        return reservacionesRepository.findByFechaEntradaLessThanEqualAndFechaSalidaGreaterThanEqual(fecha.atStartOfDay(), fecha.atTime(23, 59, 59));
    }
}
