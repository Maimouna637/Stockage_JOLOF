package com.mycompany.myapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EquipeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2L * Integer.MAX_VALUE));

    public static Equipe getEquipeSample1() {
        return new Equipe().id(1L).nom("nom1").prenom("prenom1");
    }

    public static Equipe getEquipeSample2() {
        return new Equipe().id(2L).nom("nom2").prenom("prenom2");
    }

    public static Equipe getEquipeRandomSampleGenerator() {
        return new Equipe().id(longCount.incrementAndGet()).nom(UUID.randomUUID().toString()).prenom(UUID.randomUUID().toString());
    }
}
