package in.co.rays.proj4.bean;

import java.util.Date;

/**
 * TimetableBean class is used as a Bean/DTO to store
 * timetable and examination details such as semester,
 * description, exam date, exam time, course details,
 * and subject details.
 * 
 * This class extends BaseBean and provides getter
 * and setter methods for all properties.
 * 
 * @author Vinay
 * @version 1.0
 *
 */
public class TimetableBean extends BaseBean {

	/**
	 * Semester of the examination.
	 */
	private String semester;

	/**
	 * Description of the timetable.
	 */
	private String description;

	/**
	 * Date of examination.
	 */
	private Date examDate;

	/**
	 * Time of examination.
	 */
	private String examTime;

	/**
	 * Course ID.
	 */
	private long courseId;

	/**
	 * Course name.
	 */
	private String courseName;

	/**
	 * Subject ID.
	 */
	private long subjectId;

	/**
	 * Subject name.
	 */
	private String subjectName;

	/**
	 * Returns semester.
	 * 
	 * @return semester
	 */
	public String getSemester() {
		return semester;
	}

	/**
	 * Sets semester.
	 * 
	 * @param semester
	 */
	public void setSemester(String semester) {
		this.semester = semester;
	}

	/**
	 * Returns description.
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns exam date.
	 * 
	 * @return examDate
	 */
	public Date getExamDate() {
		return examDate;
	}

	/**
	 * Sets exam date.
	 * 
	 * @param examDate
	 */
	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	/**
	 * Returns exam time.
	 * 
	 * @return examTime
	 */
	public String getExamTime() {
		return examTime;
	}

	/**
	 * Sets exam time.
	 * 
	 * @param examTime
	 */
	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	/**
	 * Returns course ID.
	 * 
	 * @return courseId
	 */
	public long getCourseId() {
		return courseId;
	}

	/**
	 * Sets course ID.
	 * 
	 * @param courseId
	 */
	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	/**
	 * Returns course name.
	 * 
	 * @return courseName
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets course name.
	 * 
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns subject ID.
	 * 
	 * @return subjectId
	 */
	public long getSubjectId() {
		return subjectId;
	}

	/**
	 * Sets subject ID.
	 * 
	 * @param subjectId
	 */
	public void setSubjectId(long subjectId) {
		this.subjectId = subjectId;
	}

	/**
	 * Returns subject name.
	 * 
	 * @return subjectName
	 */
	public String getSubjectName() {
		return subjectName;
	}

	/**
	 * Sets subject name.
	 * 
	 * @param subjectName
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	/**
	 * Returns unique key value.
	 * 
	 * @return id as String
	 */
	@Override
	public String getKey() {
		return id + "";
	}

	/**
	 * Returns display value.
	 * 
	 * @return value
	 */
	@Override
	public String getValue() {
		return null;
	}
}