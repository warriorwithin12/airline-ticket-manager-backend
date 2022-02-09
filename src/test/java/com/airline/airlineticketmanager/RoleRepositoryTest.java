package com.airline.airlineticketmanager;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.models.auth.RoleValue;
import com.airline.airlineticketmanager.repositories.RoleRepository;
import com.airline.airlineticketmanager.services.RoleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@DataJpaTest
@TestPropertySource(properties = {"spring.config.location = classpath:application-test.yml"})
@ContextConfiguration(classes = BeansConfig.class)
@ActiveProfiles("test")
public class RoleRepositoryTest {

//    @Autowired
//    private TestEntityManager entityManager;

    @MockBean
    private RoleService service;

    @Autowired
    private RoleRepository repository;


    @BeforeAll
    public void setUp() {
        Role admin = service.create(Role.builder().name("ROLE_ADMIN").build());

//        entityManager.persist(Role.builder().name("ROLE_ADMIN").build());
//        Employee alex = new Employee("alex");
//        Mockito.when(employeeRepository.findByName(alex.getName()))
//                .thenReturn(alex);
    }

//    @BeforeEach
//    void initDependencies() {
//        service = new RoleService(repository);
//    }

    @Test
    public void whenValidRoleName_thenRoleShouldBeFound() {
        String name = "ROLE_ADMIN";
//        Role found = repository.findByName(RoleValue.ROLE_ADMIN.getName());
        Role found = service.getRoleByName(RoleValue.ROLE_ADMIN.getName());
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void whenNewRoleCreated_thenRoleValueCreated(){
//        RoleValue.putValue("TEST");
        Role newRole = Role.builder().name("TEST").build();
        Role created = service.create(newRole);
        RoleValue newDEnum = RoleValue.valueOf("TEST");
        assertThat(newDEnum).isNotNull();
        assertThat(created.getName()).isEqualTo(newDEnum.getName());
    }

//    @Test
//    public void givenEmployees_whenGetEmployees_thenStatus200()
//            throws Exception {
//
//        Role newRole = new Role("ADMIN");
//        mvc.perform(get("/auth/role/1")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content()
//                .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
//                .andExpect((ResultMatcher) jsonPath("$[0].name", is("ROLE_ADMIN")));
//    }
}
