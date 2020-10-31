package org.com.appl.contato.controller;

import org.com.api.contato.representation.ContatoReq;
import org.com.api.contato.representation.ContatoResp;
import org.com.cmn.contato.exception.internalServerError;
import org.com.sv.contato.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contato")
public class ContatoController {

    @Autowired
    private ContatoService contatoService;

    @GetMapping(path = "/list")
    @ResponseStatus(HttpStatus.OK)
    Page<ContatoReq> findAll(Pageable pageable) {
        return contatoService.findall(pageable);
    }

    @PostMapping(path = "/createde")
    @ResponseStatus(HttpStatus.CREATED)
    public ContatoResp createde(@RequestBody ContatoReq contatoReq) throws internalServerError {
        return contatoService.createde(contatoReq);
    }

    @PutMapping(path = "/edit/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ContatoResp editCustomer(@PathVariable(name = "id") Long id, @RequestBody ContatoReq contatoReq) throws internalServerError {
        return contatoService.editCustomer(id, contatoReq);
    }

    @DeleteMapping(value = "/excluir/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable(name = "id") Long id) throws internalServerError {
        contatoService.deleteCustomer(id);
    }
}
