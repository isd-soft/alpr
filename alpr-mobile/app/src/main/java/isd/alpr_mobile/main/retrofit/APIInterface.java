package isd.alpr_mobile.main.retrofit;

import java.util.List;

import isd.alpr_mobile.main.model.Car;
import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicenseValidationResponse;
import isd.alpr_mobile.main.model.Mail;
import isd.alpr_mobile.main.model.MailResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIInterface {
    @POST("/cars")
    Call<LicenseValidationResponse> validateLicensePlate(@Body List<LicensePlate> licensePlates);

    @GET("/cars/in")
    Call<List<Car>> getAllInCars();

    @POST("/email")
    Call<MailResponse> sendEmailNotification(@Body Mail mail);
}
