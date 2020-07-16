package isd.alprserver.controllers;



import isd.alprserver.model.ParkingPlan;
import isd.alprserver.services.interfaces.ParkingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@RestController
@RequestMapping("/parkingplan")
@CrossOrigin("*")
@RequiredArgsConstructor
public class ParkingPlanController {

    private final ParkingPlanService parkingPlanService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("imageFile") MultipartFile file) throws IOException {
        System.out.println("Original Image Byte Size - " + file.getBytes().length);
        ParkingPlan img = ParkingPlan.builder().id(1).photo(file.getBytes()).build();
        parkingPlanService.add(img);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping(path = { "/get" })
    public ParkingPlan getImage() throws IOException {
        final Optional<ParkingPlan> retrievedImage = parkingPlanService.findPlanById(1);
        return new ParkingPlan(retrievedImage.get().getPhoto());
    }

}

