package cn.lcl.service;

import cn.lcl.pojo.QuestionAnswer;
import cn.lcl.pojo.ThingQuestion;

import java.util.List;

public interface QuestionService {

    /**
     *
     * @param question
     * @return save success or failed
     */
    Boolean saveQuestion(ThingQuestion question);

    List<ThingQuestion> listQuestions(Integer thingId);

    /**
     *
     * @param answer one answer-option of question.
     * @return save success or failed
     */
    Boolean saveQuestionAnswer(QuestionAnswer answer);

    List<ThingQuestion> listQuestionsWithAnswers(Integer thingId);

}
