package lt.vytautasb;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Main {
    private static List<Product> productListFromCSV = new LinkedList<>();

     private static void loadDataOnStart(){
        try{
            String fileName = "sample.csv";
            ClassLoader classLoader = Main.class.getClassLoader();
            File file = new File(classLoader.getResource(fileName).getFile());
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;

            csvLoop:
            while ((line = br.readLine()) != null){
                if(i == 0){
                    i++;
                    continue;
                }

                String[] productString = line.split(",");
                Product product = new Product(productString[0], productString[1], Integer.parseInt(productString[2]),
                        new SimpleDateFormat("yyy-MM-dd").parse(productString[3]));

                if(!productListFromCSV.isEmpty()){
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

    public static void main(String[] args){
         loadDataOnStart();
        Collections.sort(productListFromCSV);
        for (Product product:getProductsByQuantity(10)){
            System.out.println(product);
        }
    }
}
