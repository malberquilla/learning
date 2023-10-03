package org.malberquilla.learning.streams;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.malberquilla.learning.models.Province;
import org.malberquilla.learning.utils.Utils;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class StreamsMain {

    public static final String OPERATION_SEPARATION =
        "-----------------------------------------------------------------------------------";

    public static void main(String[] args) {
        List<Province> provinceList = Utils.getProvinceList();

        // Population density > 50
        provinceList.stream()
            .filter(p -> p.getPopulationDensity() > 50)
            .map(Province::getName)
            .forEach(log::info);

        log.info(OPERATION_SEPARATION);

        // Distinct dialects
        provinceList.stream()
            .map(Province::getDialect)
            .distinct()
            .sorted()
            .forEach(log::info);

        log.info(OPERATION_SEPARATION);

        // Show the first 3 provinces
        provinceList.stream()
            .limit(3)
            .forEach(log::info);

        log.info(OPERATION_SEPARATION);

        // peek -> make an operation without stream alteration
        provinceList.stream()
            .peek(p -> log.info("Processing {}", p.getName()))
            .filter(p -> p.getPopulationDensity() > 70)
            .peek(
                p -> log.info("Processing {}, Density: {}", p.getName(), p.getPopulationDensity()))
            .map(Province::getName)
            .forEach(log::info);

        log.info(OPERATION_SEPARATION);

        Province maxPopulation = provinceList.stream()
            .max(Comparator.comparing(Province::getPopulation))
            .get();
        log.info(maxPopulation);

        log.info(OPERATION_SEPARATION);

        Province minDensity = provinceList.stream()
            .min(Comparator.comparing(Province::getPopulationDensity))
            .get();
        log.info(minDensity);

        log.info(OPERATION_SEPARATION);

        double averageLocations = provinceList.stream()
            .mapToInt(Province::getLocationsNumber)
            .average()
            .getAsDouble();
        log.info(averageLocations);

        log.info(OPERATION_SEPARATION);

        int totalPopulation = provinceList.stream()
            .mapToInt(Province::getPopulation)
            .sum();
        log.info(totalPopulation);

        log.info(OPERATION_SEPARATION);

        List<Province> spanishProvinces = provinceList.stream()
            .filter(p -> "Español".equalsIgnoreCase(p.getDialect()))
            .collect(Collectors.toList());
        log.info(spanishProvinces);

        log.info(OPERATION_SEPARATION);

        String dialects = provinceList.stream()
            .map(Province::getDialect)
            .distinct()
            .collect(Collectors.joining(" - "));
        log.info(dialects);

        log.info(OPERATION_SEPARATION);

        Map<String, List<Province>> groupingByDialect = provinceList.stream()
            .collect(Collectors.groupingBy(Province::getDialect));
        groupingByDialect.forEach((k, v) -> log.info("{}: {}", k, v));

        log.info(OPERATION_SEPARATION);
    }
}