package org.example.dto.mapping;

import org.example.dto.HorseResponse;
import org.example.dto.LessonRequest;
import org.example.dto.LessonResponseForAdmin;
import org.example.dto.LessonResponseForClient;
import org.example.dto.UserRegistrationResponse;
import org.example.model.Horse;
import org.example.model.Lesson;
import org.example.model.UserRegistration;
import org.example.repository.HorseRepository;
import org.example.repository.UserDataRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {HorseRepository.class, UserDataRepository.class})
public abstract class LessonMapping{

    @Autowired
    private HorseRepository horseRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Mapping(source="horses",target="horses", qualifiedByName = "horseIdsToEntity")
    @Mapping(source="participants",target="participants", qualifiedByName = "participantIdsToEntity")
    @Mapping(source = "instructorId", target = "instructor", qualifiedByName = "instructorToEntity")
    public abstract Lesson toEntity(LessonRequest lessonRequest);

    @Mapping(source = "instructor", target = "instructor", qualifiedByName = "instructorToResponse")
    @Mapping(source = "horses", target = "horses", qualifiedByName = "horsesToResponse")
    @Mapping(source="participants",target="participants", qualifiedByName = "participantsToResponse")
    public abstract  LessonResponseForAdmin toResponseForAdmin(Lesson lesson);

    @Mapping(source = "instructor", target = "instructor", qualifiedByName = "instructorToResponse")
    @Mapping(source="participants",target="currentParticipants", qualifiedByName = "countCurrentParticipants")
    public abstract LessonResponseForClient toResponseForClient(Lesson lesson);

    @Named("countCurrentParticipants")
    public Integer countCurrentParticipants(List<UserRegistration> participants){
        return participants != null ? participants.size() : 0;
    }

    @Named("horseIdsToEntity")
    public List<Horse> horseIdsToEntity(List<Long> horseIds){
        if (horseIds == null || horseIds.isEmpty()) {
            return new ArrayList<>();
        }
        return horseIds.stream()
                .map(id->horseRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("Horse with id "+id+" not found")))
                .toList();
    }

    @Named("participantIdsToEntity")
    public List<UserRegistration> participantIdsToEntity(List<Long> participantsIds){
        if (participantsIds == null || participantsIds.isEmpty()) {
            return new ArrayList<>();
        }
        return participantsIds.stream()
                .map(id->userDataRepository.findById(id)
                        .orElseThrow(()->new IllegalArgumentException("User with id "+id+" not found")))
                .toList();
    }



    @Named("participantsToResponse")
    public List<UserRegistrationResponse> participantsToResponse(List<UserRegistration> participants){
        if (participants == null || participants.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserRegistrationResponse> participantResponses = new ArrayList<>();
        for(UserRegistration participant: participants){
            participantResponses.add(new UserRegistrationResponse(
                    participant.getId(),
                    participant.getFirstName(),
                    participant.getLastName(),
                    participant.getEmail(),
                    participant.getPhoneNumber(),
                    participant.getRole()
            ));
        }
        return participantResponses;
    }


    @Named("horsesToResponse")
    public List<HorseResponse> horsesToResponse(List<Horse> horseEntities){
        if (horseEntities == null || horseEntities.isEmpty()) {
            return new ArrayList<>();
        }
        List<HorseResponse> horseResponses = new ArrayList<>();
        for(Horse horse: horseEntities){
            horseResponses.add(new HorseResponse(
                    horse.getId(),
//                    horse.getRegistrationNumber(),
                    horse.getName(),
                    horse.getBreed(),
                    horse.getAge())
            );
        }
        return horseResponses;
    }

    @Named("instructorToEntity")
    public UserRegistration instructorToEntity(Long instructorId){
        return userDataRepository.findById(instructorId)
                .orElseThrow(()->new IllegalArgumentException("Instructor with id "+instructorId+" not found"));

    }

    @Named("instructorToResponse")
    public UserRegistrationResponse instructorToResponse(UserRegistration instructor){
        return new UserRegistrationResponse(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmail(),
                instructor.getPhoneNumber(),
                instructor.getRole()
        );
    }
}