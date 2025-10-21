package com.example.apartamentos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartamentos.models.PropiedadImagenesModel;

@Repository
public interface PropiedadImagenesRepository extends JpaRepository<PropiedadImagenesModel, Long>{
    
}
