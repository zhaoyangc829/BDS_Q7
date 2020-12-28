import java.io.*;
import java.util.*;

public class Mod {
    private final List<Integer> L;
    private final List<String> inputs = new LinkedList<>();
    private final List<String> outputs = new LinkedList<>();
    private final String type;

    public Mod(List<Integer> L, String type) {
        this.L = L;
        this.type = type;
    }

    public void calculate(String inputFilePath) {
        switch (type) {
            case "C": {
                System.out.println("Loading data...");
                load(inputFilePath);
                System.out.println("Loading finished");
                System.out.println("Start to calculate...");
                calculateCoordinates();
                System.out.println("Calculation finished");
                System.out.println("Writing to file...");
                writeCoordinates();
                System.out.println("Finished");
                break;
            }
            case "I": {
                System.out.println("Loading data...");
                load(inputFilePath);
                System.out.println("Loading finished");
                System.out.println("Start to calculate...");
                calculateIndex();
                System.out.println("Calculation finished");
                System.out.println("Writing to file...");
                writeIndex();
                System.out.println("Finished");
            }
        }
    }

    private void load(String inputFilePath) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(inputFilePath))));
            String text;
            br.readLine(); // 去掉第一行 x1 x2
            while ((text = br.readLine()) != null) {
                this.inputs.add(text);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Caught IOException at loadCoordinates.");
        }
    }

    private void calculateCoordinates() {
//        for (String i : this.inputs) {
//            int value = Integer.parseInt(i);
//            int x1 = value % this.L1;
//            int x2 = (value - value % this.L1) / this.L1;
//            this.outputs.add(x1 + "\t" + x2);
//        }
        StringBuilder coordinates;
        int numerator, denominator, quotient, mod = 0;
        for (String input : this.inputs) {
            coordinates = new StringBuilder();
            for (int i = 0; i < this.L.size(); i++) {
                if (i == 0) {
                    mod = Integer.parseInt(input);
                }
                numerator = mod;
                denominator = this.getDenominator(i + 1);
                quotient = numerator / denominator;
                mod = numerator % denominator;
                coordinates.insert(0, quotient).insert(0, "\t");
            }
            this.outputs.add(coordinates.toString().trim());
        }
    }

    private int getDenominator(int minus) {
        int denominator = 1;
        for (int i = 0; i < this.L.size() - minus; i++) {
            denominator *= this.L.get(i);
        }
        return denominator;
    }

    private void calculateIndex() {
//        for (String i : this.inputs) {
//            String[] coordinates = i .split("\t");
//            int x1 = Integer.parseInt(coordinates[0]);
//            int x2 = Integer.parseInt(coordinates[1]);
//            this.outputs.add(String.valueOf(x2 * this.L1 + x1));
//        }
        int index, l;
        for (String input : this.inputs) {
            index = 0;
            String[] coordinates = input.split("\t");
            for (int i = 0; i < this.L.size(); i++) {
                if (i == 0) {
                    l = 1;
                } else {
                    l = this.L.get(i - 1);
                }
                index += Integer.parseInt(coordinates[i]) * l;
            }
            this.outputs.add(String.valueOf(index));
        }
    }

    private void writeIndex() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("./output.txt"))));
            bw.write("Index" + "\r\n");
            this.write(bw);
        } catch (IOException e) {
            System.out.println("Caught IOException at writeIndex.");
        }
    }

    private void writeCoordinates() {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File("./output.txt"))));
            bw.write("x1" + "\t" + "x2" + "\r\n");
            this.write(bw);
        } catch (IOException e) {
            System.out.println("Caught IOException at writeCoordinates.");
        }
    }

    private void write(BufferedWriter bw) throws IOException {
        for (String o : this.outputs) {
            bw.write(o + "\r\n");
        }
        bw.flush();
        bw.close();
    }
}
