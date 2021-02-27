package sample;

public class ActivatingArrowThread extends Thread {
    private Main main;
    private Pair idx = new Pair();
    private Pair idx_default = new Pair();
    private boolean isAlive = true;

    public ActivatingArrowThread(Main main, int first, int last){
        this.main = main;

        idx.setFirst(first);
        idx.setSecond(last);
        idx_default = idx;
    }


    @Override
    public void run(){
        while (!isInterrupted())
        {
            idx = main.activate(idx.getFirst(), idx.getSecond());
            if (idx.getFirst()>idx.getSecond())
                {
                    idx = idx_default;
                }
        }
    }



}
