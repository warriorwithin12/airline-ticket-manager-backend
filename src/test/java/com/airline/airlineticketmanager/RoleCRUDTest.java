package com.airline.airlineticketmanager;

import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.models.auth.RoleValue;
import com.airline.airlineticketmanager.repositories.RoleRepository;
import com.airline.airlineticketmanager.services.RoleService;
import lombok.extern.log4j.Log4j2;
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

    @BeforeEach
    public void setUp() {
        Role admin = Role.builder().id(1L).name("ROLE_ADMIN").build();
        Role test = Role.builder().name("ROLE_TEST").build();
        Role testCreated = Role.builder().id(2L).name("ROLE_TEST").createdAt(new Date()).modifiedAt(new Date()).build();
        // Init mocks methods
        MockitoAnnotations.openMocks(this);
        Mockito.when(service.getRoleByName("ROLE_ADMIN")).thenReturn(admin);
        Mockito.when(service.create(any(Role.class))).thenReturn(testCreated); //.thenAnswer(r -> RoleValue.putValue(r.get));

        log.debug(service.getRoleByName("ROLE_ADMIN"));
        log.debug(service.create(test));
        log.debug("CUSTOM PROP: "+ customProp);
        log.debug("OTHER: "+ other);
    }

    @Test
    public void whenValidRoleName_thenRoleShouldBeFound() {
        String name = "ROLE_ADMIN";
        Role found = service.getRoleByName(RoleValue.ROLE_ADMIN.getName());
        assertThat(found.getName()).isEqualTo(name);
    }

    @Test
    public void whenNewRoleCreated_thenRoleValueCreated(){
        Role test = Role.builder().name("ROLE_TEST").build();
        Role expected = Role.builder().id(2L).name("ROLE_TEST").createdAt(new Date()).modifiedAt(new Date()).build();
        Role created = service.create(test);
//        log.info("Created: {}", created);
        // Check new object fields
        assertThat(created).hasNoNullFieldsOrProperties();
        assertThat(created).hasFieldOrProperty("id");
        assertThat(created.getId()).isEqualTo(expected.getId());
        assertThat(created.getName()).isEqualTo(expected.getName());
        // Check RoleValue DEnum
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
