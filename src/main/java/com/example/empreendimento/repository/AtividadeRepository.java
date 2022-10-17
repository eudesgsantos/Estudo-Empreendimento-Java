package com.example.empreendimento.repository;

import com.example.empreendimento.entities.Atividade;
import com.example.empreendimento.entities.Empreendimento;
import com.example.empreendimento.entities.Credito;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtividadeRepository extends CrudRepository<Atividade, Long>{
}

