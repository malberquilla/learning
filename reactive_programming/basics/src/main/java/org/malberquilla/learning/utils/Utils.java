package org.malberquilla.learning.utils;

import java.util.Arrays;
import java.util.List;

import org.malberquilla.learning.models.Province;

public class Utils {

    public static List<Province> getProvinceList() {
        return Arrays.asList(
            new Province("Madrid", 87, 67_886_543, 28, "Español", "Madrid", 90.67),

            new Province("Valencia", 45, 7876876, 46, "Valenciano", "Valencia", 91.3),

            new Province("Coruña", 39, 54545, 9, "Gallego", "Coruña", 78.23),

            new Province("Toledo", 26, 3677556, 38, "Español", "Toledo", 35.17),

            new Province("Ourense", 29, 788866, 27, "Gallego", "Ourense", 28.68),

            new Province("Cuenca", 15, 986442, 16, "Español", "Cuenca", 34.12),

            new Province("Barcelona", 74, 556779, 8, "Catalan", "Barcelona", 97.25),

            new Province("Zamora", 28, 987656, 42, "Español", "Zamora", 56.34),

            new Province("Guipuzcoa", 35, 432223, 20, "Euskera", "San Sebastian", 48.23),

            new Province("Vizcaya", 48, 567654, 6, "Euskera", "Bilbao", 54.89)
        );
    }
}
