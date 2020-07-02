package isd.alpr_mobile.main.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car implements Serializable {
    @SerializedName("id")
    public long id;
    @SerializedName("licensePlate")
    public String licensePlate;
    @SerializedName("brand")
    public String brand;
    @SerializedName("model")
    public String model;
    @SerializedName("color")
    public String color;
    @SerializedName("ownerEmail")
    public String ownerEmail;
    @SerializedName("ownerName")
    public String ownerName;
    @SerializedName("ownerTelephone")
    public String ownerTelephone;
    @SerializedName("ownerCompany")
    public String ownerCompany;
    @SerializedName("status")
    public String status;
}
