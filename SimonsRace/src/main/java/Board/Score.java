package Board;

public class Score implements java.io.Serializable{
    private int score;

    public Score() {
        this.score = 0;
    }

    public void addScore(int number){
        if(number > 0){
            this.score = this.score + number;
        } else {
            System.out.println("Illegal score");
        }
    }

    public void removeScore(int number){
        if(number > 0){
            if(this.score - number > 0){
                this.score = this.score - number;
            } else {
                this.score = 0;
            }

        } else {
            System.out.println("Illegal score");
        }
    }
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
