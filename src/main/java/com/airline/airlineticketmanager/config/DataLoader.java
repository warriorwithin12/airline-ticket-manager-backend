package com.airline.airlineticketmanager.config;

import com.airline.airlineticketmanager.models.Airline;
import com.airline.airlineticketmanager.models.Passenger;
import com.airline.airlineticketmanager.models.Plane;
import com.airline.airlineticketmanager.models.PlaneCapacityType;
import com.airline.airlineticketmanager.models.auth.Role;
import com.airline.airlineticketmanager.models.auth.RoleValue;
import com.airline.airlineticketmanager.models.auth.User;
import com.airline.airlineticketmanager.repositories.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Log4j2
@Profile("local")
public class DataLoader implements ApplicationRunner {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String dbMode;
    private final List<String> initDBModes;
    // JPA Repositories
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final PlaneRepository planeRepository;
    private final AirTicketRepository airTicketRepository;
    private final AirlineRepository airlineRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(PassengerRepository passengerRepository,
                      FlightRepository flightRepository,
                      PlaneRepository planeRepository,
                      AirTicketRepository airTicketRepository,
                      AirlineRepository airlineRepository, RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passengerRepository = passengerRepository;
        this.flightRepository = flightRepository;
        this.planeRepository = planeRepository;
        this.airTicketRepository = airTicketRepository;
        this.airlineRepository = airlineRepository;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.initDBModes = Arrays.asList("create", "create-drop");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (dbMode != null && initDBModes.contains(dbMode)) {
            this.createUsers();
            this.loadPassengers();
            this.loadAirlines();
            this.loadPlanes();
        }
        else {
            this.loadRoleValues();
        }
    }

    private void createUsers(){
        Role admin = roleRepository.save(Role.builder().name(RoleValue.ROLE_ADMIN.getName()).build());
        Role user = roleRepository.save(Role.builder().name(RoleValue.ROLE_USER.getName()).build());
        Role api = roleRepository.save(Role.builder().name(RoleValue.ROLE_API.getName()).build());
        Role apiRead = roleRepository.save(Role.builder().name(RoleValue.ROLE_API_READ.getName()).build());
        Role apiWrite = roleRepository.save(Role.builder().name(RoleValue.ROLE_API_WRITE.getName()).build());
        log.info("Created Role:" + admin);
        log.info("Created Role:" + user);
        log.info("Created Role:" + api);
        log.info("Created Role:" + apiRead);
        log.info("Created Role:" + apiWrite);
        log.info("Created user:" + userRepository.save(User.builder()
                .username("admin")
                .email("admin@sample.com")
                .password(passwordEncoder.encode("secret"))
                .roles(Collections.singleton(admin))
                .build()));
        log.info("Created user:" + userRepository.save(User.builder()
                .username("user")
                .email("user@sample.com")
                .password(passwordEncoder.encode("secret"))
                .roles(Collections.singleton(user))
                .build()));
        log.info("Created user:" + userRepository.save(User.builder()
                .username("api")
                .email("api@sample.com")
                .password(passwordEncoder.encode("secret"))
                .roles(Collections.singleton(api))
                .build()));
        log.info("Created user:" + userRepository.save(User.builder()
                .username("apiReader")
                .email("apireader@sample.com")
                .password(passwordEncoder.encode("secret"))
                .roles(Collections.singleton(apiRead))
                .build()));
        log.info("Created user:" + userRepository.save(User.builder()
                .username("apiModifier")
                .email("apimodifier@sample.com")
                .password(passwordEncoder.encode("secret"))
                .roles(Collections.singleton(apiWrite))
                .build()));
        log.info("Created user:" + userRepository.save(User.builder()
                .username("apiFull")
                .email("apifull@sample.com")
                .password(passwordEncoder.encode("secret"))
                .roles(new HashSet<>(Arrays.asList(apiRead, apiWrite)))
                .build()));
    }

    private void loadPassengers(){
        log.info("Preloading passenger:" + passengerRepository.save(Passenger.builder()
                .code("11111111L")
                .firstName("Harry")
                .lastName("Potter")
                .email("hello@sample.com")
                .address("London")
                .build()));
        log.info("Preloading passenger: " + passengerRepository.save(Passenger.builder()
                .code("22222222L")
                .firstName("Ronald")
                .lastName("Weasley")
                .email("hello@sample.com")
                .address("London")
                .build()));
        log.info("Preloading passenger: " + passengerRepository.save(Passenger.builder()
                .code("33333333L")
                .firstName("Hermione")
                .lastName("Granger")
                .email("hello@sample.com")
                .address("London")
                .build()));
    }

