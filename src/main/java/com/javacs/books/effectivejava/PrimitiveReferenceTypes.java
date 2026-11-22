package com.javacs.books.effectivejava;

public class PrimitiveReferenceTypes {
    class User {
        private String name;
        User(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        PrimitiveReferenceTypes primitiveReferenceTypes = new PrimitiveReferenceTypes();
        PrimitiveReferenceTypes.User u1 = primitiveReferenceTypes.new User("a");

        PrimitiveReferenceTypes.User u2 = u1;

        u2.setName("b");

        System.out.println(u1.getName());
        System.out.println(u2.getName());
    }
}
