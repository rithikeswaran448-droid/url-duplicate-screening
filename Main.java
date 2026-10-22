import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int m = readPositiveInt(sc, "Enter filter size in bits: ");
        int k = readPositiveInt(sc, "Enter number of hash positions: ");

        BloomFilter bloom = new BloomFilter(m, k);
        ExactSetEvaluator evaluator = new ExactSetEvaluator(bloom);

        while (true) {
            System.out.println("\n=== URL Duplicate Screening ===");
            System.out.println("1. Insert URL");
            System.out.println("2. Check URL");
            System.out.println("3. Show statistics");
            System.out.println("4. Measure false-positive rate");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid menu number.");
                continue;
            }

            if (choice == 5) {
                System.out.println("Program ended.");
                break;
            }

            if (choice == 1) {
                System.out.print("Enter URL to insert: ");
                String url = sc.nextLine();

                try {
                    if (evaluator.insert(url)) {
                        System.out.println("URL inserted successfully.");
                    } else {
                        System.out.println("Duplicate URL already exists.");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                }

            } else if (choice == 2) {
                System.out.print("Enter URL to check: ");
                String url = sc.nextLine();

                try {
                    if (!bloom.mightContain(url)) {
                        System.out.println("DEFINITELY NOT PRESENT");
                    } else if (evaluator.containsExact(url)) {
                        System.out.println("CONFIRMED DUPLICATE");
                    } else {
                        System.out.println(
                                "POSSIBLE MATCH: Bloom Filter false positive");
                    }
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                }

            } else if (choice == 3) {
                System.out.println("\n=== Filter Statistics ===");
                System.out.println("Filter size: " + bloom.getM() + " bits");
                System.out.println("Hash positions: " + bloom.getK());
                System.out.println("Unique URLs: " + evaluator.exactSize());
                System.out.println("Insertion calls: " + bloom.getInserted());
                System.out.println("Bits set: " + bloom.countSetBits());
                System.out.printf("Bit occupancy: %.2f%%%n",
                        bloom.occupancy() * 100);
                System.out.println("Bloom Filter memory: "
                        + bloom.memoryBytes() + " bytes");
                System.out.println("Approximate HashSet memory: "
                        + evaluator.estimatedExactSetBytes() + " bytes");
                System.out.printf("Theoretical FP rate: %.4f%%%n",
                        bloom.theoreticalFpRate(evaluator.exactSize()) * 100);

            } else if (choice == 4) {
                int count = readNonNegativeInt(
                        sc, "How many unseen test URLs? ");

                String[] unseen = new String[count];
                for (int i = 0; i < count; i++) {
                    System.out.print("Enter unseen URL " + (i + 1) + ": ");
                    unseen[i] = sc.nextLine();
                }

                try {
                    int fp = evaluator.countFalsePositives(unseen);
                    double rate = evaluator.observedFpRate(unseen) * 100;

                    System.out.println("Test URLs: " + count);
                    System.out.println("False positives: " + fp);
                    System.out.printf("Observed FP rate: %.2f%%%n", rate);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid input: " + e.getMessage());
                }

            } else {
                System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }

    private static int readPositiveInt(Scanner sc, String message) {
        while (true) {
            int value = readNonNegativeInt(sc, message);
            if (value > 0) {
                return value;
            }
            System.out.println("Value must be greater than zero.");
        }
    }

    private static int readNonNegativeInt(Scanner sc, String message) {
        while (true) {
            System.out.print(message);
            try {
                int value = Integer.parseInt(sc.nextLine().trim());
                if (value >= 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Ask again if the input is not an integer.
            }
            System.out.println("Enter a valid non-negative integer.");
        }
    }
}
