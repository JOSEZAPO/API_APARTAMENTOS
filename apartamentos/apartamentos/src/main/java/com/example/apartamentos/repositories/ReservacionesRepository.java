package com.example.apartamentos.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.apartamentos.models.ReservacionesModel;

@Repository
public interface ReservacionesRepository extends JpaRepository<ReservacionesModel, Long> {

    List<ReservacionesModel> findByIdCliente_Id(Long clienteId);

    List<ReservacionesModel> findByIdPropiedad_Id(Long propiedadId);

    List<ReservacionesModel> findByFechaEntradaLessThanEqualAndFechaSalidaGreaterThanEqual(LocalDateTime fechaEntrada, LocalDateTime fechaSalida);

}
