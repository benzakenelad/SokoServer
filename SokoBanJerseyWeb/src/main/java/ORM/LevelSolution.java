package ORM;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class LevelSolution implements Serializable{
	
	private Integer ID;
	private String levelData; // TXT display chained into one string
	private String solution; // TXT solution
	
	// C'tors
	public LevelSolution() {}
	
	public LevelSolution(String levelData, String solution){
		this.levelData = levelData;
		this.solution = solution;
	}
	
	// Getters and setters
	@Id
	@GeneratedValue
	@Column(name = "ID", nullable = false)
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	
	@Column(name = "LEVELDATA", nullable = false)
	public String getLevelData() {
		return levelData;
	} 
	
	public void setLevelData(String levelData) {
		this.levelData = levelData;
	}
	@Column(name = "SOLUTION")
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}
	@Override
	public String toString() {
		return levelData + " " + solution;
	}

}
