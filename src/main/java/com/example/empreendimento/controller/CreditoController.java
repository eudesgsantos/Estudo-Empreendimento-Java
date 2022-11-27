package com.example.empreendimento.controller;

import com.example.empreendimento.entities.Credito;
import com.example.empreendimento.entities.Empreendimento;
import com.example.empreendimento.repository.CreditoRepository;
import com.example.empreendimento.repository.EmpreendimentoRepository;
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

    @PutMapping(path = "/import")
    public @ResponseBody String importXML(@RequestParam String path) throws ParseException {

        creditoRepository.deleteAll();

        String destination = "/Users/esantos/Developer/CESAR/empreendimento-java/credito.xml";

        try {
            downloadUsingStream(path, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ReadXML reader = new ReadXML();

        ArrayList<String> xmlFields = reader.run(destination, Credito.getFields(),"Credito");

        for (int i = 0; i < Integer.parseInt(xmlFields.get(0)); i++) {
            Credito n = new Credito();
            n.setId(Long.parseLong(xmlFields.get(1+(i*3))));
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
            n.setCpf(xmlFields.get(2+(i*3)));
            n.setDataCredito(formatter.parse(xmlFields.get(3+(i*3))));
            creditoRepository.save(n);
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
        for (Credito v : creditoRepository.findAll()) {
            result += String.format("\"%d\",", v.getId());
            result += String.format("\"%s\",", v.getCpf());
            result += String.format("\"%s\"\n", v.getDataCredito());
        }
        String url = WriteCSV.run("", result);
        return  url;
    }


    @GetMapping(path="/all")
    public @ResponseBody Iterable<Credito> getAllCredito() {
        return creditoRepository.findAll();
    }
}
