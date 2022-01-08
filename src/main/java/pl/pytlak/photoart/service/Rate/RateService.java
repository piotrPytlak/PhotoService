package pl.pytlak.photoart.service.Rate;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.dto.request.AddRateRequest;
import pl.pytlak.photoart.entitiyKey.RateId;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.Rate;
import pl.pytlak.photoart.entity.RateCategories;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.repository.RateRepository;
import pl.pytlak.photoart.service.authentication.AuthenticationService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class RateService {

    private final RateRepository rateRepository;
    private final AuthenticationService authenticationService;

    public void addRateByPhotoId(AddRateRequest addRateRequest) {

        User currentUser = authenticationService.getCurrentUser();

        rateRepository.saveAll(addRateRequest.getRateObjList().stream().
                filter(x -> x.getValue() != null)
                .map(x -> Rate.builder()
                .rateId(RateId.builder()
                        .photoId(addRateRequest.getPhotoId())
                        .categoryId(x.getId())
                        .userId(currentUser.getId())
                        .build())
                .photo(Photo.builder()
                        .id(addRateRequest.getPhotoId())
                        .build())
                .rateValue(x.getValue())
                .rateCategories(RateCategories.builder()
                        .id(x.getId())
                        .build())
                .user(currentUser)
                .build())
                .collect(Collectors.toList()));

    }
}
