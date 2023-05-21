package ru.otus.app.configs.providers;

public interface LocalizationSettingsProvider {

    String getStudentNameGreetingMessage();

    String getStudentSurnameGreetingMessage();

    String getOutputQuestionMessage();

    String getTestPassMessage();

    String getTestFailedMessage();

}
