package com.cfs.BMS.controller;


import com.cfs.BMS.dto.TheaterRequest;
import com.cfs.BMS.dto.UserRequest;
import com.cfs.BMS.entity.Theater;
import com.cfs.BMS.entity.User;
import com.cfs.BMS.service.TheaterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
public class TheaterController {

    private final TheaterService theaterService;

    //post add
    @PostMapping
    private ResponseEntity<Theater> addTheater(@RequestBody TheaterRequest request)
    {
        return  ResponseEntity.ok(theaterService.addTheater(request));
    }



    @GetMapping
    public ResponseEntity<List<Theater>> getAllTheaters()
    {
        return ResponseEntity.ok(theaterService.getAllTheaters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Theater> getTheaterById(@PathVariable Long id)
    {
        return ResponseEntity.ok(theaterService.getTheaterById(id));
    }

    @GetMapping("/city/{cityId}")
    public ResponseEntity<List<Theater>> getTheaterByCity(@PathVariable Long cityId)
    {
        return ResponseEntity.ok(theaterService.getTheaterByCity(cityId));
    }


}
