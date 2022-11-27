package com.example.empreendimento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

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

    @PutMapping(path = "/import")
    public @ResponseBody String importXML(@RequestParam String path) throws ParseException {

        empreendimentoRepository.deleteAll();

        String destination = "/Users/esantos/Developer/CESAR/empreendimento-java/empreendimento.xml";

        try {
            downloadUsingStream(path, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadXML reader = new ReadXML();

        ArrayList<String> xmlFields = reader.run(destination, Empreendimento.getFields(),"Empreendimento");

        for (int i = 0; i < Integer.parseInt(xmlFields.get(0)); i++) {
            Empreendimento n = new Empreendimento();
            n.setId(Long.parseLong(xmlFields.get(1+(i*5))));
            //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            n.setCodigo(xmlFields.get(2+(i*5)));
            n.setDescricao(xmlFields.get(3+(i*5)));
            n.setTaxa(Float.parseFloat(xmlFields.get(4+(i*5))));
            n.setAliquota(Float.parseFloat(xmlFields.get(5+(i*5))));
            empreendimentoRepository.save(n);
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

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Empreendimento> getAllEmpreendimento() {
        return empreendimentoRepository.findAll();
    }
}
