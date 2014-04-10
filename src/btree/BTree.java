package btree;

public class BTree {
	
	private BTreeNode root;
	
	public BTree(){
		root = new BTreeNode();
		root.wrapper = this;
	}
	
	public int insert(int x){
		return root.insert(x);
	}
	
	public boolean search(int x){
		return root.search(x);
	}
	
	protected void setRoot(BTreeNode root){
		this.root = root;
		this.root.wrapper = this;
	}
	
	@Override
	public String toString() {
		return root.toString();
	}
	
	public static void main(String[] args) {
		BTree t = new BTree();

		int[] toInsert = {5,15,10,20,25,30,3,4,29,28,2,21,26,22,8,12,13};
		String solution = "00002000011001002";
		String actualSolution = "00200100021010000";
		
		String statusOfInsertion = "";
		for(int x: toInsert){
			statusOfInsertion += t.insert(x);
		}
		
		System.out.println("Das einfuegen in den Baum hat richtig funktioniert: " + statusOfInsertion.equals(actualSolution));
		System.out.println("Der Algorithmus aus der Vorlesung ist richtig: " + statusOfInsertion.equals(solution));
		System.out.println();
		System.out.println(t);
		
		System.out.println(t.search(26));
		System.out.println(t.search(100));
		
	}

}
