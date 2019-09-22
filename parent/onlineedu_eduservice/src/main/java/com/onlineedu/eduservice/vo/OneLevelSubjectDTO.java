package com.onlineedu.eduservice.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneLevelSubjectDTO {
    private String id;
    private String title;
    private List<TwoLevelSubjectDTO> children = new ArrayList<>();
}
