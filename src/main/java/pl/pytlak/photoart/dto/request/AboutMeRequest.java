package pl.pytlak.photoart.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AboutMeRequest {

    @NotNull
    private String content;
}
