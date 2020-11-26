package com.study.service.slack.dto;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
public class MessageRequest {
    private final String channel;
    private final List<SlackBlock> blocks;

    @AllArgsConstructor
    @Getter
    public static class SlackBlock {
        private SlackBlockType type;
        private SlackBlockText text;
    }

    @AllArgsConstructor
    @Getter
    public static class SlackBlockText {
        private SlackBlockType type;
        private String text;

    }

    public enum SlackBlockType {
        SECTION("section"), MRKDWN("mrkdwn");

        private String name;

        SlackBlockType(String name) {
            this.name = name;
        }

        @JsonValue
        public String getName() {
            return name;
        }

    }


}
