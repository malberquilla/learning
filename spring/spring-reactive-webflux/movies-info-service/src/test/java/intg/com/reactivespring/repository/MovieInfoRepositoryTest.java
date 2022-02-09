package com.reactivespring.repository;

import com.reactivespring.domain.MovieInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataMongoTest
@ActiveProfiles("test") // If not specified, use the default spring active profile
class MovieInfoRepositoryTest {

    @Autowired
    MovieInfoRepository movieInfoRepository;

    @BeforeEach
    void setUp() {
        var movieInfos = List.of(new MovieInfo(null, "Batman Begins",
                        2005, List.of("Christian Bale", "Michael Cane"), LocalDate.parse("2005-06-15")),
                new MovieInfo(null, "The Dark Knight",
                        2008, List.of("Christian Bale", "HeathLedger"), LocalDate.parse("2008-07-18")),
                new MovieInfo("abc", "Dark Knight Rises",
                        2012, List.of("Christian Bale", "Tom Hardy"), LocalDate.parse("2012-07-20")));

        movieInfoRepository.saveAll(movieInfos)
                .blockLast();
    }

    @AfterEach
    void tearDown() {
        movieInfoRepository.deleteAll().block();
    }

    @Test
    void findAll() {
        // given

        // when
        var movieInfoFlux = movieInfoRepository.findAll().log();

        // then
        StepVerifier.create(movieInfoFlux)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    void findById() {
        // given

        // when
        var movieInfoMono = movieInfoRepository.findById("abc").log();

        // then
        StepVerifier.create(movieInfoMono)
                .assertNext(movieInfo -> {
                    assertEquals("Dark Knight Rises", movieInfo.getName());
                })
                .verifyComplete();
    }

    @Test
    void saveMovieInfo() {
        // given
        var movieInfo = new MovieInfo(null, "Spiderman: No way home",
                2005, List.of("Tom Holland", "Tobey Maguire", "Andrew Garfield"), LocalDate.parse("2021-12-16"));
        // when
        var movieInfoMono = movieInfoRepository.save(movieInfo).log();

        // then
        StepVerifier.create(movieInfoMono)
                .assertNext(mi -> {
                    assertNotNull(mi.getMovieInfoId());
                    assertEquals("Spiderman: No way home", mi.getName());
                });
    }

    @Test
    void updateMovieInfo() {
        // given
        var movieInfo = movieInfoRepository.findById("abc").log().block();
        movieInfo.setYear(2021);
        // when
        var movieInfoMono = movieInfoRepository.save(movieInfo).log();

        // then
        StepVerifier.create(movieInfoMono)
                .assertNext(mi -> {
                    assertEquals(2021, mi.getYear());
                });
    }

    @Test
    void deleteMovieInfo() {
        // given

        // when
        movieInfoRepository.deleteById("abc").log().block();
        var movieInfoFlux = movieInfoRepository.findAll().log();

        // then
        StepVerifier.create(movieInfoFlux)
                .expectNextCount(2)
                .verifyComplete();
    }
}