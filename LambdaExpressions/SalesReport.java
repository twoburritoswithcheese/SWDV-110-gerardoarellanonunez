package LambdaExpressions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Map;

public class SalesReport {
    public static void main(String[] args) {

        record Sale(String product, String region, int qty, double price) {}

        List<Sale> sales = new ArrayList<>(List.of(
            new Sale("potato chips", "Idaho", 10, 300.0),  
            new Sale("sodas", "Oregon", 20, 200.0),  
            new Sale("bananas", "Idaho", 88, 120.0),  
            new Sale("eggs", "Oregon", 1000, 345.00),  
            new Sale("milk", "California", 100, 550.0),  
            new Sale("steak", "Washington", 300, 4082.0),  
            new Sale("chicken", "Utah", 20, 285.0),  
            new Sale("cheese", "Idaho", 288, 827.0),  
            new Sale("yogurt", "Utah", 100, 200.0),  
            new Sale("pop-tarts", "Idaho", 1000, 2455.0)  
        ));

        double totalRevenue = sales.stream()
                                   .mapToDouble(s -> s.qty() * s.price())
                                   .sum();

        System.out.printf("Total Revenue: $%,.2f", totalRevenue);

        // sort with revenue over $100, A to Z, no duplicates
        sales.stream().filter(s -> (s.qty() * s.price()) > 100)
                      .map(Sale::product)
                      .distinct()
                      .sorted()
                      .forEach(System.out::println);

        // biggest sale
        Optional<Sale> biggestSale = sales.stream()
                .max(Comparator.comparingDouble(s -> s.qty() * s.price()));

        System.out.println("Biggest Sale: " + biggestSale);
        
        // revenue by region
        Map<String, Double> revenuByRegeion = sales.stream()
                .collect(Collectors.groupingBy(Sale::region, 
                Collectors.summingDouble(s -> s.qty() * s.price())));
        
        System.out.println(revenuByRegeion);
    }

}

