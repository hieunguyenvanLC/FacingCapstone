package capstone.fps.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;

@Entity
@Table(name = "fr_schedule", catalog = "fpsdb", schema = "fpsdb")
@XmlRootElement
public class FRSchedule {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "FR_Store_id")
    private FRStore store;
    @ManyToOne
    @JoinColumn(name = "FR_Weekday_id")
    private FRWeekday weekday;
    @Column(name = "open_hour")
    private Integer openHour;
    @Column(name = "open_minute")
    private Integer openMinute;
    @Column(name = "close_hour")
    private Integer closeHour;
    @Column(name = "close_minute")
    private Integer closeMinute;

    public FRSchedule() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FRStore getStore() {
        return store;
    }

    public void setStore(FRStore store) {
        this.store = store;
    }

    public FRWeekday getWeekday() {
        return weekday;
    }

    public void setWeekday(FRWeekday weekday) {
        this.weekday = weekday;
    }

    public Integer getOpenHour() {
        return openHour;
    }

    public void setOpenHour(Integer openHour) {
        this.openHour = openHour;
    }

    public Integer getOpenMinute() {
        return openMinute;
    }

    public void setOpenMinute(Integer openMinute) {
        this.openMinute = openMinute;
    }

    public Integer getCloseHour() {
        return closeHour;
    }

    public void setCloseHour(Integer closeHour) {
        this.closeHour = closeHour;
    }

    public Integer getCloseMinute() {
        return closeMinute;
    }

    public void setCloseMinute(Integer closeMinute) {
        this.closeMinute = closeMinute;
    }
}
