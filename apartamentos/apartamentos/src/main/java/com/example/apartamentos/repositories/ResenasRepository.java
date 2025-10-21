package com.example.apartamentos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartamentos.models.ResenasModel;

@Repository
public interface ResenasRepository extends JpaRepository<ResenasModel, Long>{
    
}
