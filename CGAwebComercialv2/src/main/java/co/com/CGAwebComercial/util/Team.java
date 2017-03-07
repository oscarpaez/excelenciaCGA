package co.com.CGAwebComercial.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.CGAwebComercial.entyties.CargaTrabajo;

@SuppressWarnings("serial")
public class Team implements Serializable {
	 
    private String name;
     
    private List<CargaTrabajo> stats;
     
    public Team() {
    	 stats = new ArrayList<CargaTrabajo>();
    }
    
    public Team(String name) {
        this.name = name;
        stats = new ArrayList<CargaTrabajo>();
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CargaTrabajo> getStats() {
		return stats;
	}

	public void setStats(List<CargaTrabajo> stats) {
		this.stats = stats;
	}
}
