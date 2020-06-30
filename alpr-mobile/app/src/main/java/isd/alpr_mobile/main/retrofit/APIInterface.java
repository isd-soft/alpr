package isd.alpr_mobile.main.retrofit;

import isd.alpr_mobile.main.model.Car;
import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("/cars")
    Call<LicenseValidationResponse> validateLicensePlate(@Body LicensePlate licensePlate);
}
