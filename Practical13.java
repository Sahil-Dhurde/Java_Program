public class Practical13 {
    public static void main(String[] args) {
        
        int intPrimitive = 10;
        double doublePrimitive = 25.5;
        char charPrimitive = 'A';
        boolean boolPrimitive = true;

        
        Integer intObject = Integer.valueOf(intPrimitive);     // Manual Boxing
        Double doubleObject = Double.valueOf(doublePrimitive);
        Character charObject = Character.valueOf(charPrimitive);
        Boolean boolObject = Boolean.valueOf(boolPrimitive);

        // Auto-boxing 
        Integer autoBoxedInt = intPrimitive;
        Double autoBoxedDouble = doublePrimitive;

        // Unboxing: Converting wrapper objects back to primitive types
        int unboxedInt = intObject.intValue();                
        double unboxedDouble = doubleObject.doubleValue();
        char unboxedChar = charObject.charValue();
        boolean unboxedBool = boolObject.booleanValue();

        // Auto-unboxing
        int autoUnboxedInt = autoBoxedInt;
        double autoUnboxedDouble = autoBoxedDouble;

        
        System.out.println("Boxing:");
        System.out.println("Integer object: " + intObject);
        System.out.println("Double object: " + doubleObject);
        System.out.println("Character object: " + charObject);
        System.out.println("Boolean object: " + boolObject);

        System.out.println("\nUnboxing:");
        System.out.println("int value: " + unboxedInt);
        System.out.println("double value: " + unboxedDouble);
        System.out.println("char value: " + unboxedChar);
        System.out.println("boolean value: " + unboxedBool);

        System.out.println("\nAuto-boxing and Auto-unboxing:");
        System.out.println("Auto-boxed int: " + autoBoxedInt);
        System.out.println("Auto-unboxed int: " + autoUnboxedInt);
        System.out.println("Auto-boxed double: " + autoBoxedDouble);
        System.out.println("Auto-unboxed double: " + autoUnboxedDouble);
    }
}

