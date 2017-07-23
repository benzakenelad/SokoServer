package ORM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
/**
 *  
 * <p> User entity </p>
 * @author Elad Ben Zaken
 */
public class User implements Serializable {

	// Data members
	private String userName;
	private String firstName;
	private String lastName;
	
	private List<Record> recordList = new ArrayList<Record>();


	// c'tor
	public User(String userName) {
		this.userName = userName;
	}
	
	public User() {}
	
	// Getters and setters
	@Id
	@Column(name = "USERNAME", nullable = false)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "FIRSTNAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Column(name = "LASTNAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public List<Record> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<Record> recordList) {
		this.recordList = recordList;
	}

	@Override
	public String toString() {
	
		return this.firstName + " " + this.lastName;
	}
	
}