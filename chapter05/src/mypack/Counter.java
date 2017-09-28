package mypack;

/**
 * Created by haifei on 2017/9/28.
 */
public class Counter {
    private int count;

    public Counter(){
        this(0);
    }
    public Counter(int count){
        this.count=count;
    }
    public void setCount(int count){
        this.count=count;
    }
    public int getCount(){
        return count;
    }

    public void add(int step){
        count+=step;
    }
}
