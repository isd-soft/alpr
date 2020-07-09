package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "([A-Z]{3}\\s\\d{1,3}|[A-Z]{1,2}\\s[A-Z]{2}\\s\\d{1,3})")
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

    private byte[] photo;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", user=" + user.getId() +
                ", status=" + status.getId() +
                '}';
    }
}
