package isd.alpr_mobile.main.scan.license_plate_validation;

import java.util.List;

import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;

public interface OnPlateFoundListener {
    void onPlateFound(List<LicensePlate> plates);
}
