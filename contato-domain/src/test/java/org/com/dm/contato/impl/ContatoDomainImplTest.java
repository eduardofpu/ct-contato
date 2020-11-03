package org.com.dm.contato.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.com.api.contato.representation.ContatoBuilder;
import org.com.api.contato.representation.ContatoReq;
import org.com.api.contato.representation.ContatoResp;
import org.com.cmn.contato.exception.internalServerError;
import org.com.ct.md.contato.entity.Contato;
import org.com.rp.contato.repository.ContatoRepository;
import org.com.sv.contato.service.ContatoService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ContatoDomainImplTest {


    private Contato contatoEsperado;
    private ContatoReq contato1;
    private ContatoReq contato2;

    PageRequest paginacao;
    List<ContatoReq> list;
    Page<ContatoReq> listPage;

    @InjectMocks
    protected ContatoDomainImpl contatoService;

    @Mock
    protected ContatoService mockService;

    @Mock
    protected ContatoRepository contatoRepository;

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Before
    public void setUp() {

        contatoEsperado = new ContatoBuilder()
                .setId(1L)
                .setName("Eduardo")
                .setFone("553495516838")
                .build();

//        Lista 1
        contato1 = new ContatoReq();
        contato1.setName("Eduardo");
        contato1.setFone("553495516838");
//        Lista 2
        contato2 = new ContatoReq();
        contato2.setName("Eduardo Rodrigues Delfino");
        contato2.setFone("553495517855");

//      paginação esperada
        paginacao = new PageRequest(0, 20);
        list = Arrays.asList(contato1, contato2);
        listPage = new PageImpl<>(list, paginacao, list.size());


    }

    @Test
    public void testFindallIsEmpity() {
        list = Arrays.asList();
        listPage = new PageImpl<>(list, paginacao, list.size());

        when(mockService.findall(any())).thenReturn(listPage);
        Page<ContatoReq> findall = mockService.findall(new PageRequest(0, 20));
        Assertions.assertThat(findall.getContent().isEmpty()).isEqualTo(this.listPage.getContent().isEmpty());
        Assertions.assertThat(findall.getTotalElements()).isEqualTo(this.listPage.getTotalElements());
    }

    @Test
    public void testFindall() {

        when(mockService.findall(any())).thenReturn(listPage);
        Page<ContatoReq> findall = mockService.findall(new PageRequest(0, 20));
        Assertions.assertThat(findall.getTotalElements()).isEqualTo(this.listPage.getTotalElements());
    }

    @Test
    public void testCreate() throws internalServerError {

        when(mockService.createde(any(ContatoReq.class))).thenReturn(new ContatoResp(contatoEsperado.getId()));
        ContatoResp createde = mockService.createde(contato1);

        Assertions.assertThat(createde.getId()).isEqualTo(contatoEsperado.getId());
    }

    @Test
    public void testCreateIsBlack() throws internalServerError {

        exception.expect(internalServerError.class);
        exception.expectMessage("Nome ou Fone, não deve estar em branco");

        ContatoReq conReq = new ContatoReq();
        conReq.setName("");
        conReq.setFone(null);
        contatoService.createde(conReq);
    }

    @Test
    public void testEdit() throws internalServerError {

        when(mockService.createde(any(ContatoReq.class))).thenReturn(new ContatoResp(contatoEsperado.getId()));
        ContatoResp createde = mockService.createde(contato1);
        Assertions.assertThat(createde.getId()).isEqualTo(contatoEsperado.getId());

        when(mockService.editCustomer(any(Long.class), any(ContatoReq.class))).thenReturn(new ContatoResp(createde.getId()));
        ContatoResp resp = mockService.editCustomer(contatoEsperado.getId(), contato2);

        Assertions.assertThat(resp.getId()).isEqualTo(contatoEsperado.getId());
    }


    @Test
    public void testEditNotId() throws internalServerError {
        exception.expect(internalServerError.class);
        exception.expectMessage("O regirstro não existe");

        when(mockService.createde(any(ContatoReq.class))).thenReturn(new ContatoResp(contatoEsperado.getId()));
        ContatoResp createde = mockService.createde(contato1);
        Assertions.assertThat(createde.getId()).isEqualTo(contatoEsperado.getId());

        when(mockService.editCustomer(any(Long.class), any(ContatoReq.class))).thenReturn(new ContatoResp(createde.getId()));
        contatoService.editCustomer(2L, contato2);
    }

    @Test
    public void testDelete() throws internalServerError {

        ContatoResp contatoResp = new ContatoResp(contatoEsperado.getId());
        when(mockService.createde(any(ContatoReq.class))).thenReturn(new ContatoResp(contatoResp.getId()));

        contatoRepository.findOne(contatoResp.getId());
        mockService.deleteCustomer(contatoResp.getId());
    }

    @Test
    public void testDeleteNotId() throws internalServerError {
        exception.expect(internalServerError.class);
        exception.expectMessage("O regirstro não existe");

        ContatoResp contatoResp = new ContatoResp(contatoEsperado.getId());
        when(mockService.createde(any(ContatoReq.class))).thenReturn(new ContatoResp(contatoResp.getId()));

        contatoService.deleteCustomer(2L);
    }

}
