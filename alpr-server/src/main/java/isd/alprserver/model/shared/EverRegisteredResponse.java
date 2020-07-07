package isd.alprserver.model.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EverRegisteredResponse {
    @NotNull
    private long peopleNumber;

    @NotNull
    private long carsNumber;

    @NotNull
    private long scansNumber;
}
