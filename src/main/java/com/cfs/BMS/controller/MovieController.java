package com.cfs.BMS.controller;


import com.cfs.BMS.dto.MovieRequest;
import com.cfs.BMS.entity.Movie;
import com.cfs.BMS.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;



    @PostMapping
    private ResponseEntity<Movie> addMovie(@RequestBody MovieRequest request)
    {
        return  ResponseEntity.ok(movieService.addMovie(request));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> UpdateMovie(@PathVariable Long id, @RequestBody MovieRequest movie)
    {
        return ResponseEntity.ok(movieService.updateMovie(id,movie));

    }


    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies()
    {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id)
    {
        return ResponseEntity.ok(movieService.getMovieById(id));
    }


    @GetMapping("/search")
    public ResponseEntity<List<Movie>> searchMovies(@RequestParam String title)
    {
        return ResponseEntity.ok(movieService.searchByTitle(title));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Long id){
        movieService.deleteMovie(id);
        return ResponseEntity.ok("Movie has been deleted");
    }
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<Movie>> getByGenre(@PathVariable String genre)
    {
        return ResponseEntity.ok(movieService.getByGenre(genre));
    }

    @GetMapping("/language/{language}")
    public ResponseEntity<List<Movie>> getByLanguage(@PathVariable String language)
    {
        return ResponseEntity.ok(movieService.getByLanguage(language));
    }
}
