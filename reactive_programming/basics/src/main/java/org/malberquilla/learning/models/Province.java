package org.malberquilla.learning.models;

import lombok.Data;

@Data
public class Province {

    private String capital;
    private String dialect;
    private int locationsNumber;
    private String name;
    private int population;
    private double populationDensity;
    private int postalCode;

    public Province(String name, int locationsNumber, int population, int postalCode,
        String dialect,
        String capital, double populationDensity) {
        this.name = name;
        this.locationsNumber = locationsNumber;
        this.population = population;
        this.postalCode = postalCode;
        this.dialect = dialect;
        this.capital = capital;
        this.populationDensity = populationDensity;
    }

    @Override
    public String toString() {
        return "Province{" + "name='" + name + '\''
            + ", locationsNumber=" + locationsNumber
            + ", population=" + population
            + ", postalCode=" + postalCode
            + ", dialect='" + dialect + '\''
            + ", capital='" + capital + '\''
            + ", populationDensity=" + populationDensity
            + '}';
    }
}
