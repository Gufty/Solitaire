public class Card {
    private int rank;
    private String suit;
    private boolean isFaceUp;
    public Card(int r, String s){
        rank = r;
        suit = s;
        isFaceUp = false;
    }
    public int getRank(){
        return rank;
    }
    public String getSuit(){
        return suit;
    }
    public boolean isRed(){
        if(suit.equals("d") || suit.equals("h"))
            return true;
        return false;
    }
    public boolean isFaceUp(){
        return isFaceUp;
    }
    public void turnUp(){
        isFaceUp = true;
    }
    public void turnDown(){
        isFaceUp = false;
    }
    public String convRank(int r){
        if(rank == 1)
        return "a";
        if(rank == 10)
        return "t";
        if(rank == 11)
        return "j";
        if(rank == 12)
        return "q";
        if(rank == 13)
        return "k";
        return "" + r;
    }
    public String rankString(){
         return convRank(getRank()) + "" + getSuit();
    }
    public String getFileName(){
        if(isFaceUp == false)
            return "cards\\back.gif";
        return "cards\\" + rankString() + ".gif";
    }
    public String toString(){
        return rankString();
    }
}