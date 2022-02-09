package com.learnreactiveprogramming.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class FluxAndMonoGeneratorService {

    public Flux<String> namesFlux() {
        return Flux.fromIterable(List.of("max", "alex", "chloe")).log();
    }

    public Mono<String> nameMono() {
        return Mono.just("alex");
    }

    public Mono<String> namesMonoMapFilter(int stringLength) {
        return nameMono()
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength);
    }

    public Mono<List<String>> namesMonoFlatMapFilter(int stringLength) {
        return nameMono()
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMap(this::splitStringMono).log();
    }

    public Flux<String> namesMonoFlatMapManyFilter(int stringLength) {
        return nameMono()
                .map(String::toUpperCase)
                .filter(s -> s.length() > stringLength)
                .flatMapMany(this::splitString).log();
    }

    private Mono<List<String>> splitStringMono(String s) {
        var charArray = s.split("");
        return Mono.just(List.of(charArray));
    }

    public Flux<String> namesFluxMap() {
        return namesFlux().map(String::toUpperCase).log();
    }

    public Flux<String> namesFluxMapLength(int stringLength) {
        return namesFluxMap()
                .filter(s -> s.length() > stringLength).log();
    }

    public Flux<String> namesFluxFlatMap() {
        return namesFluxMap().flatMap(this::splitString).log();
    }

    public Flux<String> namesFluxTransform() {
        Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase);
        var defaultFlux = Flux.just("default")
                .transform(filterMap);
        return namesFluxMap()
                .transform(filterMap)
                //.defaultIfEmpty("default")
                .switchIfEmpty(defaultFlux)
                .log();
    }

    public Flux<String> namesFluxFlatMapAsync() {
        return namesFluxMap().flatMap(this::splitStringWithDelay).log();
    }

    public Flux<String> namesFluxconcatMapAsync() {
        return namesFluxMap().concatMap(this::splitStringWithDelay).log();
    }

    public Flux<String> concatFlux() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return Flux.concat(abcFlux, defFlux);
    }

    public Flux<String> concatFluxWith() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");

        return abcFlux.concatWith(defFlux);
    }

    public Flux<String> concatFluxWithMono() {
        var aMono = Mono.just("A");
        var bMono = Mono.just("B");

        return aMono.concatWith(bMono);
    }

    public Flux<String> mergeFlux() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));

        return Flux.merge(abcFlux, defFlux).log();
    }

    public Flux<String> mergeFluxWith() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));

        return abcFlux.mergeWith(defFlux).log();
    }

    public Flux<String> mergeSequentialFlux() {
        var abcFlux = Flux.just("A", "B", "C")
                .delayElements(Duration.ofMillis(100));
        var defFlux = Flux.just("D", "E", "F")
                .delayElements(Duration.ofMillis(125));

        return Flux.mergeSequential(abcFlux, defFlux).log();
    }

    public Flux<String> zipFlux() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        // Same with zipWith and with Mono
        return Flux.zip(abcFlux, defFlux, (first, second) -> first + second).log();
    }

    public Flux<String> zipMapFlux() {
        var abcFlux = Flux.just("A", "B", "C");
        var defFlux = Flux.just("D", "E", "F");
        // Same with zipWith and with Mono
        return Flux.zip(abcFlux, defFlux).map(pair -> pair.getT1() + pair.getT2());
    }

    public Flux<String> splitString(String name) {
        return Flux.fromArray(name.split(""));
    }

    public Flux<String> splitStringWithDelay(String name) {
        var split = name.split("");
        var delay = new Random().nextInt(1000);
        return Flux.fromArray(split)
                .delayElements(Duration.ofMillis(delay));
    }

    public static void main(String[] args) {
        FluxAndMonoGeneratorService fluxAndMonoGeneratorService = new FluxAndMonoGeneratorService();

        fluxAndMonoGeneratorService.namesFlux()
                .subscribe(name -> {
                    System.out.println("Mono name is " + name);
                });

        fluxAndMonoGeneratorService.nameMono()
                .subscribe(name -> System.out.println("Name is " + name));
    }
}
