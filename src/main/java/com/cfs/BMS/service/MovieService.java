package com.cfs.BMS.service;


import com.cfs.BMS.dto.MovieRequest;
import com.cfs.BMS.entity.Movie;
import com.cfs.BMS.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;

    public Movie addMovie(MovieRequest request)
    {
        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .genre(request.getGenre())
                .language(request.getLanguage())
                .durationMinutes((double) request.getDurationMinutes())
                .rating(request.getRating())
                .releaseDate(request.getReleaseDate())
                .build();

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

    }

    public List<Movie> searchByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<Movie> getByGenre(String genre) {
        return movieRepository.findByGenre(genre);
    }

    public List<Movie> getByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }


    public Movie updateMovie(Long id, MovieRequest request) {
        Movie existing = getMovieById(id);
        existing.setTitle(request.getTitle());
        existing.setDescription(request.getDescription());
        existing.setGenre(request.getGenre());
        existing.setLanguage(request.getLanguage());
        existing.setDurationMinutes(Double.valueOf(request.getDurationMinutes()));
        existing.setRating(request.getRating());
        existing.setReleaseDate(request.getReleaseDate());
        existing.setPosterUrl(request.getPosterUrl());
        return movieRepository.save(existing);
    }

    // Delete movie
    public void deleteMovie(Long id) {

        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Movie not found with id: " + id));

        movieRepository.delete(movie);
    }
    
}


