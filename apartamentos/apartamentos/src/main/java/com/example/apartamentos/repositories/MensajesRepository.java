package com.example.apartamentos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartamentos.models.MensajesModel;

@Repository 
public interface MensajesRepository extends JpaRepository<MensajesModel, Long>{
    
}
