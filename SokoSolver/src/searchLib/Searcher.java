package searchLib;

public interface Searcher <T> {
	public Solution search(Searchable<T> searchable);
	public int getNumberOfNodesEvaluated();

}
