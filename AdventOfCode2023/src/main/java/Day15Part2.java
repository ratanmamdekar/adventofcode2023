import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;


public class Day15Part2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter initialization sequence: ");
        String str = sc.nextLine();
        String[] initializationSequence = new String[0];

        while (!str.equalsIgnoreCase("end")) {
            initializationSequence = str.split(",");
            str = sc.nextLine();
        }

        int ans = process(initializationSequence);
        System.out.println("Total sum of focusing power: " + ans);
    }

    private static int process(String[] initializationSequence) {
        List<List<String>> boxes = new ArrayList<>();
        for (int i=0;i<256;i++){
            boxes.add(new ArrayList<>());
        }

        for (String sequence :initializationSequence){
            if (sequence.charAt(sequence.length()-1)=='-'){
                String substring = sequence.substring(0, sequence.length() - 1);
                int hash = getHash(substring);
                List<String> box = boxes.get(hash);
                Iterator<String> iterator = box.iterator();
                while (iterator.hasNext()){
                    String next = iterator.next();
                    if(next.startsWith(substring)){
                        iterator.remove();
                        break;
                    }
                }
            }else {
                String[] split = sequence.split("=");
                int hash = getHash(split[0]);
                List<String> box = boxes.get(hash);
                boolean found =false;
                for (int i=0;i<box.size();i++){
                    if (box.get(i).startsWith(split[0])){
                        box.set(i,String.join(" ",split));
                        found=true;
                    }
                }
                if (!found){
                    box.add(String.join(" ",split));
                }
            }
        }
//        System.out.println(boxes);
//        return 0;
        return findFocusingPower(boxes);
    }

    private static int findFocusingPower(List<List<String>> boxes) {
        int fp=0;
        for (int i=0;i<256;i++){
            List<String> strings = boxes.get(i);
            for(int j=0;j<strings.size();j++){
                int focalLength = Integer.parseInt(strings.get(j).split(" ")[1]);
                fp += (i+1)*(j+1)*focalLength;
            }
        }
        return fp;
    }

    private static int getHash(String sequence) {

        int hash=0;
        for(char ch : sequence.toCharArray()){
            hash+=(int)ch;
            hash*=17;
            hash%=256;
        }
        return hash;
    }


}

/*\
sample input
rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7
end

ans - 145
* */