package com.vegecipe.domain.community;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    NOTICE("NOTICE", "공지"),
    GENERAL("GENERAL", "일반"),
    INFORMATION("INFORMATION", "정보"),
    EVENT("EVENT", "이벤트")
    ;

    private final String key;
    private final String title;
}
