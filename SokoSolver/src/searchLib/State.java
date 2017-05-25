package searchLib;

public class State <T>{
	
	// Data members
	private T obj;
	private double cost = 0;
	private Action action = null;
	private State<T> cameFrom = null;

	// c'tor
	public State(T obj) {
		this.obj = obj;
	}
	
	public State(T obj, double cost) {
		this.obj = obj;
		this.cost = cost;
	}
	
	// Methods
	@Override
	public boolean equals(Object o) {
		@SuppressWarnings("unchecked")
		State<T> s = (State<T>)o;
		return obj.equals(s.obj);
	}
	
	@Override
	public int hashCode() {		
		return obj.hashCode();
	}
	
	@Override
	public String toString() {		
		return obj.toString();
	}
	
	
	
	// Getters & Setters
	public T getObj() {
		return obj;
	}
	public void setObj(T obj) {
		this.obj = obj;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	public Action getAction() {
		return action;
	}
	public void setAction(Action action) {
		this.action = action;
	}
}
