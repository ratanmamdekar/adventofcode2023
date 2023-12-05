import java.util.*;
import java.util.stream.Collectors;

public class Day5Part1 {
    static List<List<Long>> seedToSoil;
    static List<List<Long>> soilToFertilizer;
    static List<List<Long>> fertilizerToWater;
    static List<List<Long>> waterToLight;
    static List<List<Long>> lightToTemperature;
    static List<List<Long>> temperatureToHumidity;
    static List<List<Long>> humidityToLocation;
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream
        System.out.println("Enter seeds: ");
        String str= sc.nextLine();
        List<Long> seeds = getSeeds(str);
        System.out.println("enter seed-to-soil map:");
        seedToSoil = new ArrayList<>();
        str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            seedToSoil.add(getLongsFromStr(str));
            str= sc.nextLine();
        }

        System.out.println("enter soil-to-fertilizer map:");
        soilToFertilizer = new ArrayList<>();
        str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            soilToFertilizer.add(getLongsFromStr(str));
            str= sc.nextLine();
        }

        System.out.println("enter fertilizer-to-water map:");
        fertilizerToWater = new ArrayList<>();
        str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            fertilizerToWater.add(getLongsFromStr(str));
            str= sc.nextLine();
        }

        System.out.println("enter water-to-light map:");
        waterToLight = new ArrayList<>();
        str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            waterToLight.add(getLongsFromStr(str));
            str= sc.nextLine();
        }

        System.out.println("enter light-to-temperature map:");
        lightToTemperature = new ArrayList<>();
        str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            lightToTemperature.add(getLongsFromStr(str));
            str= sc.nextLine();
        }

        System.out.println("enter temperature-to-humidity map:");
        temperatureToHumidity = new ArrayList<>();
        str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            temperatureToHumidity.add(getLongsFromStr(str));
            str= sc.nextLine();
        }

        System.out.println("enter humidity-to-location map:");
        humidityToLocation = new ArrayList<>();
        str= sc.nextLine();
        while (!str.equalsIgnoreCase("end")){
            humidityToLocation.add(getLongsFromStr(str));
            str= sc.nextLine();
        }
        long ans = Long.MAX_VALUE;
        for(long seed:seeds){
            long location = getLocationForSeed(seed);
            ans = Math.min(ans,location);
        }
        System.out.println("lowest location number : " + ans);
    }

    private static long getLocationForSeed(long seed) {
        long soil = getDestFromSrc(seed,seedToSoil);
        long fertilizer = getDestFromSrc(soil,soilToFertilizer);
        long water = getDestFromSrc(fertilizer,fertilizerToWater);
        long light = getDestFromSrc(water,waterToLight);
        long temperature = getDestFromSrc(light,lightToTemperature);
        long humidity = getDestFromSrc(temperature,temperatureToHumidity);
        long location = getDestFromSrc(humidity,humidityToLocation);
        System.out.println("seed:"+seed +" -> location:"+location);
        return location;
    }

    private static long getDestFromSrc(long input, List<List<Long>> map) {
        for(List<Long> row : map){
            long src = row.get(1);
            long size = row.get(2);
            long dst = row.get(0);

            if(input>=src && input<=src+size-1){
                long diff  = input-src;
                return dst+diff;
            }
        }

        // if not found in map then return same
        return input;
    }

    private static List<Long> getLongsFromStr(String str) {
        String[] split = str.split(" ");
        List<Long> seedToSoil = new ArrayList<>();
        for(int i=0;i<split.length;i++){
            seedToSoil.add(Long.parseLong(split[i]));
        }
        return seedToSoil;
    }

    private static List<Long> getSeeds(String str) {

        String[] split = str.split(" ");
        List<Long> seeds = new ArrayList<>();
        for(int i=1;i<split.length;i++){
            seeds.add(Long.parseLong(split[i]));
        }
        return seeds;
    }

}

/*\
sample input(enter with each prompt)
seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48
end

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15
end

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4
end

water-to-light map:
88 18 7
18 25 70
end

light-to-temperature map:
45 77 23
81 45 19
68 64 13
end

temperature-to-humidity map:
0 69 1
1 0 69
end

humidity-to-location map:
60 56 37
56 93 4
end

ans - 35 for seed 13
* */