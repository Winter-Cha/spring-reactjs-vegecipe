package com.vegecipe.domain.community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    GENERAL("GENERAL", "일반"),
    NOTICE("NOTICE", "공지"),
    EVENT("EVENT", "행사")
    ;

    private final String key;
    private final String title;
}
