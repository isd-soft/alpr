package isd.alpr_mobile.main.scan.license_plate_validation;

import isd.alpr_mobile.main.model.LicenseValidationResponse;

public interface OnPlateFoundListener {
    void onPlateFound(LicenseValidationResponse response);
}
