package org.com.appl.contato.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

import org.com.api.contato.representation.ContatoBuilder;
import org.com.api.contato.representation.ContatoReq;
import org.com.api.contato.representation.ContatoResp;
import org.com.appl.contato.ContatoApplication;
import org.com.cmn.contato.exception.internalServerError;
import org.com.ct.md.contato.entity.Contato;
import org.com.dm.contato.impl.ContatoDomainImpl;
import org.com.rp.contato.repository.ContatoRepository;
import org.com.sv.contato.service.ContatoService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.kopitubruk.util.json.JSONUtil;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ContatoApplication.class)
@AutoConfigureMockMvc
public class ContatoControllerTest {

    private final String PATH = "/contato/";
    private final String LIST = PATH +"list";
    private final String CREATEDE = PATH +"createde";
    private final String EDIT = PATH +"edit/";
    private final String DELETE = PATH +"excluir/";

    //Instância do ObjectMapper para trabalhar com JSON
    private ObjectMapper objectMapper;

    private ContatoReq body;
    private Contato contatoEsperado;
    private ContatoReq contato1;
    private ContatoReq contato2;

    PageRequest paginacao;
    List<ContatoReq> list;
    Page<ContatoReq> listPage;

    @InjectMocks
    protected ContatoDomainImpl contatoService;

    @MockBean
    protected ContatoService mockService;

    @Mock
    protected ContatoRepository contatoRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        objectMapper = new ObjectMapper();

        contatoEsperado = new ContatoBuilder()
                .setId(1L)
                .setName("Eduardo")
                .setFone("553495516838")
                .build();

//      body para os metodos post e put
        body = new ContatoReq("Eduardo","553495516838");

//        Lista 1
        contato1 = new ContatoReq();
        contato1.setName("Eduardo");
        contato1.setFone("553495516838");
//        Lista 2
        contato2 = new ContatoReq();
        contato2.setName("Eduardo");
        contato2.setFone("553495516838");

//      paginação esperada
        paginacao = new PageRequest(0, 20);
        list = Arrays.asList(contato1, contato2);
        listPage = new PageImpl<>(list, paginacao, list.size());


    }

    @Test
    public void findAllIsEmpity() throws Exception {

        list = Arrays.asList();
        listPage = new PageImpl<>(list, paginacao, list.size());

        when(mockService.findall(any())).thenReturn(listPage);

        this.mockMvc.perform(MockMvcRequestBuilders.get(LIST).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())                
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content").isEmpty())
                .andExpect(jsonPath("$.totalElements").value(0));
    }

    @Test
    public void findAll() throws Exception {

        when(mockService.findall(any())).thenReturn(listPage);

        this.mockMvc.perform(MockMvcRequestBuilders.get(LIST).accept(MediaType.APPLICATION_JSON))                
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalElements").value(2));
    }


    @Test
    public void create() throws Exception {

        when(mockService.createde(any(ContatoReq.class))).thenReturn(new ContatoResp(contatoEsperado.getId()));

        System.out.println("------------"+JSONUtil.toJSON(body)+"----------------");

        mockMvc.perform(MockMvcRequestBuilders.post((CREATEDE))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").value(1));
 //                .andDo(print());
    }

    @Test
    public void createIsNotBlack() throws Exception {

        exception.expect(internalServerError.class);
        exception.expectMessage("Nome ou Fone, não deve estar em branco");

        body = new ContatoReq("","553495516838");
        contatoService.createde(body);

        System.out.println("------------"+JSONUtil.toJSON(body)+"----------------");

        mockMvc.perform(MockMvcRequestBuilders.post((CREATEDE))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
//                .andDo(print());
    }

    @Test
    public void edit() throws Exception {

        when(mockService.editCustomer(any(Long.class),any(ContatoReq.class))).thenReturn(new ContatoResp(contatoEsperado.getId()));

        mockMvc.perform(MockMvcRequestBuilders.put((EDIT+"/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted())
                .andExpect(jsonPath("$.id").value(1));
//                .andDo(print());
    }

    @Test
    public void editIsNotBlack() throws Exception {

        exception.expect(internalServerError.class);
        exception.expectMessage("Nome ou Fone, não deve estar em branco");

        body = new ContatoReq("","553495516838");
        contatoService.createde(body);

        mockMvc.perform(MockMvcRequestBuilders.put((EDIT+"/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
//                .andDo(print());
    }

    @Test
    public void editNotId() throws Exception {

        exception.expect(internalServerError.class);
        exception.expectMessage("O regirstro não existe");

        body = new ContatoReq("Eduardo","553495516838");
        contatoService.editCustomer(1L, body);

        mockMvc.perform(MockMvcRequestBuilders.put((EDIT+"/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());
//                .andDo(print());
    }

    @Test
    public void delete() throws Exception {
        exception.expect(internalServerError.class);
        exception.expectMessage("O regirstro não existe");

        contatoService.deleteCustomer(1L);

        contatoRepository.delete(contatoEsperado.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete((DELETE+"/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void deleteNotId() throws Exception {

        when(mockService.createde(any(ContatoReq.class))).thenReturn(new ContatoResp(contatoEsperado.getId()));
        contatoRepository.delete(contatoEsperado.getId());
        mockMvc.perform(MockMvcRequestBuilders.delete((DELETE+"/"+ contatoEsperado.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }
}
