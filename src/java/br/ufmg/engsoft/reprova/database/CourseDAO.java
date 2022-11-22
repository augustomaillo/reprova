package br.ufmg.engsoft.reprova.database;

import br.ufmg.engsoft.reprova.mime.json.Json;
import br.ufmg.engsoft.reprova.model.Course;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CourseDAO {
    protected static final Logger logger = LoggerFactory.getLogger(QuestionsDAO.class);

    protected final Json json_formatter;

    protected final MongoCollection<Document> questions_collection;

    /**
     * Basic constructor.
     * @param db    the database, mustn't be null
     * @param json  the json formatter for the database's documents, mustn't be null
     * @throws IllegalArgumentException  if any parameter is null
     */
    public CourseDAO(Mongo db, Json json_formatter) {
        if (db == null)
            throw new IllegalArgumentException("db mustn't be null");

        if (json_formatter == null)
            throw new IllegalArgumentException("json mustn't be null");

        this.questions_collection = db.getCollection("questions");
        this.questions_collection.createIndex(Indexes.compoundIndex(
        													Indexes.ascending("year"),
        													Indexes.ascending("ref"),
        													Indexes.text("courseName")
        							), new IndexOptions().unique(true));

        this.json_formatter = json_formatter;
    }
    
    public boolean delete(Course course) {
    	if (course == null) {
    		throw new IllegalArgumentException("course mustn't be null");
    	}
    	boolean result = this.collection.deleteOne(and(
														eq("year", course.year),
														eq("ref", course.ref.value),
														eq("courseName", course.courseName)
													)).wasAcknowledged();
    	if (result)
    		logger.info("Deleted course " + course.courseName +  ": " + course.year + "/" + course.ref.value);
    	else
    		logger.warn("Failed to delete course " + course.courseName +  ": " + course.year + "/" + course.ref.value);
    	return result;
    }

    public abstract void add(Course course);
    public abstract Course get(Course course);
}
