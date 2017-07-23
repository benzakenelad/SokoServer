package com.eladproj.SokoBanJerseyWeb;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ORM.LevelSolution;
import ORM.ORMManager;

@Path("request")
/**
 * Solution Services
 * @author Elad Ben Zaken
 *
 */
public class UserService {
	private static ORMManager manager;

	public UserService() {
		manager = new ORMManager();
	}

	@Path("getsolution")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getLevelSolution(String levelData) {
		List<LevelSolution> solutions = manager.getAllLevelSolutions();
		for (LevelSolution solution : solutions)
			if (solution.getLevelData().equals(levelData))
				return solution.getSolution();

		return null;
	}

	@Path("addsolution")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addSolution(LevelSolution solution) {
		if (solution != null) {
			manager.addLevelSolution(solution);
		}
	}

}