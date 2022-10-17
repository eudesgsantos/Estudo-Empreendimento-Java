package com.example.empreendimento.repository;

import com.example.empreendimento.entities.Empreendimento;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmpreendimentoRepository extends CrudRepository<Empreendimento, Long> {
}