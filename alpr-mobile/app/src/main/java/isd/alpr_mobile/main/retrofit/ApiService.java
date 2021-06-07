package isd.alpr_mobile.main.retrofit;

import java.util.List;

import isd.alpr_mobile.main.model.Car;
import isd.alpr_mobile.main.model.LicensePlate;
import isd.alpr_mobile.main.model.LicensePlateRequest;
import isd.alpr_mobile.main.model.ValidateCarDTO;
import isd.alpr_mobile.main.utility.HttpResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("api/cars/license_plate")
    Call<HttpResponse<ValidateCarDTO>> getCarByLicensePlate(@Body LicensePlateRequest request);

    @GET("/api/cars/in")
    Call<HttpResponse<List<Car>>> getAllInCars(@Query("search") String search);

    @POST("api/cars/status/{id}")
    Call<Void> changeCarStatus(@Path("id") long id);

}
