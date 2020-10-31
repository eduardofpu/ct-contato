package org.com.sv.contato.service;

import org.com.api.contato.representation.ContatoReq;
import org.com.api.contato.representation.ContatoResp;
import org.com.cmn.contato.exception.internalServerError;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ContatoService {
    Page<ContatoReq> findall(Pageable pageable);
    ContatoResp createde(ContatoReq contatoReq) throws internalServerError;
    ContatoResp editCustomer(Long id, ContatoReq contatoReq) throws internalServerError;
    void deleteCustomer(Long id) throws internalServerError;
}
