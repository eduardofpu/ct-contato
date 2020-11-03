package org.com.api.contato.representation;

public class ContatoReq {

    private String name;
    private String fone;

    public ContatoReq() {
    }

    public ContatoReq(String name, String fone) {
        this.name = name;
        this.fone = fone;
    }

    public String getName() {
        return name;
    }

    public String getFone() {
        return fone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFone(String fone) {
        this.fone = fone;
    }
}
