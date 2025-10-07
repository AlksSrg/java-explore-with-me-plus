package ru.practicum.user.utill;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class UserGetParam {
    private List<Long> ids;
    private Integer from;
    private Integer size;
}
