package ru.era.distributionoftasks.services.distributor.entity;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static ru.era.distributionoftasks.services.distributor.entity.Rang.*;

// TODO: 11.11.2023 Поправить стоимость задач
public class TaskAnalyser {
    public static Optional<Task> calcTaskForAgencyPoint(AgencyPoint agencyPoint) {
        return Stream.of(
                        checkByStimulateIssuance(agencyPoint),
                        checkByLearningAgent(agencyPoint),
                        checkByDeliveryMaterials(agencyPoint)
                )
                .filter(Optional::isPresent)
                .map(Optional::get)
                .min(Comparator.comparing(o -> o.getPriority().getPriority()));
    }

    private static Optional<Task> checkByStimulateIssuance(AgencyPoint agencyPoint) {
        boolean condition1 = agencyPoint.getNumberOfDaysOfIssue() > 7 && agencyPoint.getNumberOfApproved() > 0;
        boolean condition2 = agencyPoint.getNumberOfDaysOfIssue() > 14;
        if(condition1 || condition2) {
            return Optional.of(new Task(
                    240,
                    Priority.MAX_PRIORITY,
                    List.of(SENIOR_RANG),
                    10));
        } else {
            return Optional.empty();
        }

    }

    private static Optional<Task> checkByLearningAgent(AgencyPoint agencyPoint) {
        if (agencyPoint.getNumberOfIssued() > 0 && (1.0 * agencyPoint.getNumberOfIssued() / agencyPoint.getNumberOfApproved()) * 100 < 50) {
            return Optional.of(new Task(
                    120,
                    Priority.MEDIUM_PRIORITY,
                    List.of(SENIOR_RANG, MIDDLE_RANG),
                    4));
        } else {
            return Optional.empty();
        }
    }

    private static Optional<Task> checkByDeliveryMaterials (AgencyPoint agencyPoint) {
        if (agencyPoint.getPointsConnected().equals("вчера") || !agencyPoint.isDelivered()) {
            return Optional.of(new Task(
                    90,
                    Priority.LOW_PRIORITY,
                    List.of(SENIOR_RANG, MIDDLE_RANG, JUNIOR_RANG),
                    1));
        } else {
            return Optional.empty();
        }
    }

}
