package com.example.apartamentos.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartamentos.models.ClientesModel;
import com.example.apartamentos.models.TipoCliente;

@Repository
public interface ClientesRepository extends JpaRepository<ClientesModel, Long>{

    List<ClientesModel> findByTipo(TipoCliente tipo);
}