package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class MaterielEnStockTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static MaterielEnStock getMaterielEnStockSample1() {
        return new MaterielEnStock().id(1L).quantite(1).noserie("noserie1");
    }

    public static MaterielEnStock getMaterielEnStockSample2() {
        return new MaterielEnStock().id(2L).quantite(2).noserie("noserie2");
    }

    public static MaterielEnStock getMaterielEnStockRandomSampleGenerator() {
        return new MaterielEnStock()
            .id(longCount.incrementAndGet())
            .quantite(intCount.incrementAndGet())
            .noserie(UUID.randomUUID().toString());
    }
}
