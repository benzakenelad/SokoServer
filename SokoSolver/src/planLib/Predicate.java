package planLib;

import java.util.Map;

public class Predicate {
	private String name;
	private Object[] args;
	
	public String getName() {
		return name;
	}

	public Object[] getArgs() {
		return args;
	}	
	
	public Predicate(String name, Object... args) {
		this.name = name;
		this.args = args;
	}
	
	@Override
	public boolean equals(Object obj) {
		Predicate p = (Predicate)obj;
		if (!this.name.equals(p.name))
			return false;
		for (int i = 0; i < args.length; i++) {
			if (!args[i].equals(p.args[i])) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name + "(");
		for (Object arg : args) {
			sb.append(arg).append(",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}
	
	@Override
	public int hashCode() {		
		return toString().hashCode();
	}
	
	public Predicate instantiate(Map<String, Object> assignment) {
		Predicate p = new Predicate(name);
		p.args = new Object[args.length];
		
		for (int i = 0; i < args.length; i++) {
			Object obj = assignment.get(args[i]);
			p.args[i] = obj;
		}
		return p;
	}
	
}
