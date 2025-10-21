package com.example.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartamentos.models.MensajesModel;
import com.example.apartamentos.repositories.MensajesRepository;

@Service
public class MensajesService {
    
    @Autowired
    private MensajesRepository mensajesRepository;

    public List<MensajesModel> getAllMensajes(){
        return mensajesRepository.findAll();
    }

    public Optional<MensajesModel> getMensajesById(Long id){
        return mensajesRepository.findById(id);
    }

    public MensajesModel saveMensajes (MensajesModel mensajes){
        return mensajesRepository.save(mensajes);
    }

    public void deleteMensajes(Long id){
        mensajesRepository.deleteById(id);
    }
}
