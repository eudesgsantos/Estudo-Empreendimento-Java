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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
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

    @PutMapping(path = "/import")
    public @ResponseBody String importXML(@RequestParam String path) throws ParseException {

        atividadeRepository.deleteAll();

        String destination = "/Users/esantos/Developer/CESAR/empreendimento-java/atividade.xml";

        try {
            downloadUsingStream(path, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadXML reader = new ReadXML();

        ArrayList<String> xmlFields = reader.run(destination, Atividade.getFields(),"Atividade");

        for (int i = 0; i < Integer.parseInt(xmlFields.get(0)); i++) {
            Atividade n = new Atividade();
            n.setId(Long.parseLong(xmlFields.get(1+(i*5))));
            //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            Optional<Empreendimento> p = empreendimentoRepository.findById(Long.parseLong(xmlFields.get(2+(i*5))));
            Optional<Credito> c = creditoRepository.findById(Long.parseLong(xmlFields.get(5+(i*5))));
                if(c.isPresent()){
                    n.setCreditoId(Long.parseLong(xmlFields.get(5+(i*5))));
                }
            n.setEmpreendimentoId(Long.parseLong(xmlFields.get(2+(i*5))));
            n.setValorAtividade(Float.parseFloat(xmlFields.get(3+(i*5))));
            n.setPrazo(Integer.parseInt(xmlFields.get(4+(i*5))));
            n.setValorSeguro(n.getValorAtividade() * p.get().getAliquota());
            n.setMontante((n.getValorAtividade() *p.get().getAliquota()) + (n.getValorAtividade() * (1 + p.get().getTaxa()) * n.getPrazo()));
            atividadeRepository.save(n);
        }
        return "Imported";
    }

    public static void downloadUsingStream(String urlStr, String file) throws IOException {
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        FileOutputStream fis = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int count=0;
        while((count = bis.read(buffer,0,1024)) != -1)
        {
            fis.write(buffer, 0, count);
        }
        fis.close();
        bis.close();
    }

    @GetMapping(path = "/export")
    public @ResponseBody String exportCSV(@RequestParam String path) throws IOException {
        String result = "";
        for (Atividade v : atividadeRepository.findAll()) {
            result += String.format("\"%d\",", v.getId());
            result += String.format("\"%s\",", v.getCreditoId());
            result += String.format("\"%d\",", v.getEmpreendimentoId());
            result += String.format("\"%s\",", v.getMontante());
            result += String.format("\"%d\",", v.getPrazo());
            result += String.format("\"%s\",", v.getValorAtividade());
            result += String.format("\"%s\"\n", v.getValorSeguro());
        }
        String url = WriteCSV.run("", result);
        return  url;
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

