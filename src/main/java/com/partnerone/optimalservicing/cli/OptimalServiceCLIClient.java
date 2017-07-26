package com.partnerone.optimalservicing.cli;

import com.partnerone.optimalservicing.service.OptimalWorkForceProviderService;

import java.util.Arrays;

public class OptimalServiceCLIClient {

    private static final OptimalWorkForceProviderService service = new OptimalWorkForceProviderService();

    public static void main(String[] args) {
        int[] portfolio = null;
        int seniorCapacity = 0;
        int juniorCapacity = 0;

        try {
            portfolio = Arrays.stream(args[0].split(",")).mapToInt(Integer::parseInt).toArray();
            seniorCapacity = Integer.parseInt(args[1]);
            juniorCapacity = Integer.parseInt(args[2]);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Enter rooms ',' separated and capacities space separated. Eg. format. 35,24,28 10 6");
        }

        service.optimalService(portfolio, seniorCapacity, juniorCapacity)
                .forEach(System.out::println);
    }
}