    private void loadAirlines(){
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("QA").companyName("Qatar Airways").country("QATAR").countryCode("QT").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("SA").companyName("Singapore Airlines").country("SINGAPORE").countryCode("SG").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("AN").companyName("ANA All Nippon Airways").country("JAPAN").countryCode("JP").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("EM").companyName("Emirates").country("EMIRATES").countryCode("EM").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("JA").companyName("Japan Airlines").country("JAPAN").countryCode("JP").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("CP").companyName("Cathay Pacific Airways").country("CHINA").countryCode("CH").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("EA").companyName("EVA Air").country("TAIWAN").countryCode("TW").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("QN").companyName("Qantas Airways").country("AUSTRALIA").countryCode("AU").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("HA").companyName("Hainan Airlines").country("CHINA").countryCode("CH").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("AF").companyName("Air France").country("FRANCE").countryCode("FR").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("RY").companyName("Ryanair").country("IRELAND").countryCode("IR").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("LF").companyName("Lufthansa").country("GERMANY").countryCode("GE").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("EJ").companyName("EasyJet").country("UNITED KINGDOM").countryCode("UK").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("IB").companyName("Iberia").country("SPAIN").countryCode("SP").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("AE").companyName("Aegean Airlines").country("GREECE").countryCode("GR").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("AL").companyName("Aer Lingus").country("IRELAND").countryCode("IR").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("NW").companyName("Norwegian").country("NORWAY").countryCode("NO").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("BA").companyName("British Airways").country("UNITED KINGDOM").countryCode("UK").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("VA").companyName("Virgin Atlantic").country("UNITED KINGDOM").countryCode("UK").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("FA").companyName("Finnair").country("FINLAND").countryCode("FI").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("FL").companyName("Aeroflot").country("RUSSIA").countryCode("RU").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("KL").companyName("KLM Royal Dutch Airlines").country("NETHERLAND").countryCode("PH").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("TK").companyName("Turkish Airlines").country("TURKEY").countryCode("TK").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("AU").companyName("Australian Airlines").country("AUSTRALIA").countryCode("AU").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("SW").companyName("Swiss International Air Lines").country("SWEDEN").countryCode("SW").build()));
        log.info("Preloading Airline: " + airlineRepository.save(Airline.builder().company("VU").companyName("Vueling").country("SPAIN").countryCode("SP").build()));
    }

    private void loadPlanes() throws Exception {
        Airline qatarAirways = this.airlineRepository.findByCompany("QA");
        Airline vueling = this.airlineRepository.findByCompany("VU");
        Airline ryanair = this.airlineRepository.findByCompany("RY");
        Airline norwegian = this.airlineRepository.findByCompany("NW");
        Airline easyJet = this.airlineRepository.findByCompany("EJ");
        Airline iberia = this.airlineRepository.findByCompany("IB");
        Airline turkishAirlines = this.airlineRepository.findByCompany("TK");
        Airline airFrance = this.airlineRepository.findByCompany("AF");
        List<Airline> airlines = Arrays.asList(qatarAirways, vueling, ryanair, norwegian, easyJet, iberia, turkishAirlines, airFrance);

        if (airlines.stream().noneMatch(Objects::isNull)){
            Random random = new Random();
            int airlinesSize = airlines.size();
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(133).model("Airbus A220-100").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(160).model("Airbus A220-300").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(266).model("Airbus A300").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(47).capacityType(PlaneCapacityType.CARGO).model("Airbus A300-600ST Beluga").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(240).model("Airbus A310").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(156).model("Airbus A319").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(189).model("Airbus A320").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(236).model("Airbus A321").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(277).model("Airbus A330").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(300).model("Airbus A340").owner(airlines.get(random.nextInt(airlinesSize))).build()));

            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(126).model("Boeing 737").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(366).model("Boeing 747").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(289).model("Boeing 757-300").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(550).model("Boeing 777-300ER").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(440).model("Boeing 787-10").owner(airlines.get(random.nextInt(airlinesSize))).build()));

            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(105).model("Comac C919").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(100).model("Concorde").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(56).model("Dash 8").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(44).model("Canadair Regional Jet").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(250000).capacityType(PlaneCapacityType.CARGO).model("Antonov 225").owner(airlines.get(random.nextInt(airlinesSize))).build()));

            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(50).model("Embraer 135/145").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(80).model("Embraer 170/175").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(88).model("Embraer 175").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(114).model("Embraer 190/195").owner(airlines.get(random.nextInt(airlinesSize))).build()));
            log.info("Preloading plane: " + planeRepository.save(Plane.builder().capacity(122).model("Embraer 195").owner(airlines.get(random.nextInt(airlinesSize))).build()));
        }
        else {
            throw new Exception("Cannot retreive all Airlines created before. Exiting.");
        }

    }

    /**
     * Load the role values to RoleValue dynamic enum class.
     */
    private void loadRoleValues(){
        this.roleRepository.findAll().forEach(role -> {
            RoleValue.putValue(role.getName());
        });
    }
}
