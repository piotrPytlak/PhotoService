package pl.pytlak.photoart.service.photo;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pl.pytlak.photoart.entity.Photo;
import pl.pytlak.photoart.entity.User;
import pl.pytlak.photoart.repository.PhotoRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;


    public String createName(User user, String originalFileName) throws IOException {

        ArrayList<Integer> numberList = new ArrayList<>();
        String userName = user.getUsername();
        int uniqNumber;

        Path path = Paths.get("C:\\Projects\\PhotoArt\\src\\main\\resources\\static\\images");

        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().startsWith(userName))
                    .collect(Collectors.toList());
        }


        for (Path p : result) {
            if(p.toString().matches("(.*?-.*?)-.*")) {
                String name = p.toString();
                String temp = StringUtils.substringBetween(name,"-","-");
                int number = Integer.parseInt(temp);
                numberList.add(number);
            }
        }

        if (numberList.isEmpty())
            uniqNumber = 1;
        else
            uniqNumber = Collections.max(numberList) + 1;

        return (userName + "-" +  uniqNumber +"-"+originalFileName  );

    }

    public Photo add(Photo photo) {
        return photoRepository.save(photo);
    }

}
