package ORM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
/**
 * <p> Level Record entity </p>
 * @author Elad Ben Zaken
 *
 */
public class Record implements Serializable{
	
	// Data members
	private Integer recordID;;
	private User user;
	private String levelID;
	private Integer steps;
	private Double time;
	
	// c'tor
	public Record(User user, String levelID, Integer steps, Double time) {
		this.user = user;
		this.levelID = levelID;
		this.steps = steps;
		this.time = time;
	}
	
	public Record() {}
	
	// Getters and setters
	@Id
	@GeneratedValue
	@Column(name = "RECORDID", nullable = false)
	public Integer getRecordID() {
		return recordID;
	}

	public void setRecordID(Integer recordID) {
		this.recordID = recordID;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERNAME", nullable = false)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "LEVELID")
	public String getLevelID() {
		return levelID;
	}

	public void setLevelID(String levelID) {
		this.levelID = levelID;
	}

	@Column(name = "STEPS")
	public Integer getSteps() {
		return steps;
	}

	public void setSteps(Integer steps) {
		this.steps = steps;
	}

	@Column(name = "TIME")
	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Record [Record ID = " + recordID + ", User Full Name = " + user.getFirstName() + " " + user.getLastName() + ", Level ID = " + levelID + ", Steps = " + steps
				+ ", Finish Time = " + time + "]";
	}

}