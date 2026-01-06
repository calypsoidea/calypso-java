package helloworlds;

public class HelloWorld {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No asset provided.");
            return;
        }
        String asset = args[0];
        System.out.println("Java received asset: " + asset);
    }
    
}
