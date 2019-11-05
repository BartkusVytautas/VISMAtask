package lt.vytautasb;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Main {

    /**
     *  A List where all products from CSV file will be placed
     */
    private static List<Product> productListFromCSV = new LinkedList<>();

    /**
     * A method which reads the CSV file and converts strings to Product objects and places them to the list
     */
    private static void loadDataOnStart(){
        try{
            String fileName = "sample.csv";
            ClassLoader classLoader = Main.class.getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());  //To avoid writing an absolute path every time, reader reads from resource folder
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            csvLoop:
            while ((line = br.readLine()) != null){
                if(i == 0){     //Skipping first row with column names
                    i++;
                    continue;
                }

                String[] productString = line.split(",");   //Splitting the row by "," and converting it to the Product object
                Product product = new Product(productString[0], productString[1], Integer.parseInt(productString[2]),
                        new SimpleDateFormat("yyy-MM-dd").parse(productString[3]));

                if(!productListFromCSV.isEmpty()){   //Adding quantities for the same products
                    for(Product p: productListFromCSV){
                        if(product.getName().equals(p.getName()) && product.getCode().equals(p.getCode()) &&
                                product.getExpirationDate().equals(p.getExpirationDate())){
                            p.setQuantity(p.getQuantity() + product.getQuantity());
                            continue csvLoop;
                        }
                    }
                }
                productListFromCSV.add(product);
            }
            br.close();
        } catch (IOException | ParseException e){
            e.printStackTrace();

        }

    }

    /**
     * A method to get the product list with products with less when given quantity
     * @param  quantity  A maximum quantity to get the products to
     * @return  A list with products with less than given quantity
     */
    private static List<Product> getProductsByQuantity(int quantity){
        List<Product> productsByQuantity = new LinkedList<>();
        for(Product p: productListFromCSV){
            if(p.getQuantity() < quantity){
                productsByQuantity.add(p);
            }
        }
        Collections.sort(productsByQuantity);
        return productsByQuantity;
    }

    /**
     * A method to get the product list which have expiration date before the given date
     * @param date Maximum date that expiration date has to meet
     * @return A list with expiration dates below than given date
     */
    private static List<Product> getProductsByDate(Date date){
         List<Product> productsByExpirationDate = new LinkedList<>();
         for(Product p: productListFromCSV){
             if(p.getExpirationDate().compareTo(date) < 0){
                 productsByExpirationDate.add(p);
             }
         }
         Collections.sort(productsByExpirationDate);
         return productsByExpirationDate;
    }

    public static void main(String[] args){
         loadDataOnStart();
         Collections.sort(productListFromCSV);

         Scanner sc = new Scanner(System.in);
         int choice = 1;
         int quantity;
         Date expirationDate;

         while (choice != 0){
             System.out.println("Enter 1 to get products by quantity, 2 to get products by expiration date, 0 to exit: ");
             choice = sc.nextInt();
             switch (choice){
                 case 0:
                     System.out.println("Exiting...");
                     break;
                 case 1:
                     System.out.println("Enter the quantity: ");
                     quantity = sc.nextInt();
                     System.out.println("Products that have less than " + quantity + " quantity are: ");
                     for(Product p: getProductsByQuantity(quantity)){
                         System.out.println(p);
                     }
                     break;
                 case 2:
                     System.out.println("Enter the expiration date in format yyyy-MM-dd: ");
                     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                     try {
                         expirationDate = sdf.parse(sc.next());
                         System.out.println("Products that have expiration date with less than " + sdf.format(expirationDate) + " are: ");
                         for (Product p : getProductsByDate(expirationDate)) {
                             System.out.println(p);
                         }
                     }catch (ParseException e){
                         System.out.println("Invalid date format, should be yyyy-MM-dd");
                     }
                     break;
                 default:
                     System.out.println("Wrong number, try again.");
                     break;
             }

         }
         sc.close();
    }
}
