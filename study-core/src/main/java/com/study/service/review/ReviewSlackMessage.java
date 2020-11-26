package com.study.service.review;

import com.study.service.slack.dto.MessageRequest;
import com.study.service.user.DeveloperType;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static com.study.service.slack.dto.MessageRequest.SlackBlockType.MRKDWN;
import static com.study.service.slack.dto.MessageRequest.SlackBlockType.SECTION;

/*
 * 리뷰 선정 slack api message 생성
 */
public class ReviewSlackMessage {

    public static MessageRequest makeReviewMatchingMessage(String channelName, List<Review> reviews) {

        LocalDateTime now = LocalDateTime.now();

        MessageRequest.SlackBlock headSection = new MessageRequest.SlackBlock(
                SECTION,
                new MessageRequest.SlackBlockText(
                        MRKDWN,
                        String.format("*:mega: %d년 %d월 %d일 리뷰어 매칭*", now.getYear(), now.getMonth().getValue(), now.getDayOfMonth())));

        MessageRequest.SlackBlock sectionFront = new MessageRequest.SlackBlock(
                SECTION,
                new MessageRequest.SlackBlockText(MRKDWN, "*프론트엔드*"));

        MessageRequest.SlackBlock sectionFrontBody = new MessageRequest.SlackBlock(
                SECTION,
                new MessageRequest.SlackBlockText(MRKDWN, makeReviewMessageBody(reviews, DeveloperType.FRONTEND)));

        MessageRequest.SlackBlock sectionBackend = new MessageRequest.SlackBlock(
                SECTION,
                new MessageRequest.SlackBlockText(MRKDWN, "*백엔드*"));

        MessageRequest.SlackBlock sectionBackendBody = new MessageRequest.SlackBlock(
                SECTION,
                new MessageRequest.SlackBlockText(MRKDWN, makeReviewMessageBody(reviews, DeveloperType.BACKEND)));

        return new MessageRequest(
                channelName,
                Arrays.asList(headSection, sectionFront, sectionFrontBody, sectionBackend, sectionBackendBody));
    }

    private static String makeReviewMessageBody(List<Review> reviews, DeveloperType devType) {
        return reviews
                .stream()
                .filter(e -> e.getReviewer().getDeveloperType().equals(devType))
                .map(r -> " - " + r.getReviewer().getName() + " -> " + r.getReviewee().getName())
                .reduce("", ((s1, s2) -> s1 + "\n" + s2));
    }
}
