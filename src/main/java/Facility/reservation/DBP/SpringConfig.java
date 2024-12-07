package Facility.reservation.DBP;

import Facility.reservation.DBP.reporsitory.JdbcReservationRepository;
import Facility.reservation.DBP.reporsitory.ReservationRepository;
import Facility.reservation.DBP.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private  final DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //private final EntityManager em;    // for JPA
    //@Autowired
    //public SpringConfig(EntityManager em) {
    //    this.em = em;}

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepository());
    }

    @Bean
    public ReservationRepository reservationRepository(){
        return new JdbcReservationRepository(dataSource);
        //return new JdbcTemplatesReservationRepository(dataSource);
        //return new JPAReservationRepository(em);
    }
}


