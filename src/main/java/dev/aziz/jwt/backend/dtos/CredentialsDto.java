package dev.aziz.jwt.backend.dtos;

public record CredentialsDto (String login, char[] password) { }