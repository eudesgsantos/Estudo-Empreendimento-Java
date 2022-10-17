package com.example.empreendimento.controller;

import com.example.empreendimento.entities.Empreendimento;
import com.example.empreendimento.repository.EmpreendimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/empreendimento")
public class EmpreendimentoController {
    @Autowired
    private EmpreendimentoRepository empreendimentoRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewEmpreendimento (@RequestParam String codigo ,@RequestParam String descricao
            , @RequestParam float taxa, @RequestParam float aliquota) {

        if(taxa > 0 && aliquota >= 0 && aliquota <= 1 && !descricao.strip().equals("")) {
            Empreendimento n = new Empreendimento();
            n.setCodigo(codigo);
            n.setDescricao(descricao);
            n.setTaxa(taxa);
            n.setAliquota(aliquota);
            empreendimentoRepository.save(n);
            return "Saved";
        }
        return "";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteEmpreendimento(@RequestParam long id) {
        empreendimentoRepository.deleteById(id);
        return "Removed";
    }

    @GetMapping(path="/get")
    public @ResponseBody Optional<Empreendimento> getEmpreendimento(@RequestParam long id) {
        return empreendimentoRepository.findById(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateEmpreendimento(@RequestParam long id,@RequestParam String codigo, @RequestParam String descricao, @RequestParam float taxa, @RequestParam float aliquota) {
        empreendimentoRepository.deleteById(id);
        Empreendimento n = new Empreendimento();
        n.setId(id);
        n.setCodigo(codigo);
        n.setDescricao(descricao);
        n.setTaxa(taxa);
        n.setAliquota(aliquota);
        empreendimentoRepository.save(n);
        return "Updated";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Empreendimento> getAllEmpreendimento() {
        return empreendimentoRepository.findAll();
    }
}
