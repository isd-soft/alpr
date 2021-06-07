package isd.alpr_mobile.main.model;

import java.util.ArrayList;
import java.util.List;


public class LicensePlateRequest {
    private List<String> plates;

    public LicensePlateRequest(String licensePlate) {
        this.plates = new ArrayList<>();
        plates.add(licensePlate);
    }

    public List<String> getLicensePlate() {
        return plates;
    }
}
