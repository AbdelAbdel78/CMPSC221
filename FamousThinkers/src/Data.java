import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class Data {
    
    private static final String path = "C:\\Users\\abdel\\Desktop\\Penn State\\2022 Spring\\CMPSC221\\FamousThinkers\\data.csv";
    
    public static ArrayList<Thinker> getData() {
        String line = "";
        ArrayList<Thinker> arrList = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
//                for (int i = 0; i < values.length; i++) {
//                    System.out.println(values[i]);
//                }
                Thinker think = new Thinker(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), 
                        Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]));
                arrList.add(think);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        return arrList;
    }
    
    public static void addData(Thinker think) {
        String line = "";
        String[] data = {think.getLastName(), think.getFirstName(), Integer.toString(think.getExam(1)), Integer.toString(think.getExam(2)), 
            Integer.toString(think.getExam(3)), Integer.toString(think.getExam(4)), Integer.toString(think.getExam(5)), Integer.toString(think.getExam(6))};
        
        try (FileWriter fw = new FileWriter(path, true)) {
            for (String value : data) {
                fw.append(value);
                fw.append(",");
            }
            fw.append(System.lineSeparator());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static int getMinScore(ArrayList<Thinker> thinkers, int examNum) {
        int min = Integer.MAX_VALUE;
        
        for (Thinker thinker: thinkers) {
            if (min > thinker.getExam(examNum)) {
                min = thinker.getExam(examNum);
            }
        }
        return min;
    }
    
    public static int getMaxScore(ArrayList<Thinker> thinkers, int examNum) {
        int max = Integer.MIN_VALUE;
        
        for (Thinker thinker: thinkers) {
            if (max < thinker.getExam(examNum)) {
                max = thinker.getExam(examNum);
            }
        }
        return max;
    }
    
    public static double getAvgScore(ArrayList<Thinker> thinkers, int examNum) {
        double avg = 0;
        
        for (Thinker thinker: thinkers) {
            avg += thinker.getExam(examNum);
        }
        
        avg /= thinkers.size();
        
        return avg;
    }
    
    public static int getMinScore(Thinker think) {
        int min = Integer.MAX_VALUE;
        
        for (int i = 1; i <=6; i++) {
            if (min > think.getExam(i)) {
                min = think.getExam(i);
            }
        }
        return min;
    }
    
    public static int getMaxScore(Thinker think) {
        int max = Integer.MIN_VALUE;
        
        for (int i = 1; i <=6; i++) {
            if (max < think.getExam(i)) {
                max = think.getExam(i);
            }
        }
        return max;
    }
    
    public static double getAvgScore(Thinker think) {
        double avg = 0;
        
        for (int i = 1; i <=6; i++) {
            avg += think.getExam(i);
        }
        
        avg /= 6;
        return avg;
    }
    
    public static Thinker searchName(ArrayList<Thinker> thinkers, String input) {
        String name;
        
        for (Thinker thinker: thinkers) {
            name = thinker.getFirstName() + " " + thinker.getLastName();
            name = name.toLowerCase();
            
            if (name.contains(input)) {
                return thinker;
            }
        }
        
        return null;
    }
}     