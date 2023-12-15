import java.util.*;


public class Day15Part1 {

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
        System.out.println("Total sum of hash: " + ans);
    }

    private static int process(String[] initializationSequence) {
        int sum=0;

        for (String sequence :initializationSequence){
            int hash = getHash(sequence);
            System.out.println(sequence+":"+hash);
            sum+=hash;
        }
        return sum;
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

ans - 1320
* */