package dungeons;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class represents a random number generator for testing. It 
 * encapsulates the Random class and extends the RandomNumberGenerator 
 * class. 
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class RandomNumberGeneratorTest extends Random 
    implements RandomNumberGenerator {
  
  private static final long serialVersionUID = 1L;
  private int randomNumber;
  private List<Integer> randomNumbersList;
  
  /**
   * Constructor for generating pre-defined random number or a list of 
   * random numbers.
   * 
   * @param numbers A range of numbers than forms the list from where
   *                the random numbers are generated.
   */
  public RandomNumberGeneratorTest(int...numbers) {
    if (numbers.length == 0) {
      this.randomNumber = 0;
    } else if (numbers.length == 1) {
      this.randomNumber = numbers[0];
    } else if (numbers.length > 1) {
      this.randomNumbersList = new ArrayList<>();
      for (int i = 0; i < numbers.length; i++) {
        this.randomNumbersList.add(numbers[i]);
      }
      this.randomNumber = numbers[0];
    }
  }
 
  @Override
  public int getRandomNumber(int lowerBound, int upperBound) {
    return this.randomNumber;
  }

  @Override
  public List<Integer> getUniqueRandomNumbersList(int length, 
      int lowerBound, int upperBound) {
    return this.randomNumbersList.subList(0, length);
  }

  @Override
  public List<Integer> getRepeatedRandomNumbersList(int length, 
      int lowerBound, int upperBound) {
    List<Integer> predefinedNumbers = new ArrayList<>();
    int size = 0;
    for (int i = 0; i < length; i++) {
      if (size == upperBound) {
        size = 0;
      }
      predefinedNumbers.add(size);
      size++;
    }
    return predefinedNumbers;
  }

  @Override
  public void setSeed(int seed) {
    // do nothing
  }

}
