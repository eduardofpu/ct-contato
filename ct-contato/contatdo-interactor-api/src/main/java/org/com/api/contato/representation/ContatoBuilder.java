package org.com.api.contato.representation;

import org.com.ct.md.contato.entity.Contato;

public class ContatoBuilder {
    private Contato contato;

    public ContatoBuilder() {

        this.contato = new Contato();
    }

    public ContatoBuilder setId(Long id){
        contato.setId(id);
        return this;
    }

    public ContatoBuilder setName(String name){
        contato.setName(name);
        return this;
    }

    public ContatoBuilder setFone(String fone){
        contato.setFone(fone);
        return this;
    }

    public Contato build(){
        return contato;
    }
}
