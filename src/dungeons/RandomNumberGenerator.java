package dungeons;

import java.util.List;

/**
 * An interface for generating random numbers and lists to be 
 * used in the model development and testing. The methods here help 
 * in generating single random numbers and a list of random numbers.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public interface RandomNumberGenerator {
  
  /**
   * Generate a random number from the required range of 
   * integers.
   * 
   * @param lowerBound Lower bound of the integer range
   * @param upperBound Upper bound of the integer range
   * @return a random number
   */
  int getRandomNumber(int lowerBound, int upperBound);
  
  /**
   * Generate a list of random non-repeating integers within 
   * the given range. 
   * 
   * @param length Length of the random number list
   * @param lowerBound Lower bound of the integers range
   * @param upperBound Upper bound of the integers range
   * @return list of random numbers
   */
  List<Integer> getUniqueRandomNumbersList(int length, int lowerBound, int upperBound);
  
  /**
   * Generate a list of random repeating integers within 
   * the given range. 
   * 
   * @param length Length of the random number list
   * @param lowerBound Lower bound of the integers range
   * @param upperBound Upper bound of the integers range
   * @return list of random numbers
   */
  List<Integer> getRepeatedRandomNumbersList(int length, int lowerBound, int upperBound);
  
  /**
   * Set a seed value for the random number generator.
   * 
   * @param seed Seed value
   */
  void setSeed(int seed);
}
