package com.example.models;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Topic {
    SPORT, FOOD, BOOKS;
    public static List<Topic> getTopicList() {
        return Stream.of(Topic.values()).collect(Collectors.toList());
    }
}
