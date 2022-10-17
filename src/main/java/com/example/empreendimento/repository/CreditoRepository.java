package com.example.empreendimento.repository;

import com.example.empreendimento.entities.Credito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditoRepository extends CrudRepository<Credito, Long> {
}

