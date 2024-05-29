import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {
    //
    //
    // PHASE 1: testing binary operations
    //
    //

    @Test
    public void addTwoIntTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.addTwo(4,36),40,.001);
    }
    @Test
    public void addTwoNegativeIntTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.addTwo(-4,-30),-34,.001);
    }

    @Test
    public void addNegativeToPositiveIntTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.addTwo(-5,4),-1,.001);
        assertEquals(calculator.addTwo(-10,20),10,.001);
    }
    @Test
    public void addPositiveToNegativeTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.addTwo(5,-7),-2,.001);
        assertEquals(calculator.addTwo(10,-7),3,.001);
    }
    @Test
    public void subtractTwoIntTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.subtractTwo(4,3),1,.001);
        assertEquals(calculator.subtractTwo(2,4),-2,.001);
    }
    @Test
    public void subtractTwoNegativeIntTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.subtractTwo(-3,-5),2,.001);
        assertEquals(calculator.subtractTwo(-3,-1),-2,.001);
    }
    @Test
    public void subtractNegativeFromPositiveTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.subtractTwo(-5,3),-8,.001);
        assertEquals(calculator.subtractTwo(-10,20),-30,.001);
    }
    @Test
    public void subtractPositiveFromNegativeTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.subtractTwo(7,-4),11,.001);
        assertEquals(calculator.subtractTwo(9,-10),19,.001);
    }
    @Test
    public void multiplyTwoPositiveIntTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.multiplyTwo(4,6),24,.001);
        assertEquals(calculator.multiplyTwo(10,6),60,.001);
    }
    @Test
    public void multiplyTwoNegativeIntTest(){
        Calculator calculator= new Calculator();
        assertEquals(calculator.multiplyTwo(-5,-6),30,.001);
        assertEquals(calculator.multiplyTwo(-9,-9),81,.001);
    }
    @Test
    public void multiplyNegativeAndPositiveTest(){
        Calculator calculator= new Calculator();
        assertEquals(-40,calculator.multiplyTwo(-8,5),.001);
        assertEquals(-45,calculator.multiplyTwo(9,-5),.001);
    }
    @Test
    public void divideTwoPositivesTest(){
        Calculator calculator= new Calculator();
       assertEquals(5.0,calculator.divideTwo(45,9),.001);

    }
    @Test
    public void divideTwoNegativeTest(){
        Calculator calculator= new Calculator();
        assertEquals(1,calculator.divideTwo(-3,-3),.001);
    }
    @Test
    public void divideNegativeFromPositiveTest(){
        Calculator calculator= new Calculator();
        assertEquals(-3,calculator.divideTwo(-6,2),.001);
    }
    @Test
    public void dividePositiveFromNegativeTest(){
        Calculator calculator= new Calculator();
        assertEquals(-3,calculator.divideTwo(9,-3),.001);
    }
    //
    //
    // PHASE2: testing pushing operators and numbers into both stack and queue according to the shunting yard algorithm
    @Test
    public void pushOpenBracketInStackTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("(");
        assertEquals('(',(char)calculator.stack.peek());
    }
    @Test
    public void pushStarAndDivisionInStackTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("*");
        assertEquals('*',(char)calculator.stack.peek());
        calculator.readExpression("/");
        assertEquals('/',(char)calculator.stack.peek());
    }
    @Test
    public void pushDivisionAfterDvisionAndStar(){
        Calculator calculator= new Calculator();
        calculator.readExpression("*/");
        assertEquals('*',calculator.queue.getLast());
        assertEquals('/',calculator.stack.peek());
        calculator.stack.clear();
        calculator.queue.clear();
        calculator.readExpression("//");
        assertEquals('/',calculator.queue.getLast());
        assertEquals('/',calculator.stack.peek());
    }
    @Test
    public void pushPlusAndMinusAfterOpenBracket(){
        Calculator calculator= new Calculator();
        calculator.readExpression("(+");
        assertEquals(calculator.stack.size(),2);
        assertEquals(calculator.stack.peek(),'+');
        calculator.stack.clear();
        calculator.readExpression("(-3");
        assertEquals(calculator.stack.size(),1);
        assertEquals(calculator.stack.peek(),'(');
    }
    @Test
    public void pushPlusAndMinusAfterPlusOrMinus(){
        Calculator calculator= new Calculator();
        calculator.readExpression("1+2+5");
        assertEquals(calculator.stack.size(),2);
        assertEquals(calculator.stack.peek(),'+');
        calculator.stack.clear();
        calculator.readExpression("4+5-6-");
        assertEquals(calculator.stack.size(),3);
        assertEquals(calculator.stack.peek(),'-');
    }
    @Test
    public void pushPlusAndMinusAfterStarOrDivisionTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("*+");
        assertEquals(calculator.stack.size(),1);// because of precedence the '*' should be popped into queue
        assertEquals(calculator.queue.getLast(),'*'); // making sure it is added to queue
        calculator.stack.clear(); // to clear prev stack ^
        calculator.readExpression("0/7*2-1");
        assertEquals(calculator.stack.size(),1);// because of precedence the '*' should be popped into queue
        assertEquals(calculator.queue.getLast(),1.0); // making sure it is added to queue

    }

    @Test
    public void pushClosedBracketTest(){ // the KING haha
        Calculator calculator= new Calculator();
        calculator.readExpression("/(+*)");
        assertEquals(calculator.queue.getLast(),'+');
        assertEquals(calculator.stack.peek(),'/');
        assertEquals(calculator.stack.size(),1); //{/}
        assertEquals(calculator.queue.size(),2); // {* ,- ,+ }
    }
    @Test
    public void enqueuePositiveNumberTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("2+4");
        assertEquals(calculator.stack.peek(),'+');
        assertEquals(calculator.queue.getLast(),4.0);
        assertEquals(calculator.queue.getFirst(),2.0);
        calculator.stack.clear();
        calculator.queue.clear();
        calculator.readExpression("255+4");
        assertEquals(calculator.stack.peek(),'+');
        assertEquals(calculator.queue.getFirst(),255.0);
        assertEquals(calculator.queue.getLast(),4.0);
    }
    @Test
    public void enqueueNegativeNumberInBeginningTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("-4+5");
        assertEquals(calculator.queue.getFirst(),-4.0);

    }
    @Test
    public void enqueueNegativeNumberAfterPlusOrMinusTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("1+-3");
        assertEquals(-3.0,calculator.queue.getLast());
        assertNotEquals('-',calculator.stack.peek());
        calculator.stack.clear();
        calculator.queue.clear();
        calculator.readExpression("1--3");
        assertEquals(-3.0,calculator.queue.getLast());
        assertEquals(1,calculator.stack.size());
    }
    @Test
    public void enqueueNegativeNumberAfterStarAndDivisionTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("1*-3");
        assertEquals(-3.0,calculator.queue.getLast());
        assertEquals('*',calculator.stack.peek());
        calculator.stack.clear();
        calculator.queue.clear();
        calculator.readExpression("1/-3");
        assertEquals(-3.0,calculator.queue.getLast());
        assertEquals('/',calculator.stack.peek());
    }
    @Test
    public void enqueueNegativeNumberAfterOpenBracketTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("(-3)");
        assertEquals(-3.0,calculator.queue.getLast());
        assertEquals(0,calculator.stack.size());
        calculator.stack.clear();
        calculator.queue.clear();

    }
    //
    //
    // PHASE3: testing readExpression method
    //
    //
    @Test
    public void readFullExpressionTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("(5+2)*3");
        assertEquals(4,calculator.queue.size());//{5,2,+,3}
        assertEquals(1,calculator.stack.size());//{*}

        calculator.stack.clear();
        calculator.queue.clear();

        calculator.readExpression("(-5*-7)"); // queue{-5,-7,*} // stack{}
        assertEquals(3,calculator.queue.size());
        assertEquals(0,calculator.stack.size());
        assertEquals(-5.0,calculator.queue.getFirst());

        calculator.stack.clear();
        calculator.queue.clear();

        calculator.readExpression("(5*4+3*2)-1"); //queue{5, 4, *,3,2,*,+,1} // stack{-}
        assertEquals(8,calculator.queue.size());
        assertEquals(1,calculator.stack.size());
        assertEquals(5.0,calculator.queue.getFirst());
        assertEquals(1.0,calculator.queue.getLast());

    }
    //
    //
    // PHASE 4: testing emptyTheStackIntoQueue method
    //
    //
    @Test
    public void emptyTheStackIntoQueueTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("(-3*8)/6-2");
        int stackSizeBefore= calculator.stack.size();
        int queueSizeBefore= calculator.queue.size();
        calculator.emptyStackIntoQueue();
        assertEquals(queueSizeBefore+stackSizeBefore,calculator.queue.size());
        assertEquals('-',calculator.queue.getLast());
        assertEquals(-3.0,calculator.queue.getFirst());

    }
    //
    //
    // PHASE 5: testing emptyQueueIntoStack method
    //
    //
    @Test
    public void emptyQueueIntoStackTest(){
        Calculator calculator= new Calculator();
        calculator.readExpression("(-3*8)/6-2");
        calculator.emptyStackIntoQueue();
        calculator.emptyQueueIntoStack();

        assertEquals(0,calculator.queue.size());
        assertEquals(-6.0, (double)calculator.stack.peek(),.001);
    }






}
