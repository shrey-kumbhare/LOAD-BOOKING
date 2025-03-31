import lombok.Data;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
public class Load {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String shipperId;
    
    @Embedded
    private Facility facility;
    
    private String productType;
    private String truckType;
    private int noOfTrucks;
    private double weight;
    private String comment;
    private Timestamp datePosted;
    @Enumerated(EnumType.STRING)
    private LoadStatus status;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = LoadStatus.POSTED;
        }
        if (datePosted == null) {
            datePosted = new Timestamp(System.currentTimeMillis());
        }
    }

    @Entity
    @Data
    public static class Facility {
        private String loadingPoint;
        private String unloadingPoint;
        private Timestamp loadingDate;
        private Timestamp unloadingDate;
    }

    public enum LoadStatus {
        POSTED, BOOKED, CANCELLED
    }
}