package catalogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

  public class BetterBasket extends Basket implements Serializable {
      private static final long serialVersionUID = 1L;

      static class Product {
          String name;
          int quantity;

          public Product(String name, int quantity) {
              this.name = name;
              this.quantity = quantity;
          }

          @Override
          public String toString() {
              return "Product{name='" + name + "', quantity=" + quantity + '}';
          }
      }

      public static class OrderMerger {
          // Method to merge product orders
          public static List<Product> mergeProductOrders(List<Product> productList) {
              Map<String, Integer> productCountMap = new HashMap<>();

              for (Product product : productList) {
                  productCountMap.put(product.name, productCountMap.getOrDefault(product.name, 0) + product.quantity);
              }

              List<Product> mergedProductList = new ArrayList<>();
              for (Map.Entry<String, Integer> entry : productCountMap.entrySet()) {
                  mergedProductList.add(new Product(entry.getKey(), entry.getValue()));
              }

              // Sort by product name
              mergedProductList.sort(Comparator.comparing(product -> product.name));

              return mergedProductList;
          }

          // Method to implement pagination
          public static List<Product> paginateProducts(List<Product> productList, int pageSize, int pageNumber) {
              int fromIndex = pageSize * (pageNumber - 1);
              int toIndex = Math.min(fromIndex + pageSize, productList.size());

              if (fromIndex >= productList.size() || fromIndex < 0) {
                  return Collections.emptyList(); // Return an empty list if the page number is invalid
              }

              return productList.subList(fromIndex, toIndex);
          }

          public static void main(String[] args) {
              List<Product> orders = Arrays.asList(
                      new Product("0005", 1),
                      new Product("0002", 2),
                      new Product("0001", 1),
                      new Product("0007", 3),
                      new Product("0004", 1),
                      new Product("0006", 1),
                      new Product("0003", 1)
              );

              // Merge and sort the product orders
              List<Product> mergedOrders = mergeProductOrders(orders);

              // Define pagination parameters
              int pageSize = 3;  // Number of items per page
              int pageNumber = 1; // Desired page number

              // Get paginated results
              List<Product> paginatedOrders = paginateProducts(mergedOrders, pageSize, pageNumber);

              // Print the paginated orders
              System.out.println("Page " + pageNumber + ":");
              for (Product product : paginatedOrders) {
                  System.out.println(product);
              }
          }
      }
  }

