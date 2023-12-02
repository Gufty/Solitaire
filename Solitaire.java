import java.util.*;

import javax.swing.JFrame;

public class Solitaire
{
	public static void main(String[] args)
	{
		new Solitaire();
	}

	private Stack<Card> stock;
	private Stack<Card> waste;
	private Stack<Card>[] foundations;
	private Stack<Card>[] piles;
	private SolitaireDisplay display;
	private Clock timer;

	public Solitaire()
	{
		
		foundations = new Stack[4];
		piles = new Stack[7];
		//INSERT d1CODE HERE
		stock = new Stack<Card>();
		waste = new Stack<Card>();
		for(int i = 0; i < foundations.length; i++)
			foundations[i] = new Stack<Card>();
		for(int i = 0; i < piles.length; i++)
			piles[i] = new Stack<Card>();
		display = new SolitaireDisplay(this);
		timer = new Clock();
		createStock();
		deal();
	}

	//returns the card on top of the stock,
	//or null if the stock is empty
	public Card getStockCard()
	{
		if(stock.isEmpty())
			return null;
		return stock.peek();
	}

	//returns the card on top of the waste,
	//or null if the waste is empty
	public Card getWasteCard()
	{
		if(waste.isEmpty())
			return null;
		return waste.peek();
	}
	//precondition:  0 <= index < 4
	//postcondition: returns the card on top of the given
	//               foundation, or null if the foundation
	//               is empty
	public Card getFoundationCard(int index)
	{
		if(foundations[index].isEmpty())
			return null;
		return foundations[index].peek();
	}

	//precondition:  0 <= index < 7
	//postcondition: returns a reference to the given pile
	public Stack<Card> getPile(int index)
	{
		return piles[index];
	}

	public void createStock(){
		ArrayList<Card> deck = new ArrayList<>();
		for(int i = 0; i < 4; i++)
			for(int j = 1; j < 14; j++){
				Card temp = new Card(j, suitConv(i));
				deck.add(temp);
			}
		while(!deck.isEmpty()){
			int randm = (int)(Math.random() * deck.size());
			stock.add(deck.get(randm));
			deck.remove(randm);
		}	
	}
	
	public void deal(){
		for(int i = 0; i < 7; i++){
			for(int j = 0; j <= i; j++){
				piles[i].push(stock.pop());
				System.out.print(piles[i].peek() + ", ");
			}
			System.out.println("");
			piles[i].peek().turnUp();
		}	
	}

	public void dealThreeCards(){
		if(stock.size() > 3)
			for(int i = 0; i < 3; i++){
				waste.push(stock.pop());
				waste.peek().turnUp();
				System.out.print(waste.peek() + ", ");
			}
		else{
			while(!stock.isEmpty()){
				waste.push(stock.pop());
				waste.peek().turnUp();
				System.out.print(waste.peek()  + ", ");
			}
		}
	}
	
	public void resetStock(){
		while(!waste.isEmpty()){
			waste.peek().turnDown();
			stock.push(waste.pop());
		}
	}
	private boolean canAddToPile(Card card, int i){
		if(card == null)
			return false;
		if((piles[i].isEmpty() && card.getRank() == 13) || (piles[i].peek().isFaceUp() && (piles[i].peek().isRed() ^ card.isRed()) && card.getRank() == piles[i].peek().getRank() - 1))
			return true;
		return false;
	}
	private boolean canAddToFoundation(Card card, int index){
		if((getFoundationCard(index) == null && card.getRank() == 1) || (getFoundationCard(index).getSuit().equals(card.getSuit()) && card.getRank() == getFoundationCard(index).getRank() + 1))
			return true;
		return false;
	}
	public String suitConv(int x){
		if(x == 0)
			return "c";
		if(x == 1)
			return "d";
		if(x == 2)
			return "h";
		if(x == 3)
			return "s";
		else
		return "";
	}
	private Stack<Card> removeFaceUpCards(int index){
		Stack<Card> fin = new Stack<Card>();
		while(!piles[index].isEmpty() && piles[index].peek().isFaceUp()){
			fin.push(piles[index].pop());
		}
		return fin;
	}
	private void addToPile(Stack<Card> cards, int index){
		while(!cards.isEmpty())
			piles[index].push(cards.pop());
	}

	//called when the stock is clicked
	public void stockClicked()
	{
		//IMPLEMENT ME
		if(display.isWasteSelected() || display.isPileSelected()){
			return;
		}
		//System.out.println("stock clicked");
		if(!stock.isEmpty())
			dealThreeCards();
		else
			resetStock();
	}

	//called when the waste is clicked
	public void wasteClicked()
	{
		//IMPLEMENT ME
		System.out.println("waste clicked");
		if(!waste.isEmpty() && !display.isWasteSelected() && !display.isPileSelected())
			display.selectWaste();
		else
			display.unselect();
	}

	//precondition:  0 <= index < 4
	//called when given foundation is clicked
	public void foundationClicked(int index)
	{
		//IMPLEMENT ME
		System.out.println("foundation #" + index + " clicked");
		if(display.isPileSelected() && canAddToFoundation(piles[display.selectedPile()].peek(), index)){
			foundations[index].push(piles[display.selectedPile()].pop());
		}
		if(display.isWasteSelected() && canAddToFoundation(waste.peek(),index)){
			foundations[index].push(waste.pop());
		}
		display.unselect();
	}

	//precondition:  0 <= index < 7
	//called when given pile is clicked
	public void pileClicked(int index)
	{
		//IMPLEMENT ME
		System.out.println("pile #" + index + " clicked");
		if(!waste.isEmpty() && display.isWasteSelected() && canAddToPile(getWasteCard(), index)){
			piles[index].push(waste.pop());
			display.unselect();
		}
		if(!display.isWasteSelected() && !display.isPileSelected() && !piles[index].isEmpty() && piles[index].peek().isFaceUp()){
			display.selectPile(index);
		}
		else if(display.selectedPile() == index){
			display.unselect();
			return;
		}
		if(!display.isWasteSelected() && !display.isPileSelected() && !piles[index].peek().isFaceUp()){
			piles[index].peek().turnUp();
			return;
		}
		if(display.selectedPile() != index){
			Stack<Card> temp = (Stack<Card>)(getPile(display.selectedPile())).clone();
			Card fin = null;
			while(!temp.isEmpty() && temp.peek().isFaceUp())
				fin = temp.pop();
			if(!canAddToPile(fin, index)){
				display.unselect();
				return;
			}
			else
				addToPile(removeFaceUpCards(display.selectedPile()), index);
			display.unselect();
		}
		
			
			
	}
}