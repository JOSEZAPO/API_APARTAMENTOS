package com.example.apartamentos.services;

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

    public Optional<ReservacionesModel> getReservacionesById(Long id) {
        return reservacionesRepository.findById(id);
    }

    public ReservacionesModel saveReservaciones(ReservacionesModel reservaciones) {
        return reservacionesRepository.save(reservaciones);
    }

    public void deleteReservaciones(Long id) {
        reservacionesRepository.deleteById(id);
    }
}
