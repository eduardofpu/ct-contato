package org.com.api.contato.representation;

import org.com.ct.md.contato.entity.Contato;
import org.springframework.data.domain.Page;

public class ListContatoBuilder {
    private Page<Contato> contatos;


    public static ListContatoBuilder builder() {
        return new ListContatoBuilder();
    }

    public ListContatoBuilder ContatoBuilder(Page<Contato> contatos) {
        this.contatos = contatos;
        return this;
    }

    public Page<ContatoReq> build() {
        return this.contatos.map(contato -> new ContatoReq(
                contato.getName(),
                contato.getFone()
        ));
    }
}
