package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MaterielSurLeTerrainTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MaterielSurLeTerrain getMaterielSurLeTerrainSample1() {
        return new MaterielSurLeTerrain()
            .id(1L)
            .serie1("serie11")
            .nombre1(1)
            .serie2("serie21")
            .nombre2(1)
            .serie3("serie31")
            .nombre3(1)
            .serie4("serie41")
            .nombre4(1);
    }

    public static MaterielSurLeTerrain getMaterielSurLeTerrainSample2() {
        return new MaterielSurLeTerrain()
            .id(2L)
            .serie1("serie12")
            .nombre1(2)
            .serie2("serie22")
            .nombre2(2)
            .serie3("serie32")
            .nombre3(2)
            .serie4("serie42")
            .nombre4(2);
    }

    public static MaterielSurLeTerrain getMaterielSurLeTerrainRandomSampleGenerator() {
        return new MaterielSurLeTerrain()
            .id(longCount.incrementAndGet())
            .serie1(UUID.randomUUID().toString())
            .nombre1(intCount.incrementAndGet())
            .serie2(UUID.randomUUID().toString())
            .nombre2(intCount.incrementAndGet())
            .serie3(UUID.randomUUID().toString())
            .nombre3(intCount.incrementAndGet())
            .serie4(UUID.randomUUID().toString())
            .nombre4(intCount.incrementAndGet());
    }
}
