package com.example.apartamentos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.apartamentos.models.ClientesModel;
import com.example.apartamentos.models.TipoCliente;
import com.example.apartamentos.repositories.ClientesRepository;

@Service
public class ClientesService {
 @Autowired
    private ClientesRepository clientesRepository;

    public List<ClientesModel> getAllClientes() {
        return clientesRepository.findAll();
    }

    public Optional<ClientesModel>  getClienteById(Long id) {
        return clientesRepository.findById(id);
    }

    public ClientesModel saveCliente(ClientesModel cliente) {
        return clientesRepository.save(cliente);
    }

    public void deleteCliente(Long id) {
        clientesRepository.deleteById(id);
    }

    public List<ClientesModel> getClientesByTipo(TipoCliente tipo) {
        return clientesRepository.findByTipo(tipo);
    }
}