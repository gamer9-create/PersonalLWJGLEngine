package engine.logic.filesystem;

public class EngineFile {
    public int[] numbers;
    public float[] decimals;
    public String[] texts;

    public EngineFile(int n, int d, int s) {
        this.numbers = new int[n];
        this.decimals = new float[d];
        this.texts = new String[s];
    }
}
