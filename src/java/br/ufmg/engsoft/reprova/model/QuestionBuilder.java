package br.ufmg.engsoft.reprova.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder for Question.
 */
public class QuestionBuilder {
	protected String id;
    protected String theme;
    protected String description;
    protected String statement;
    protected List<Course> courses;
    protected boolean isPrivate = true;

    public QuestionBuilder id(String id) {
        this.id = id;
        return this;
    }

    public QuestionBuilder theme(String theme) {
        this.theme = theme;
        return this;
    }

    public QuestionBuilder description(String description) {
        this.description = description;
        return this;
    }

    public QuestionBuilder statement(String statement) {
        this.statement = statement;
        return this;
    }

    public QuestionBuilder courses(List<Course> courses) {
        this.courses = courses;
        return this;
    }

    public QuestionBuilder isPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
        return this;
    }


    /**
     * Build the question.
     *
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Question build() {
        if (theme == null) throw new IllegalArgumentException("theme mustn't be null");

        if (theme.isEmpty()) throw new IllegalArgumentException("theme mustn't be empty");

        if (description == null) throw new IllegalArgumentException("description mustn't be null");

        if (description.isEmpty()) throw new IllegalArgumentException("description mustn't be empty");


        if (this.courses == null) {
            this.courses = new ArrayList<>();
        } else {
            // All inner maps mustn't be null:
            for (Course course : this.courses) {
                if (course == null) {
                    throw new IllegalArgumentException("Semester mustn't be null");
                }
            }
        }

        return new Question(this.id, this.theme, this.description, this.statement, this.courses, this.isPrivate);
    }
}
