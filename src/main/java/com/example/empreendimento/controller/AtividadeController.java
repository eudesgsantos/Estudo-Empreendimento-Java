package com.example.empreendimento.controller;

import com.example.empreendimento.entities.Credito;
import com.example.empreendimento.entities.Empreendimento;
import com.example.empreendimento.entities.Atividade;
import com.example.empreendimento.repository.CreditoRepository;
import com.example.empreendimento.repository.EmpreendimentoRepository;
import com.example.empreendimento.repository.AtividadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(path="/atividade")
public class AtividadeController {
    @Autowired
    private AtividadeRepository atividadeRepository;

    @Autowired
    private EmpreendimentoRepository empreendimentoRepository;

    @Autowired
    private CreditoRepository creditoRepository;

    @PostMapping(path="/add") // Map ONLY POST Requests
    public @ResponseBody String addNewAtividade (@RequestParam long empreendimentoId
            , @RequestParam float valorAtividade, @RequestParam Integer prazo, @RequestParam Optional<Long> creditoId) {
        Atividade n = new Atividade();
        Optional<Empreendimento> p = empreendimentoRepository.findById(empreendimentoId);
        if (creditoId.isPresent()) {
            Optional<Credito> c = creditoRepository.findById(creditoId.get());
            if(c.isPresent()){
                n.setCreditoId(creditoId.get());
            }
        }

        n.setEmpreendimentoId(empreendimentoId);
        n.setValorAtividade(valorAtividade);
        n.setPrazo(prazo);
        n.setValorSeguro(n.getValorAtividade() * p.get().getAliquota());
        n.setMontante((n.getValorAtividade() *p.get().getAliquota()) + (n.getValorAtividade() * (1 + p.get().getTaxa()) * n.getPrazo()));
        atividadeRepository.save(n);
        return "Saved";
    }

    @DeleteMapping(path="/delete")
    public @ResponseBody String deleteAtividade(@RequestParam long id) {
        atividadeRepository.deleteById(id);
        return "Removed";
    }

    @GetMapping(path="/get")
    public @ResponseBody Optional<Atividade> getAtividade(@RequestParam long id) {
        return atividadeRepository.findById(id);
    }

    @PutMapping(path="/update")
    public @ResponseBody String updateAtividade (@RequestParam long id, @RequestParam long empreendimentoId
            , @RequestParam float valorAtividade, @RequestParam Integer prazo, @RequestParam Long creditoId) {

        Optional<Empreendimento> p = empreendimentoRepository.findById(empreendimentoId);
        Atividade n = new Atividade();
        n.setId(id);
        n.setEmpreendimentoId(empreendimentoId);
        n.setValorAtividade(valorAtividade);
        n.setCreditoId(creditoId);
        n.setPrazo(prazo);
        n.setValorSeguro(n.getValorAtividade() * p.get().getAliquota());
        n.setMontante(n.getValorAtividade() *p.get().getAliquota() + n.getValorAtividade() * (1 + p.get().getTaxa()) * n.getPrazo());
        atividadeRepository.save(n);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Atividade> getAllAtividade() {
        return atividadeRepository.findAll();
    }

    @GetMapping(path="/getByCredito")
    public @ResponseBody Object getAllAtividadeByCredito(@RequestParam Long creditoId) {
        Optional<Credito> p = creditoRepository.findById(creditoId);
        if(p.isPresent()){
            Credito pp = p.get();
            Iterable<Atividade> atividades = atividadeRepository.findAll();
            List<Atividade> n_atividades = new ArrayList<Atividade>();
            for (Atividade atividade:
                    atividades) {
                if(creditoId.equals(atividade.getCreditoId())){
                    n_atividades.add(atividade);
                }
            }
            ArrayList<Object> response = new ArrayList<>();
            response.add(pp);
            response.add(n_atividades);
            return response;
        }
        return HttpStatus.BAD_REQUEST;
    }
}

