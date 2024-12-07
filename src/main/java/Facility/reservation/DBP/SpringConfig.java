package Facility.reservation.DBP;

import Facility.reservation.DBP.reporsitory.*;
import Facility.reservation.DBP.service.FacilityService;
import Facility.reservation.DBP.service.ReportService;
import Facility.reservation.DBP.service.ReservationService;
import Facility.reservation.DBP.service.StudentService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private final DataSource dataSource;
    private final EntityManager em;

    @Autowired
    public SpringConfig(DataSource dataSource, EntityManager em) {
        this.dataSource = dataSource;
        this.em = em;
    }


    @Bean
    public ReservationRepository reservationRepository() {
        return new JdbcReservationRepository(dataSource);
        // return new JdbcTemplateReservationRepository(dataSource);
        // return new JpaReservationRepository(em);
    }

    @Bean
    public StudentRepository studentRepository() {
        return new JdbcStudentRepository(dataSource);
        //return new JdbcTemplateStudentRepository(dataSource);
        // return new JpaStudentRepository(em);
    }

    @Bean
    public FacilityRepository facilityRepository() {
        return new JdbcFacilityRepository(dataSource);
        //return new JdbcTemplateFacilityRepository(dataSource);
        // return new JpaFacilityRepository(em);
    }

    @Bean
    public ReportRepository reportRepository() {
        return new JdbcReportRepository(dataSource);
        //return new JdbcTemplateReportRepository(dataSource);
        // return new JpaReportRepository(em);
    }

    @Bean
    public ReservationService reservationService() {
        return new ReservationService(reservationRepository());
    }

    @Bean
    public StudentService studentService() {
        return new StudentService(studentRepository());
    }

    @Bean
    public FacilityService facilityService() {
        return new FacilityService(facilityRepository());
    }

    @Bean
    public ReportService reportService() {
        return new ReportService(reportRepository(),reservationService());
    }

}



