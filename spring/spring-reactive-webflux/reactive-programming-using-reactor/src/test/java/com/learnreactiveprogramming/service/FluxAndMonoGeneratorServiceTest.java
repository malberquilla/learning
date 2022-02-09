package com.learnreactiveprogramming.service;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

public class FluxAndMonoGeneratorServiceTest {

    FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

    @Test
    void namesFlux() {
        // given

        // when
        var namesFlux = fluxAndMonoGeneratorService.namesFlux();

        // then
        StepVerifier.create(namesFlux)
                //.expectNext("max", "alex", "chloe")
                //.expectNextCount(3)
                .expectNext("max")
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFluxMap() {
        // given

        // when
        var namesFluxMap = fluxAndMonoGeneratorService.namesFluxMap();

        // then
        StepVerifier.create(namesFluxMap)
                .expectNext("MAX", "ALEX", "CHLOE")
                .verifyComplete();
    }

    @Test
    void testNamesFluxMap() {
        // given
        var length = 3;

        // when
        var namesFluxMap = fluxAndMonoGeneratorService.namesFluxMapLength(length);

        // then
        StepVerifier.create(namesFluxMap)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMap() {
        // given

        // when
        var namesFluxFlatMap = fluxAndMonoGeneratorService.namesFluxFlatMap();

        // then
        StepVerifier.create(namesFluxFlatMap)
                .expectNext("M", "A", "X", "A","L","E","X", "C","H","L","O","E")
                .verifyComplete();
    }

    @Test
    void namesFluxFlatMapAsync() {
        // given

        // when
        var namesFluxFlatMap = fluxAndMonoGeneratorService.namesFluxFlatMapAsync();

        // then
        StepVerifier.create(namesFluxFlatMap)
                //.expectNext("M", "A", "X", "A","L","E","X", "C","H","L","O","E")
                .expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namesFluxconcatMapAsync() {
        // given

        // when
        var namesFluxFlatMap = fluxAndMonoGeneratorService.namesFluxconcatMapAsync();

        // then
        StepVerifier.create(namesFluxFlatMap)
                .expectNext("M", "A", "X", "A","L","E","X", "C","H","L","O","E")
                //.expectNextCount(12)
                .verifyComplete();
    }

    @Test
    void namesMonoFlatMapFilter() {
        //given
        int stringLength = 3;

        // when
        var stringMono = fluxAndMonoGeneratorService.namesMonoFlatMapFilter(stringLength);

        //then
        StepVerifier.create(stringMono)
                .expectNext(List.of("A","L","E","X"))
                .verifyComplete();

    }

    @Test
    void namesMonoFlatMapManyFilter() {

        //given
        int stringLength = 3;

        // when
        var stringMono = fluxAndMonoGeneratorService.namesMonoFlatMapManyFilter(stringLength);

        //then
        StepVerifier.create(stringMono)
                .expectNext("A","L","E","X")
                .verifyComplete();
    }

    @Test
    void concatFlux() {

        var value = fluxAndMonoGeneratorService.concatFlux();

        StepVerifier.create(value)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void concatFluxWith() {
        var value = fluxAndMonoGeneratorService.concatFluxWith();
        StepVerifier.create(value)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void concatFluxWithMono() {
        var value = fluxAndMonoGeneratorService.concatFluxWithMono();
        StepVerifier.create(value)
                .expectNext("A", "B")
                .verifyComplete();
    }

    @Test
    void mergeFlux() {
        var value = fluxAndMonoGeneratorService.mergeFlux();
        StepVerifier.create(value)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void mergeFluxWith() {
        var value = fluxAndMonoGeneratorService.mergeFluxWith();
        StepVerifier.create(value)
                .expectNext("A", "D", "B", "E", "C", "F")
                .verifyComplete();
    }

    @Test
    void mergeSequentialFlux() {
        var value = fluxAndMonoGeneratorService.mergeSequentialFlux();
        StepVerifier.create(value)
                .expectNext("A", "B", "C", "D", "E", "F")
                .verifyComplete();
    }

    @Test
    void zipFlux() {
        var value = fluxAndMonoGeneratorService.zipFlux();

        StepVerifier.create(value)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }

    @Test
    void zipMapFlux() {

        var value = fluxAndMonoGeneratorService.zipMapFlux();

        StepVerifier.create(value)
                .expectNext("AD", "BE", "CF")
                .verifyComplete();
    }
}