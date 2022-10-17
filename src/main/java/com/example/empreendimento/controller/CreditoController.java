package com.example.empreendimento.controller;

import com.example.empreendimento.entities.Credito;
import com.example.empreendimento.entities.Empreendimento;
import com.example.empreendimento.repository.CreditoRepository;
import com.example.empreendimento.repository.EmpreendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;


@Controller
@RequestMapping(path="/credito")
public class CreditoController {
    @Autowired
    private CreditoRepository creditoRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewCredito (@RequestParam Date dataCredito
            , @RequestParam String cpf) {
        Credito n = new Credito();
        n.setDataCredito(dataCredito);
        n.setCpf(cpf);
        creditoRepository.save(n);
        return "Saved";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteCredito(@RequestParam long id) {
        creditoRepository.deleteById(id);
        return "Removed";
    }

    @GetMapping(path="/get")
    public @ResponseBody Optional<Credito> getCredito(@RequestParam long id) {
        return creditoRepository.findById(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateCredito(@RequestParam long id, @RequestParam Date dataCredito, @RequestParam String cpf) {
        Credito n = new Credito();
        n.setId(id);
        n.setDataCredito(dataCredito);
        n.setCpf(cpf);
        creditoRepository.save(n);
        return "Updated";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Credito> getAllCredito() {
        return creditoRepository.findAll();
    }
}
