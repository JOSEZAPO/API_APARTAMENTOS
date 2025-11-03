package com.example.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartamentos.models.TipoPropiedad;
import com.example.apartamentos.models.PropiedadesModel;
import com.example.apartamentos.repositories.PropiedadesRepository;

@Service
public class PropiedadesService {
    
    @Autowired
    private PropiedadesRepository propiedadesRepository;

    public List<PropiedadesModel> getAllPropiedades() {
        return propiedadesRepository.findAll();
    }

    public Optional<PropiedadesModel> getPropiedadById(Long id) {
        return propiedadesRepository.findById(id);
    }

    public PropiedadesModel savePropiedad(PropiedadesModel propiedad) {
        return propiedadesRepository.save(propiedad);
    }

    public void deletePropiedad(Long id) {
        propiedadesRepository.deleteById(id);
    }

    public List<PropiedadesModel> getPropiedadesByTipo(TipoPropiedad tipo) {
        return propiedadesRepository.findByTipo(tipo);
    }

    public List<PropiedadesModel> getPropiedadesByPropietarioId(Long propietarioId) {
        return propiedadesRepository.findByIdPropietario_Id(propietarioId);
    }

}
