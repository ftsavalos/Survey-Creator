package com.repositories.survey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.models.User;
import com.models.survey.Survey;

import java.util.List;

@Repository
public interface SurveyRepository extends JpaRepository<Survey,Long>{

    List<Survey> findSurveysByUser(User user);

}
