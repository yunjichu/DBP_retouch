package Facility.reservation.DBP.reporsitory;

import Facility.reservation.DBP.domain.Reservation;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcReservationRepository implements ReservationRepository{
    private final DataSource dataSource;
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
