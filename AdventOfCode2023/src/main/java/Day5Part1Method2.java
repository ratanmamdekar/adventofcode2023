import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// modify input list all together, instead of one seed at a time. This helps with part2.
// inspired from https://www.youtube.com/watch?v=NmxHw_bHhGM
public class Day5Part1Method2 {
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
        List<Long> modifiedSeedPairs = new ArrayList<>();
        for(int i=0;i<seeds.size();i+=2){
            long start = seeds.get(i);
            long count = seeds.get(i+1);
            modifiedSeedPairs.add(start);
            modifiedSeedPairs.add(start+count);
        }

        List<Long> location = getLocationForSeed(seeds);
        System.out.println("lowest location number : " + location.stream().min(Long::compareTo));
    }

    private static List<Long> getLocationForSeed(List<Long> seed) {
        List<Long> soil = getDestFromSrc(seed,seedToSoil);
        List<Long> fertilizer = getDestFromSrc(soil,soilToFertilizer);
        List<Long> water = getDestFromSrc(fertilizer,fertilizerToWater);
        List<Long> light = getDestFromSrc(water,waterToLight);
        List<Long> temperature = getDestFromSrc(light,lightToTemperature);
        List<Long> humidity = getDestFromSrc(temperature,temperatureToHumidity);
        List<Long> location = getDestFromSrc(humidity,humidityToLocation);
        System.out.println("locations:"+location);
        return location;
    }

    private static List<Long> getDestFromSrc(List<Long> inputs, List<List<Long>> map) {
        List<Long> newLongs = new ArrayList<>();
        for (Long input: inputs){
            boolean foundInRange = false;
            for(List<Long> row : map){
                long src = row.get(1);
                long size = row.get(2);
                long dst = row.get(0);

                if(input>=src && input<=src+size-1){
                    foundInRange=true;
                    long diff  = input-src;
                    newLongs.add(dst+diff);
                }
            }
            if (!foundInRange){
                newLongs.add(input);
            }
        }

        return newLongs;
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