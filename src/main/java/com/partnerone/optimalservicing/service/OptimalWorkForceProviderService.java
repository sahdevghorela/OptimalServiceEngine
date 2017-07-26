package com.partnerone.optimalservicing.service;

import com.partnerone.optimalservicing.model.WorkForce;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Arrays.stream;
import static java.util.Comparator.comparingInt;

public class OptimalWorkForceProviderService {

    private static final Function<SingleCapacityWorkForce, WorkForce> SENIOR_WORKFORCE_EXTRACTOR = (singleCapacityWorkForce) -> new WorkForce(singleCapacityWorkForce.getServicePersons(), 0, singleCapacityWorkForce.getOvercapacity());
    private static final Function<SingleCapacityWorkForce, WorkForce> JUNIOR_WORKFORCE_EXTRACTOR = singleCapacityWorkForce -> new WorkForce(0, singleCapacityWorkForce.getServicePersons(), singleCapacityWorkForce.getOvercapacity());

    public List<WorkForce> optimalService(int[] structures, int seniorCapacity, int juniorCapacity) {
        validate(structures, seniorCapacity, juniorCapacity);
        List<WorkForce> optimalWorkForces = new ArrayList<>();
        stream(structures)
                .forEach(structure -> optimalWorkForces.add(getOptimalWorkForce(structure, seniorCapacity, juniorCapacity)));
        return optimalWorkForces;
    }

    private void validate(int[] structures, int seniorCapacity, int juniorCapacity) {
        if (structures.length == 0
                || structures.length > 100
                || stream(structures).anyMatch(structure -> structure <= 0 || structure > 100)) {
            throw new IllegalArgumentException("Structure provided with 0, negative or more than 100 rooms");
        }
        if (seniorCapacity <= 0 || juniorCapacity <= 0) {
            throw new IllegalArgumentException("Both Senior and Junior capacity can't be 0 or negative");
        }
    }

    private static WorkForce getOptimalWorkForce(int rooms, int seniorCapacity, int juniorCapacity) {
        if (oneSeniorIsEnough(rooms, seniorCapacity)) {
            return oneSenior();
        }
        rooms = rooms - seniorCapacity;

        List<WorkForce> workForces = combinationOfWorkForces(rooms, seniorCapacity, juniorCapacity);

        Optional<WorkForce> optimalNumber = sortOutForOptimalWorkForce(workForces);

        return optimalNumber.map(optimalWorkForce -> {
            optimalWorkForce.addOneSenior();
            return optimalWorkForce;
        }).orElse(new WorkForce(0, 0, 0));
    }

    private static Optional<WorkForce> sortOutForOptimalWorkForce(List<WorkForce> workForces) {
        return workForces.stream()
                .sorted(
                        comparingInt(WorkForce::getOverCapacity)
                                .thenComparing(comparingInt(WorkForce::getSeniors))
                )
                .findFirst();
    }

    private static List<WorkForce> combinationOfWorkForces(int rooms, int seniorCapacity, int juniorCapacity) {
        List<WorkForce> workForces = optimizeForBothCapacities(rooms, seniorCapacity, juniorCapacity);
        WorkForce ofAllSeniors = optimizeForSingleCapacity(rooms, seniorCapacity, SENIOR_WORKFORCE_EXTRACTOR);
        WorkForce ofAllJuniors = optimizeForSingleCapacity(rooms, juniorCapacity, JUNIOR_WORKFORCE_EXTRACTOR);

        workForces.add(ofAllJuniors);
        workForces.add(ofAllSeniors);
        return workForces;
    }

    private static WorkForce oneSenior() {
        return new WorkForce(1, 0, 0);
    }

    private static List<WorkForce> optimizeForBothCapacities(int rooms, int seniorCapacity, int juniorCapacity) {
        List<WorkForce> list = new ArrayList<>();
        int noOfSeniors = 0;
        int overCapacity = 0;
        while (rooms >= seniorCapacity) {
            rooms = rooms - seniorCapacity;
            noOfSeniors++;
            int noOfJuniors = (int) Math.ceil((double) rooms / juniorCapacity);
            int remainder = rooms % juniorCapacity;
            if (remainder > 0) {
                overCapacity = juniorCapacity - remainder;
            }
            WorkForce optimalNumber = new WorkForce(noOfSeniors, noOfJuniors, overCapacity);
            list.add(optimalNumber);
        }
        return list;
    }


    private static WorkForce optimizeForSingleCapacity(int rooms, int capacity, Function<SingleCapacityWorkForce, WorkForce> mapper) {
        int overCapacity = 0;
        int servicePersons = (int) Math.ceil((double) rooms / capacity);
        int remainder = rooms % capacity;
        if (remainder > 0) {
            overCapacity = capacity - remainder;
        }
        SingleCapacityWorkForce singleCapacityWorkForce = new SingleCapacityWorkForce(servicePersons, overCapacity);
        return mapper.apply(singleCapacityWorkForce);
    }

    private static boolean oneSeniorIsEnough(int rooms, int seniorCapacity) {
        return rooms <= seniorCapacity;
    }

    private static class SingleCapacityWorkForce {
        private final int servicePersons;
        private final int overcapacity;

        private SingleCapacityWorkForce(int servicePersons, int overcapacity) {

            this.servicePersons = servicePersons;
            this.overcapacity = overcapacity;
        }

        private int getServicePersons() {
            return servicePersons;
        }

        private int getOvercapacity() {
            return overcapacity;
        }
    }
}
