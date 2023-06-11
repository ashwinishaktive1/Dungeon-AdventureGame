package dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class represents a random number generator for the model. It 
 * encapsulates the Random class and extends the RandomNumberGenerator 
 * class. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class RandomNumberGeneratorDev extends Random implements RandomNumberGenerator {
  
  private static final long serialVersionUID = 1L;
  
  Random rand = new Random();
    
  @Override
  public int getRandomNumber(int lowerBound, int upperBound) {
    return rand.nextInt(upperBound - lowerBound) + lowerBound;
  }

  @Override
  public List<Integer> getUniqueRandomNumbersList(int length, int lowerBound, int upperBound) {
    List<Integer> numbers = new ArrayList<>();
    
    for (int i = 0; i < length; i++) {
      int number = 0;
      
      while (true) {
        number = this.getRandomNumber(lowerBound, upperBound);
        if (!numbers.contains(number)) {
          break;
        }
      }
      
      numbers.add(number);
    }
    return numbers;
  }

  @Override
  public List<Integer> getRepeatedRandomNumbersList(int length, int lowerBound, int upperBound) {
    List<Integer> numbers = new ArrayList<>();
    
    for (int i = 0; i < length; i++) {
      int number = 0;
      number = this.getRandomNumber(lowerBound, upperBound);
      
      numbers.add(number);
    }
    return numbers;
  }
  
  @Override
  public void setSeed(int seed) {
    this.rand.setSeed(seed);
  }
}
