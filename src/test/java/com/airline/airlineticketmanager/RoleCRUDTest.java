package com.airline.airlineticketmanager;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.models.auth.RoleValue;
import com.airline.airlineticketmanager.repositories.RoleRepository;
import com.airline.airlineticketmanager.services.RoleService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // avoid setUp() method to be static
//@DataJpaTest
@ContextConfiguration
@ActiveProfiles("test")
@Log4j2
public class RoleCRUDTest {

    @Value("${customProp}") private String customProp;
    @Value("${other}") private String other;

    @Mock
    @Qualifier("roleService")
    private RoleService service;

    @Mock
    private RoleRepository roleRepository;

    Date now;

    @BeforeAll
    public void globalSetup(){
        this.now = new Date();
    }

    @BeforeEach
    public void setUp() {
        Role admin = Role.builder().id(1L).name("ROLE_ADMIN").build();
        Role test = Role.builder().name("ROLE_TEST").build();
        Role testCreated = Role.builder().id(2L).name("ROLE_TEST").createdAt(now).modifiedAt(now).build();
        Role testModified = Role.builder().id(2L).name("ROLE_TESTTT").createdAt(now).modifiedAt(new Date()).build();
        // Init mocks methods
        MockitoAnnotations.openMocks(this);
        Mockito.lenient().when(service.getRoleByName(admin.getName())).thenReturn(admin);
        Mockito.lenient().when(service.getRoleByName(test.getName())).thenReturn(test);
        Mockito.lenient().when(service.update(any(Role.class))).thenReturn(testModified);
        Mockito.lenient().when(service.create(any(Role.class))).thenReturn(testCreated); //.thenAnswer(r -> RoleValue.putValue(r.get));
        Mockito.lenient().when(service.delete(2L)).thenReturn(testModified); //.thenAnswer(r -> RoleValue.putValue(r.get));
    }

    @Test
    public void whenValidRoleName_thenRoleShouldBeFound() {
        String name = "ROLE_ADMIN";
        Role found = service.getRoleByName(RoleValue.ROLE_ADMIN.getName());
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void whenNewRoleCreated_thenCheckConstraints(){
        Role test = Role.builder().name("ROLE_TEST").build();
        Role expected = Role.builder().id(2L).name("ROLE_TEST").createdAt(this.now).modifiedAt(this.now).build();
        Role created = service.create(test);
        // Check new object fields
        assertThat(created).hasNoNullFieldsOrProperties();
        assertThat(created).hasFieldOrProperty("id");
        assertThat(created).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    public void whenRoleModified_existsRole(){
        Role test = Role.builder().id(2L).name("ROLE_TESTTT").build();
        Role before = service.getRoleByName("ROLE_TEST");
        assertThat(before).isNotNull();
        Role after = service.update(test);
        assertThat(after).isNotNull();
        assertThat(after).usingRecursiveComparison().ignoringFields("modifiedAt").isEqualTo(test);
    }

    public void whenRoleDeleted_roleDeletedReturned(){
        Role test = Role.builder().id(2L).name("ROLE_TESTTT").build();
        Role deleted = service.delete(2L);
        assertThat(deleted).isNotNull();
        assertThat(deleted).usingRecursiveComparison()
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
