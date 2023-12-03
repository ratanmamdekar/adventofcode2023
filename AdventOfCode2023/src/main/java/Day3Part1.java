import java.util.*;

public class Day3Part1 {
    static int row,col;
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Enter all strings: ");
        List<String> engineSchematic = new ArrayList<>();
        String str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            engineSchematic.add(str);
            str= sc.nextLine();
        }
        int ans = process(engineSchematic);
        System.out.println("sum of all of the part numbers in the engine schematic : " + ans);
    }

    private static int process(List<String> engineSchematic) {
        int ans =0;

        row = engineSchematic.size();
        col = engineSchematic.get(0).length();

        for(int i =0; i< row; i++){
            for (int j=0;j<col;j++){
                if (isSymbol(engineSchematic.get(i).charAt(j))){
                    ans += getAdjacentNumbers(engineSchematic,i,j);
                }
            }
        }

        return ans;
    }

    private static int getAdjacentNumbers(List<String> engineSchematic, int i, int j) {
        int sumOfNumbers =0;
        //top left
        if(i-1>=0 && j-1>=0 && Character.isDigit(engineSchematic.get(i-1).charAt(j-1))){
            sumOfNumbers += getNumber(engineSchematic,i-1,j-1);
        }

        // up
        if(i-1>=0 && isNotNum(i-1,j-1,engineSchematic) && Character.isDigit(engineSchematic.get(i-1).charAt(j))){
            sumOfNumbers += getNumber(engineSchematic,i-1,j);
        }

        //top right
        if(i-1>=0 && j+1<col && isNotNum(i-1,j,engineSchematic) && Character.isDigit(engineSchematic.get(i-1).charAt(j+1))){
            sumOfNumbers += getNumber(engineSchematic,i-1,j+1);
        }

        //left
        if(j-1>=0 && Character.isDigit(engineSchematic.get(i).charAt(j-1))){
            sumOfNumbers += getNumber(engineSchematic,i,j-1);
        }

        //right
        if(j+1<col && Character.isDigit(engineSchematic.get(i).charAt(j+1))){
            sumOfNumbers += getNumber(engineSchematic,i,j+1);
        }

        //bottom left
        if(i+1>=0 && j-1>=0 && Character.isDigit(engineSchematic.get(i+1).charAt(j-1))){
            sumOfNumbers += getNumber(engineSchematic,i+1,j-1);
        }

        //bottom down
        if(i-1>=0 && j-1>=0 && isNotNum(i+1,j-1,engineSchematic)&& Character.isDigit(engineSchematic.get(i+1).charAt(j))){
            sumOfNumbers += getNumber(engineSchematic,i+1,j);
        }

        //bottom right
        if(i-1>=0 && j-1>=0 && isNotNum(i+1,j,engineSchematic)&& Character.isDigit(engineSchematic.get(i+1).charAt(j+1))){
            sumOfNumbers += getNumber(engineSchematic,i+1,j+1);
        }

        return sumOfNumbers;
    }

    // if prev char is a dot then it is already used to get the number
    private static boolean isNotNum(int i, int j, List<String> engineSchematic) {
        if (j<=0) return true;
        return !Character.isDigit(engineSchematic.get(i).charAt(j));
    }

    private static int getNumber(List<String> engineSchematic, int row, int j) {
        String string = engineSchematic.get(row);
        int left=j,right=j;
        while (left-1>=0 && Character.isDigit(string.charAt(left-1))){
            left--;
        }
        while (right+1<string.length() && Character.isDigit(string.charAt(right+1))){
            right++;
        }
        return Integer.parseInt(string.substring(left,right+1));
    }

    private static boolean isSymbol(char character) {
        return !isNotSymbol(character);
    }

    private static boolean isNotSymbol(char character) {
        return Character.isDigit(character) || character == '.';
    }
}

/*\
sample input
467..114..
...*......
..35..633.
......#...
617*......
.....+.58.
..592.....
......755.
...$.*....
.664.598..
end


ans - 4361
* */