package com.example.empreendimento;

import com.example.empreendimento.entities.Credito;
import com.example.empreendimento.entities.Empreendimento;
import com.example.empreendimento.entities.Atividade;
import com.example.empreendimento.repository.CreditoRepository;
import com.example.empreendimento.repository.EmpreendimentoRepository;
import com.example.empreendimento.repository.AtividadeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.PropertyAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EmpreendimentoApplicationTests {
	@Autowired
	private EmpreendimentoRepository empreendimentoRepository;

	@Autowired
	private CreditoRepository creditoRepository;

	@Autowired
	private AtividadeRepository atividadeRepository;
	@Test
	void contextLoads() {
	}
	@BeforeAll
	public void setup(){
		empreendimentoRepository.deleteAll();
		creditoRepository.deleteAll();
		atividadeRepository.deleteAll();
	}

	@Test
	void ObrigatoriedadeEmpreendimento(){
		Empreendimento n = new Empreendimento();
		boolean entrou = false;
		try {
			empreendimentoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ObrigatoriedadeEmpreendimentoNulosBrancos(){
		Empreendimento n = new Empreendimento();
		n.setDescricao("Teste");
		n.setTaxa((float)0.02);
		n.setAliquota((float) 0.01);
		boolean entrou = false;
		try {
			empreendimentoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ObrigatoriedadeCredito(){
		Credito n = new Credito();
		boolean entrou = false;
		try {
			creditoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ObrigatoriedadeCreditoNulosBrancos() throws ParseException {
		Credito n = new Credito();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		n.setDataCredito(date1);
		boolean entrou = false;
		try {
			creditoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ValidaCreditoCPFCurto() throws ParseException {
		Credito n = new Credito();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		n.setDataCredito(date1);
		n.setCpf("000");
		boolean entrou = false;
		try {
			creditoRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

	@Test
	void ValidaCupomCPFCorreto() throws ParseException {
		Credito n = new Credito();
		String sDate1="31/12/1998";
		Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(sDate1);
		n.setDataCredito(date1);
		n.setCpf("12136104438");
		creditoRepository.save(n);
	}

	@Test
	void ObrigatoriedadeVenda(){
		Atividade n = new Atividade();
		boolean entrou = false;
		try {
			atividadeRepository.save(n);
		}
		catch (DataIntegrityViolationException exception){
			entrou = true;
		}
		Assertions.assertTrue(entrou);
	}

}
