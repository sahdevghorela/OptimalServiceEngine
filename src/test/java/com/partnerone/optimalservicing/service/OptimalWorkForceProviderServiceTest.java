package com.partnerone.optimalservicing.service;

import com.partnerone.optimalservicing.model.WorkForce;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;


public class OptimalWorkForceProviderServiceTest {

    private final OptimalWorkForceProviderService workForceProviderService = new OptimalWorkForceProviderService();

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAnyStructureIsZero() {
        //Given
        int[] structures = {1, 0};

        //When
        workForceProviderService.optimalService(structures, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAnyStructureIsNegative() {
        //Given
        int[] structures = {1, -1};

        //When
        workForceProviderService.optimalService(structures, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenStructuresAreMoreThanHundred() {
        //Given
        int[] structures = new int[101];

        //When
        workForceProviderService.optimalService(structures, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenAnyStructureHasMoreThanHundredRooms() {
        //Given
        int[] structures = {10, 100, 101};

        //When
        workForceProviderService.optimalService(structures, 10, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenJuniorCapacityIsZero() {
        //Given
        int juniorCapacity = 0;

        //When
        workForceProviderService.optimalService(new int[]{10}, 10, juniorCapacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenJuniorCapacityIsNegative() {
        //Given
        int negativeJuniorCapacity = -1;

        //When
        workForceProviderService.optimalService(new int[]{10}, 10, negativeJuniorCapacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSeniorCapacityIsZero() {
        //Given
        int zeroSeniorCapacity = 0;

        //When
        workForceProviderService.optimalService(new int[]{10}, zeroSeniorCapacity, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSeniorCapacityIsNegative() {
        //Given
        int negativeSeniorCapacity = -1;

        //When
        workForceProviderService.optimalService(new int[]{10}, negativeSeniorCapacity, 1);
    }

    @Test
    public void shouldReturnAsManyNumberOfWorkForcesAsGivenStructurs() {
        //Given
        int[] structures = {10, 20};

        //When
        List<WorkForce> workForces = workForceProviderService.optimalService(structures, 6, 20);

        //Then
        assertEquals(2, workForces.size());
    }

    @Test
    public void shouldReturnAtLeastOneSeniorWhenJuniorsFitsOptimal() {
        //Given
        int[] structures = {18};
        int seniorCapacity = 10;
        int juniorCapacity = 6;

        //When
        List<WorkForce> workForces = workForceProviderService.optimalService(structures, seniorCapacity, juniorCapacity);

        //Then
        assertEquals(2, workForces.get(0).getSeniors());
        assertEquals(0, workForces.get(0).getJuniors());
    }

    @Test
    public void shouldReturnWorkForceWithLeastOverCapacity() {
        //Given
        int[] structures = {35};
        int seniorCapacity = 10;
        int juniorCapacity = 6;

        //When
        List<WorkForce> workForces = workForceProviderService.optimalService(structures, seniorCapacity, juniorCapacity);

        //Then
        assertEquals(3, workForces.get(0).getSeniors());
        assertEquals(1, workForces.get(0).getJuniors());
    }

    @Test
    public void shouldReturnWorkForceWithLeastNumberOfSeniorsWhenMultipleOptimalsArePresent() {
        //Given
        int[] structures = {50};
        int seniorCapacity = 10;
        int juniorCapacity = 10;

        //When
        List<WorkForce> workForces = workForceProviderService.optimalService(structures, seniorCapacity, juniorCapacity);

        //Then
        assertEquals(1, workForces.get(0).getSeniors());
        assertEquals(4, workForces.get(0).getJuniors());
    }


}