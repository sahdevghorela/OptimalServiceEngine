package com.partnerone.optimalservicing.cli;

import com.partnerone.optimalservicing.service.OptimalWorkForceProviderService;

public class OptimalServiceCLIClient {

    private static final OptimalWorkForceProviderService service = new OptimalWorkForceProviderService();

    public static void main(String[] args) {
        int []structures1 = {35,21,17,38};
        int []structures2 = {38};
        int []structures = {24,28};
        int seniorCapacity1 = 10;
        int seniorCapacity = 11;
        int juniorCapacity = 6;
        OptimalWorkForceProviderService
                .getOptimalNumber(structures,seniorCapacity,juniorCapacity)
                .forEach(System.out::println);
    }
}
