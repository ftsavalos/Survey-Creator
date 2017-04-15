package com.services.survey.surveyimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.models.User;
import com.models.survey.Survey;
import com.repositories.survey.SurveyRepository;
import com.services.survey.SurveyService;

import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService{

    @Autowired
    private final SurveyRepository surveyRepository;

    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }


    @Override
    public List<Survey> findSurveys() {
        return this.surveyRepository.findAll();
    }

    @Override
    public List<Survey> findSurveys(User user) {
        return this.surveyRepository.findSurveysByUser(user);
    }

    @Override
    public Survey findSurvey(Long id) {
        return this.surveyRepository.findOne(id);
    }

    @Override
    public void addSurvey(Survey survey) {
        this.surveyRepository.save(survey);
    }

    @Override
    public void updateSurvey(Survey survey) {
        this.surveyRepository.save(survey);
    }

    @Override
    public void deleteSurvey(Long id) {
        this.surveyRepository.delete(id);

    }
}
