package com.example.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartamentos.models.PropiedadImagenesModel;
import com.example.apartamentos.repositories.PropiedadImagenesRepository;

@Service
public class PropiedadImagenesService {
    
    @Autowired
    private PropiedadImagenesRepository propiedadImagenesRepository;

    public List<PropiedadImagenesModel> getAllPropiedadImagenes() {
        return propiedadImagenesRepository.findAll();
    }

    public Optional<PropiedadImagenesModel> getPropiedadImagenesById(Long id) {
        return propiedadImagenesRepository.findById(id);
    }

    public PropiedadImagenesModel savePropiedadImagenes(PropiedadImagenesModel propiedadImagenes) {
        return propiedadImagenesRepository.save(propiedadImagenes);
    }

    public void deletePropiedadImagenes(Long id) {
        propiedadImagenesRepository.deleteById(id);
    }

}
