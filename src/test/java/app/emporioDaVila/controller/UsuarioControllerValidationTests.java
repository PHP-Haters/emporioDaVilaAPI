package app.emporioDaVila.controller;

import app.emporioDaVila.entity.Usuario;
import app.emporioDaVila.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
class UsuarioControllerValidationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void saveUsuario_deveRetornar400PorUsuarioInvalido() throws Exception {
        Usuario usuarioQuebrado = new Usuario();
        usuarioQuebrado.setNome("");
        usuarioQuebrado.setEmail(null);
        usuarioQuebrado.setTelefone(null);
        usuarioQuebrado.setSenha(null);

        mockMvc.perform(post("/usuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioQuebrado)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_deveRetornar400PorUsuarioInvalido() throws Exception {
        Usuario usuarioQuebrado = new Usuario();
        usuarioQuebrado.setNome("Claudio");
        usuarioQuebrado.setEmail(null);
        usuarioQuebrado.setTelefone(null);
        usuarioQuebrado.setSenha(null);

        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioQuebrado)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void findById_deveRetornar400QuandoIdInvalido() throws Exception {
        mockMvc.perform(get("/usuario/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void delete_deveRetornar400QuandoIdInvalido() throws Exception {
        mockMvc.perform(delete("/usuario/abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_deveRetornar400QuandoIdInvalido() throws Exception {
        Usuario usuarioValido = new Usuario();
        usuarioValido.setNome("João");
        usuarioValido.setEmail("joao@example.com");
        usuarioValido.setTelefone("45900000000");
        usuarioValido.setSenha("123456");

        mockMvc.perform(put("/usuario/abc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void update_deveRetornar200QuandoValido() throws Exception {
        Usuario usuarioValido = new Usuario();
        usuarioValido.setNome("João");
        usuarioValido.setEmail("joao@example.com");
        usuarioValido.setTelefone("45900000000");
        usuarioValido.setSenha("123456");

        when(usuarioService.update(1L, usuarioValido)).thenReturn(usuarioValido);

        mockMvc.perform(put("/usuario/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioValido)))
                .andExpect(status().isOk());
    }
}
