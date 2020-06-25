package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String licensePlate;

    private String brand;

    private String model;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
