package com.partnerone.optimalservicing.service;

import com.partnerone.optimalservicing.model.OptimalWorkForce;
import com.partnerone.optimalservicing.model.WorkForce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class OptimalWorkForceProviderService {

    private static final Function<IntermediateWorkForce,OptimalWorkForce> ALL_SENIORS = intermediateWorkForce -> {
        OptimalWorkForce optimalWorkForce = new OptimalWorkForce(intermediateWorkForce.getWorkforce1(),0,intermediateWorkForce.getOvercapacity());
        return optimalWorkForce;
    };
    private static final Function<IntermediateWorkForce,OptimalWorkForce> ALL_JUNIORS = intermediateWorkForce -> {
        OptimalWorkForce optimalWorkForce = new OptimalWorkForce(0,intermediateWorkForce.getWorkforce1(),intermediateWorkForce.getOvercapacity());
        return optimalWorkForce;
    };

    public static List<WorkForce> getOptimalNumber(int[] structures, int seniorCapacity, int juniorCapacity) {

        List<WorkForce> optimalNumbers = new ArrayList<>();
        Arrays.stream(structures)
                .forEach(structure -> optimalNumbers.add(getOptimalNumber(structure, seniorCapacity, juniorCapacity)));
        return optimalNumbers;
    }

    private static WorkForce getOptimalNumber(int rooms, int seniorCapacity, int juniorCapacity) {
        if (lessThanSeniorCapacity(rooms, seniorCapacity)) {
            WorkForce optimalWorkForce = new WorkForce();
            optimalWorkForce.setSeniors(1);
            optimalWorkForce.setJuniors(0);
            return optimalWorkForce;
        }
        rooms = rooms - seniorCapacity;
        List<OptimalWorkForce> list = remainderForSeniorsAndJuniors(rooms, seniorCapacity, juniorCapacity);
        OptimalWorkForce remainderForAllSeniors = remainderForAllSeniors(rooms, seniorCapacity,ALL_SENIORS);
        OptimalWorkForce remainderForAllJuniors = remainderForAllSeniors(rooms, juniorCapacity,ALL_JUNIORS);

        list.add(remainderForAllJuniors);
        list.add(remainderForAllSeniors);
        Optional<OptimalWorkForce> optimalNumber = list.stream().sorted().findFirst();
        return optimalNumber.map(optimalWorkForce -> {
            optimalWorkForce.addSeniors(1);
            return optimalWorkForce.getWorkForce();
        }).orElse(new WorkForce());
    }

    private static List<OptimalWorkForce> remainderForSeniorsAndJuniors(int rooms, int seniorCapacity, int juniorCapacity) {
        List<OptimalWorkForce> list = new ArrayList<>();
        int noOfSeniors = 0;
        int overCapacity = 0;
        while(rooms >= seniorCapacity){
            rooms = rooms - seniorCapacity;
            noOfSeniors++;
            int noOfJuniors = (int) Math.ceil((double)rooms/juniorCapacity);
            int remainder = rooms % juniorCapacity;
            if(remainder > 0){
                overCapacity = juniorCapacity - remainder;
            }
            OptimalWorkForce optimalNumber = new OptimalWorkForce(noOfSeniors,noOfJuniors,overCapacity);
            list.add(optimalNumber);
        }
        return list;
    }


    private static OptimalWorkForce remainderForAllSeniors(int rooms, int seniorCapacity, Function<IntermediateWorkForce,OptimalWorkForce> mapper) {
        int overCapacity = 0;
        int workforce1 = (int) Math.ceil((double) rooms / seniorCapacity);
        int remainder = rooms % seniorCapacity;
        if (remainder > 0) {
            overCapacity = seniorCapacity - remainder;
        }
        IntermediateWorkForce intermediateWorkForce = new IntermediateWorkForce(workforce1,overCapacity);
        return mapper.apply(intermediateWorkForce);
    }

    private static boolean lessThanSeniorCapacity(int rooms, int seniorCapacity) {
        return rooms <= seniorCapacity;
    }

    private static class IntermediateWorkForce{
        private final int workforce1;
        private final int overcapacity;

        private IntermediateWorkForce(int workforce1, int overcapacity){

            this.workforce1 = workforce1;
            this.overcapacity = overcapacity;
        }

        private int getWorkforce1() {
            return workforce1;
        }

        private int getOvercapacity() {
            return overcapacity;
        }
    }
}
