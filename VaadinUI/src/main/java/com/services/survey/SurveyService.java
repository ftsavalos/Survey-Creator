package com.services.survey;


import com.models.User;
import com.models.survey.Survey;

import java.util.List;

public interface SurveyService {

    List<Survey> findSurveys();

    List<Survey> findSurveys(User user);

    Survey findSurvey(Long id);

    void addSurvey(Survey survey);

    void updateSurvey(Survey survey);

    void deleteSurvey(Long id);

}