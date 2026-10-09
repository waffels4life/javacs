# String Wrapper cheat sheet

String s = "  Hello Java World  ";

s.length()              // 20
s.isEmpty()             // false
s.isBlank()             // false (Java 11+)
s.contains("Java")      // true
s.startsWith("  H")     // true
s.endsWith("d  ")       // true
s.equals("...")         // compare
s.equalsIgnoreCase("  hello java world  ")  // true

s.trim()                // "Hello Java World"
s.strip()               // "Hello Java World"
s.stripLeading()        // "Hello Java World  "
s.stripTrailing()       // "  Hello Java World"
s.substring(8, 12)      // "Java"  (8 inclusive, 12 exclusive)

s.indexOf("Java")       // 8
s.lastIndexOf("o")      // 17
s.charAt(8)             // 'J'

s.toUpperCase()         // "  HELLO JAVA WORLD  "
s.toLowerCase()         // "  hello java world  "
s.replace("Java", "Cat")     // "  Hello Cat World  "
s.replaceAll("\\s+", "_")    // works with regex
s.split(" ")            // array of words
s.strip().split("\\s+") // ["Hello", "Java", "World"]

String.valueOf(42)      // "42"  (object → String)
String.join(", ", "a", "b", "c")  // "a, b, c"
"Java ".repeat(3)       // "Java Java Java "  (Java 11+)

# Integer Wrapper cheat sheet

Integer.parseInt("42")         // String → int
Integer.parseInt("FF", 16)     // hex String → int  → 255
Integer.valueOf("42")          // String → Integer  (return object)
Integer.toString(42)           // int → String
Integer.toBinaryString(42)     // → "101010"
Integer.toHexString(42)        // → "2a"
Integer.toOctalString(42)      // → "52"

Integer.compare(10, 20)        // (10 < 20) -
Integer.compare(20, 20)        // 0 - 0
Integer.compare(30, 20)        // (30 > 20) +
Integer.max(10, 20)            // → 20
Integer.min(10, 20)            // → 10
Integer.sum(10, 20)            // → 30

Integer.MAX_VALUE              // → 2,147,483,647
Integer.MIN_VALUE              // → -2,147,483,648
Integer.SIZE                   // → 32  (bit)
Integer.BYTES                  // → 4   (byte)

# Double Wrapper cheat sheet

Double.parseDouble("3.14")     // String → double
Double.valueOf("3.14")         // String → Double (Object)
Double.toString(3.14)          // double → String

Double.isNaN(0.0 / 0.0)       // → true  (Not a Number)
Double.isInfinite(1.0 / 0.0)  // → true  (unlimited)
Double.isFinite(3.14)          // → true

Double.MAX_VALUE               // → 1.7976931348623157E308
Double.MIN_VALUE               // → 4.9E-324 (least positive)

# Character Wrapper cheat sheet

Character.isLetter('A')        // → true
Character.isDigit('5')         // → true
Character.isLetterOrDigit('A') // → true
Character.isWhitespace(' ')    // → true
Character.isUpperCase('A')     // → true
Character.isLowerCase('a')     // → true

Character.toUpperCase('a')     // → 'A'
Character.toLowerCase('A')     // → 'a'
Character.getNumericValue('7') // → 7

# Boolean Wrapper cheat sheet

Boolean.parseBoolean("true")   // → true
Boolean.parseBoolean("True")   // → true  (case-insensitive)
Boolean.parseBoolean("yes")    // → false (only recognize "true")
Boolean.toString(true)         // → "true"
Boolean.compare(true, false)   // (true > false) +