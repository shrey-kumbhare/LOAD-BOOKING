import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String loadId;
    private String transporterId;
    private double proposedRate;
    private String comment;
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    private Timestamp requestedAt;

    @PrePersist
    protected void onCreate() {
        if (requestedAt == null) {
            requestedAt = new Timestamp(System.currentTimeMillis());
        }
    }

    public enum BookingStatus {
        PENDING, ACCEPTED, REJECTED
    }
}