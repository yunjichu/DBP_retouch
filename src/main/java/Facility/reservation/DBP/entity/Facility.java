package Facility.reservation.DBP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "FACILITY")
@Getter
@Setter
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FACILITY_ID")
    private Long facilityId;

    @Column(name = "FACILITY_NAME", length = 100)
    private String facilityName;

    @Column(name = "CAPACITY")
    private Integer capacity;

    @Column(name = "LOCATION", length = 255)
    private String location;

    @Lob
    @Column(name = "USAGE_GUIDELINES")
    private String usageGuidelines;

    @Column(name = "AVAILABLE_START_TIME")
    private Integer availableStartTime;

    @Column(name = "AVAILABLE_END_TIME")
    private Integer availableEndTime;

    @Transient // 데이터베이스와 매핑되지 않는 필드
    private Integer reservationCount;

    @Transient // 예약 가능 여부
    private boolean available;

    // Getter와 Setter 추가
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
