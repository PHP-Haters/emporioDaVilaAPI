//package app.emporioDaVila.service;
//
//import app.emporioDaVila.ExceptionHandlers.GenericExceptions;
//import app.emporioDaVila.entity.Usuario;
//import app.emporioDaVila.repository.UsuarioRepository;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//@Transactional
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class UsuarioServiceIntegrationTests {
//
//    @Autowired
//    private UsuarioService usuarioService;
//
//    @Autowired
//    private UsuarioRepository usuarioRepository;
//
//    private Usuario usuario;
//
//    @BeforeEach
//    void setup() {
//        usuarioRepository.deleteAll(); // garante base limpa antes de cada teste
//
//        usuario = new Usuario();
//        usuario.setNome("Gabriel");
//        usuario.setEmail("gabriel@teste.com");
//        usuario.setSenha("123456");        // **senha com 6 caracteres** (validação)
//        usuario.setTelefone("45999999999"); // preencha conforme as validações da entidade
//        usuario.setAdmin(false);
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("Salva um usuário com sucesso")
//    void saveUsuario_cenario01() {
//        String mensagem = usuarioService.saveUsuario(usuario);
//        assertEquals("Usuário salvo com sucesso.", mensagem);
//        assertEquals(1, usuarioRepository.count());
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("Retorna todos os usuários cadastrados")
//    void findAll_cenario01() {
//        usuarioRepository.save(usuario);
//        List<Usuario> lista = usuarioService.findAll();
//        assertFalse(lista.isEmpty());
//        assertEquals("Gabriel", lista.get(0).getNome());
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("Lança exceção quando não há usuários cadastrados")
//    void findAll_cenario02() {
//        usuarioRepository.deleteAll();
//        assertThrows(GenericExceptions.General.class, () -> usuarioService.findAll());
//    }
//
//    @Test
//    @Order(4)
//    @DisplayName("Retorna usuário por ID")
//    void findById_cenario01() {
//        Usuario salvo = usuarioRepository.save(usuario);
//        Usuario encontrado = usuarioService.findById(salvo.getId());
//        assertEquals(salvo.getId(), encontrado.getId());
//        assertEquals("Gabriel", encontrado.getNome());
//    }
//
//    @Test
//    @Order(5)
//    @DisplayName("Lança exceção ao buscar ID inexistente")
//    void findById_cenario02() {
//        assertThrows(GenericExceptions.NotFound.class, () -> usuarioService.findById(999L));
//    }
//
//    @Test
//    @Order(6)
//    @DisplayName("Atualiza usuário com sucesso")
//    void update_cenario01() {
//        Usuario salvo = usuarioRepository.save(usuario);
//        Usuario novo = new Usuario();
//        novo.setNome("João");
//        novo.setEmail("joao@novo.com");
//
//        Usuario atualizado = usuarioService.update(salvo.getId(), novo);
//
//        assertEquals("João", atualizado.getNome());
//        assertEquals("joao@novo.com", atualizado.getEmail());
//        assertEquals(salvo.getId(), atualizado.getId());
//    }
//
//    @Test
//    @Order(7)
//    @DisplayName("Deleta usuário com sucesso")
//    void delete_cenario01() {
//        Usuario salvo = usuarioRepository.save(usuario);
//        usuarioService.delete(salvo.getId());
//
//        // verificações diretas no repositório
//        assertFalse(usuarioRepository.existsById(salvo.getId()));
//        assertEquals(0, usuarioRepository.count());
//    }
//
//    @Test
//    @Order(8)
//    @DisplayName("Lança exceção ao deletar usuário inexistente")
//    void delete_cenario02() {
//        assertThrows(GenericExceptions.NotFound.class, () -> usuarioService.delete(999L));
//    }
//
//    @Test
//    @Order(9)
//    @DisplayName("Login com sucesso")
//    void login_cenario01() {
//        usuarioRepository.save(usuario);
//        Usuario tentativa = new Usuario();
//        tentativa.setEmail("gabriel@teste.com");
//        tentativa.setSenha("123456");
//
//        Usuario resultado = usuarioService.login(tentativa);
//        assertNull(resultado.getEmail());
//        assertNull(resultado.getSenha());
//        assertNull(resultado.getTelefone());
//        assertNull(resultado.getNome());
//    }
//
//    @Test
//    @Order(10)
//    @DisplayName("Login falha - senha incorreta")
//    void login_cenario02() {
//        usuarioRepository.save(usuario);
//        Usuario tentativa = new Usuario();
//        tentativa.setEmail("gabriel@teste.com");
//        tentativa.setSenha("errada");
//
//        assertThrows(GenericExceptions.Unauthorized.class, () -> usuarioService.login(tentativa));
//    }
//
//    @Test
//    @Order(11)
//    @DisplayName("Login falha - usuário inexistente")
//    void login_cenario03() {
//        Usuario tentativa = new Usuario();
//        tentativa.setEmail("naoexiste@teste.com");
//        tentativa.setSenha("123456");
//
//        assertThrows(GenericExceptions.NotFound.class, () -> usuarioService.login(tentativa));
//    }
//}
