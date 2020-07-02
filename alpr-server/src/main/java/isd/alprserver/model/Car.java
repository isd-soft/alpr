package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
@ToString(exclude = "user")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String licensePlate;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String brand;

    private String model;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    private void setStatus(int cars_id, int status_id){
        String sql = "UPDATE cars set statuses="+status_id+"where ID="+cars_id;
    }
}
