package com.example.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartamentos.models.DisponibilidadModel;
import com.example.apartamentos.repositories.DisponibilidadRepository;

@Service
public class DisponibilidadService {
    
    @Autowired
    private DisponibilidadRepository disponibilidadRepository;

    public List<DisponibilidadModel> getAllDisponibilidad() {
        return disponibilidadRepository.findAll();
    }

    public Optional<DisponibilidadModel> getDisponibilidadById(Long id) {
        return disponibilidadRepository.findById(id);
    }

    public DisponibilidadModel saveDisponibilidad(DisponibilidadModel disponibilidad) {
        return disponibilidadRepository.save(disponibilidad);
    }

    public void deleteDisponibilidad(Long id) {
        disponibilidadRepository.deleteById(id);
    }
}