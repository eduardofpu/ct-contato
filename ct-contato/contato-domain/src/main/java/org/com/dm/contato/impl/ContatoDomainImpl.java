package org.com.dm.contato.impl;

import org.apache.commons.lang3.StringUtils;
import org.com.api.contato.representation.ContatoBuilder;
import org.com.api.contato.representation.ContatoReq;
import org.com.api.contato.representation.ContatoResp;
import org.com.api.contato.representation.ListContatoBuilder;
import org.com.cmn.contato.data.MessageCodes;
import org.com.cmn.contato.exception.internalServerError;
import org.com.ct.md.contato.entity.Contato;
import org.com.rp.contato.repository.ContatoRepository;
import org.com.sv.contato.service.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ContatoDomainImpl implements ContatoService {

    private ContatoRepository contatoRepository;

    @Autowired
    public ContatoDomainImpl(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    @Override
    public Page<ContatoReq> findall(Pageable pageable) {
        Page<Contato> list = contatoRepository.findAll(pageable);
        return ListContatoBuilder.builder().ContatoBuilder(list).build();
    }

    @Override
    public ContatoResp createde(ContatoReq contatoReq) throws internalServerError {
        if(StringUtils.isBlank(contatoReq.getName()) || StringUtils.isBlank(contatoReq.getFone())){
            throw new internalServerError(MessageCodes.NOT_BLANK);
        }else {

            Contato contatoEntity = new ContatoBuilder()
                    .setName(contatoReq.getName())
                    .setFone(contatoReq.getFone())
                    .build();

            Contato resp = contatoRepository.save(contatoEntity);
            return new ContatoResp(resp.getId());
        }
    }

    @Override
    public ContatoResp editCustomer(Long id, ContatoReq contatoReq) throws internalServerError {

        Contato oneId = contatoRepository.findOne(id);

        boolean idBy = verifyId(oneId);

        if(idBy) {
            if (StringUtils.isBlank(contatoReq.getName()) || StringUtils.isBlank(contatoReq.getFone())) {
                throw new internalServerError(MessageCodes.NOT_BLANK);
            } else {

                Contato contatoEntity = new ContatoBuilder()
                        .setId(id)
                        .setName(contatoReq.getName())
                        .setFone(contatoReq.getFone())
                        .build();

                Contato resp = contatoRepository.save(contatoEntity);
                return new ContatoResp(resp.getId());
            }
        }else {
            throw new internalServerError(MessageCodes.ID_NOT_EXIST);
        }
    }

    @Override
    public void deleteCustomer(Long id) throws internalServerError {

        Contato oneId = contatoRepository.findOne(id);
        boolean idBy = verifyId(oneId);

        if(idBy) {
            contatoRepository.delete(id);
        } else {
            throw new internalServerError(MessageCodes.ID_NOT_EXIST);
        }
    }

    private boolean verifyId(Contato oneId) {

        if(null == oneId){
            return false;
        }
        return true;
    }
}
