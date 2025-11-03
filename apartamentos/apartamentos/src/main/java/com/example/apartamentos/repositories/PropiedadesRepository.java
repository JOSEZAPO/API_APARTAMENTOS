package com.example.apartamentos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartamentos.models.PropiedadesModel;
import com.example.apartamentos.models.TipoPropiedad;

@Repository
public interface PropiedadesRepository extends JpaRepository<PropiedadesModel, Long>{

    List<PropiedadesModel> findByTipo(TipoPropiedad tipo);

    List<PropiedadesModel> findByIdPropietario_Id(Long propietarioId);
}
