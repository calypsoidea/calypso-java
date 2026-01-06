package com.calypso.general;

public class HelloWorld {

    private String name = "";

    public static String CARLOS = "Claudio";
    public static String Kadu = "Kadu";

    public HelloWorld(String _name) {
        this.name = _name;
    }

    public String sayHello() {

        return "Hello World, from " + this.name;

    }

    public static void main(String args[]) {

        HelloWorld helloCarlos = new HelloWorld(HelloWorld.CARLOS);

        System.out.println(helloCarlos.sayHello());

         HelloWorld helloKadu = new HelloWorld(HelloWorld.Kadu);

        System.out.println(helloKadu.sayHello());

    }

}
